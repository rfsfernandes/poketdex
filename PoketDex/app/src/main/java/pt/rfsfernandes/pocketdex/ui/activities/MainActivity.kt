package pt.rfsfernandes.pocketdex.ui.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.Constants.SHOW_TYPE
import pt.rfsfernandes.pocketdex.custom.dialogs.TypeCustomDialog
import pt.rfsfernandes.pocketdex.databinding.ActivityMainBinding
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.model.type.Type
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

class MainActivity : FragmentActivity(), NavController.OnDestinationChangedListener {
    private val mMainViewModel: MainViewModel by viewModel()
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var mNavControllerList: NavController? = null
    private var mNavControllerDetails: NavController? = null
    private var mMediaPlayerMenuSound: MediaPlayer? = null
    private var mMediaPlayerWalkingMusic: MediaPlayer? = null
    private var mMyCustomApplication: MyCustomApplication? = null
    private var showTypeInfo = false
    private var pokemonId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMediaPlayerMenuSound = (application as MyCustomApplication).mediaPlayerMenuSound
        mMediaPlayerWalkingMusic = (application as MyCustomApplication).mediaPlayerWalkingMusic
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = mActivityMainBinding!!.root
        setContentView(view)
        mMyCustomApplication = application as MyCustomApplication
        mNavControllerList = findNavController(this, R.id.displayPokemonList)
        mMyCustomApplication!!.isLandscape = findViewById<View?>(R.id.displayPokemonDetails) != null
        if (mMyCustomApplication!!.isLandscape) {
            mNavControllerDetails = findNavController(this, R.id.displayPokemonDetails)
        }
        mNavControllerList!!.addOnDestinationChangedListener(this)
        initViewModel(savedInstanceState)
        mMainViewModel.isLoading(true)
        mActivityMainBinding!!.imageButtonSound.setOnClickListener { e: View? -> handleMusic(true) }
        if (mActivityMainBinding!!.imageButtonBack != null) {
            mActivityMainBinding!!.imageButtonBack!!.visibility = if (mMyCustomApplication!!.isLandscape) View.INVISIBLE else if (pokemonId != 0) View.VISIBLE else View.INVISIBLE
            mActivityMainBinding!!.imageButtonBack!!.setOnClickListener { e: View? -> mNavControllerList!!.popBackStack() }
        }
    }

    private fun handleMusic(fromClick: Boolean) {
        if (mMyCustomApplication!!.canPlaySounds) {
            if (fromClick) {
                mMyCustomApplication!!.canPlaySounds = false
                if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic!!.isPlaying) {
                    mMediaPlayerWalkingMusic!!.pause()
                }
            } else {
                if (mMediaPlayerWalkingMusic != null && !mMediaPlayerWalkingMusic!!.isPlaying) {
                    mMediaPlayerWalkingMusic!!.start()
                    mMediaPlayerWalkingMusic!!.isLooping = true
                }
            }
        } else {
            if (fromClick) {
                mMyCustomApplication!!.canPlaySounds = true
                if (mMediaPlayerWalkingMusic != null && !mMediaPlayerWalkingMusic!!.isPlaying) {
                    mMediaPlayerWalkingMusic!!.start()
                    mMediaPlayerWalkingMusic!!.isLooping = true
                }
            } else {
                if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic!!.isPlaying) {
                    mMediaPlayerWalkingMusic!!.pause()
                }
            }
        }
        handleMuteButton()
    }

    fun onItemClick(pokemonId: Int) {
        if (!mMyCustomApplication!!.isLandscape) {
            mNavControllerList!!.navigate(R.id.pokemonDetailsFragment)
        } else {
            if (mActivityMainBinding!!.linearLaoutDetailsContainer != null) {
                mActivityMainBinding!!.linearLaoutDetailsContainer!!.visibility = View.VISIBLE
            }
        }
        mMainViewModel.isLoading(true)
        mMainViewModel.pokemonById(pokemonId)
    }

    override fun onBackPressed() {
        mMyCustomApplication!!.playMenuSound()
        if (!mMyCustomApplication!!.isLandscape) {
            if (mNavControllerList!!.currentDestination != null && mNavControllerList!!.currentDestination!!.id == R.id.pokemonDetailsFragment) {
                mMainViewModel.selectedPokemonId.postValue(0)
                mNavControllerList!!.navigate(R.id.pokemonResultListFragment)
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        handleMusic(false)
    }

    /**
     * Handles behaviour of the mute button
     */
    private fun handleMuteButton() {
        mActivityMainBinding!!.imageButtonSound.setImageDrawable(
                if (mMyCustomApplication!!.canPlaySounds) ResourcesCompat.getDrawable(resources, R.drawable.volume_on, theme) else ResourcesCompat.getDrawable(resources, R.drawable.volume_off, theme)
        )
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel(savedState: Bundle?) {
        mMainViewModel.pokemonListResponseMutableLiveData.observe(this) { pokemonListResponse: List<PokemonResult?>? ->
            if (pokemonListResponse != null && pokemonListResponse.size > 0) {
                Log.d("Info from db", "Success")
            }
        }
        mMainViewModel.fetchErrorLiveData.observe(this) { message: String? ->
            Snackbar.make(findViewById(android.R.id.content), message!!,
                    Snackbar.LENGTH_LONG).show()
        }
        mMainViewModel.selectedPokemonId.observe(this) { pokemonId: Int ->
            this.pokemonId = pokemonId
            if (savedState != null && !savedState.isEmpty) {
                if (pokemonId != 0) {
                    if (!mMyCustomApplication!!.isLandscape) {
                        mNavControllerList!!.navigate(R.id.pokemonDetailsFragment)
                    } else {
                        if (mActivityMainBinding!!.linearLaoutDetailsContainer != null) {
                            mActivityMainBinding!!.linearLaoutDetailsContainer!!.visibility = View.VISIBLE
                        }
                        mNavControllerList!!.navigate(R.id.pokemonResultListFragment)
                    }
                } else {
                    mNavControllerList!!.navigate(R.id.pokemonResultListFragment)
                }
                savedState.clear()
            }
        }
        mMainViewModel.typeMutableLiveData.observe(this) { type: Type ->
            mMainViewModel.sHOW_typeMutableLiveData.observe(this) { show_type: SHOW_TYPE ->
                if (showTypeInfo) {
                    showTypeInfoDialog(type, show_type)
                }
                showTypeInfo = false
                mMainViewModel.isLoading(false)
            }
        }
        if (savedState == null) {
            mMainViewModel.getPokemonList()
        }
    }

    /**
     * Shows a dialog containing the picked move/pokemon type and it's counters/weaknesses
     *
     * @param type      Type of the move/pokemon
     * @param show_type If it's a move or a pokemon
     */
    private fun showTypeInfoDialog(type: Type, show_type: SHOW_TYPE) {
        val typeCustomDialog = TypeCustomDialog(this)
        typeCustomDialog.show()
        when (show_type) {
            SHOW_TYPE.MOVE -> {
                typeCustomDialog.pokemonTypesAdapterDoubleDamage?.refreshList(type.damageRelations.doubleDamageTo)
                typeCustomDialog.pokemonTypesAdapterHalfDamage?.refreshList(type.damageRelations.halfDamageTo)
                typeCustomDialog.pokemonTypesAdapterNoDamage?.refreshList(type.damageRelations.noDamageTo)
                typeCustomDialog.textViewComparisonType?.text = "TO"
            }
            SHOW_TYPE.POKEMON -> {
                typeCustomDialog.pokemonTypesAdapterDoubleDamage?.refreshList(type.damageRelations.doubleDamageFrom)
                typeCustomDialog.pokemonTypesAdapterHalfDamage?.refreshList(type.damageRelations.halfDamageFrom)
                typeCustomDialog.pokemonTypesAdapterNoDamage?.refreshList(type.damageRelations.noDamageFrom)
                typeCustomDialog.textViewComparisonType?.text = "FROM"
            }
        }
        typeCustomDialog.setType(type.name)
        mMainViewModel.isLoading(false)
    }

    fun setShowTypeInfo(showTypeInfo: Boolean) {
        this.showTypeInfo = showTypeInfo
    }

    override fun onPause() {
        super.onPause()
        if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic!!.isPlaying) {
            mMediaPlayerWalkingMusic!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityMainBinding = null
        if (mMediaPlayerWalkingMusic != null && mMediaPlayerWalkingMusic!!.isPlaying) {
            mMediaPlayerWalkingMusic!!.stop()
        }
        if (mMediaPlayerWalkingMusic != null && mMediaPlayerMenuSound!!.isPlaying) {
            mMediaPlayerMenuSound!!.stop()
        }
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.pokemonDetailsFragment && !mMyCustomApplication!!.isLandscape) {
            if (mActivityMainBinding!!.imageButtonBack != null) {
                mActivityMainBinding!!.imageButtonBack!!.visibility = View.VISIBLE
            }
        } else if (destination.id == R.id.pokemonResultListFragment && !mMyCustomApplication!!.isLandscape) {
            if (mActivityMainBinding!!.imageButtonBack != null) {
                mActivityMainBinding!!.imageButtonBack!!.visibility = View.GONE
            }
        } else {
            if (mActivityMainBinding!!.imageButtonBack != null) {
                mActivityMainBinding!!.imageButtonBack!!.visibility = View.GONE
            }
        }
    }
}