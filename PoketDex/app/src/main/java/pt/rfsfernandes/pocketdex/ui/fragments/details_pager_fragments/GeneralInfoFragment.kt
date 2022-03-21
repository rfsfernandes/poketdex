package pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.custom.Constants
import pt.rfsfernandes.pocketdex.custom.adapters.PokemonTypesAdapter
import pt.rfsfernandes.pocketdex.databinding.FragmentGeneralInfoBinding
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.ui.activities.MainActivity
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

class GeneralInfoFragment : Fragment() {
    private lateinit var binding: FragmentGeneralInfoBinding
    private val mMainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mMyCustomApplication: MyCustomApplication
    private lateinit var mPokemonTypesAdapter: PokemonTypesAdapter
    private var isGeneralInfoLoaded = false
    private var isDescriptionLoaded = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        val view: View = binding.root
        mMyCustomApplication = requireActivity().application as MyCustomApplication
        mPokemonTypesAdapter = PokemonTypesAdapter(requireContext())
        binding.pokemonTypeGridView.adapter = mPokemonTypesAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.pokemonTypeGridView.onItemClickListener = OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
            mMainViewModel.isLoading(true)
            if (activity != null) {
                (activity as MainActivity?)!!.setShowTypeInfo(true)
                mMainViewModel.getTypeAndCounters(id.toInt(), Constants.SHOW_TYPE.POKEMON)
            }
        }
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.pokemonMutableLiveData.observe(viewLifecycleOwner) { pokemon: Pokemon? ->
            if (pokemon != null) {
                populateGeneralViews(pokemon)
                inflatePokemonTypes(pokemon)
                isGeneralInfoLoaded = true
                stopLoading()
            }
        }
        mMainViewModel.pokemonDescriptionLiveData.observe(viewLifecycleOwner
        ) { description: String? ->
            binding.textViewDescription.text = description
            isDescriptionLoaded = true
            stopLoading()
        }
    }

    /**
     * Stops the progressBar loading
     */
    private fun stopLoading() {
        if (isDescriptionLoaded && isGeneralInfoLoaded) {
            mMainViewModel.isLoading(false)
            isDescriptionLoaded = false
            isGeneralInfoLoaded = false
        }
    }

    /**
     * Populates general views such as images and pokemon name
     *
     * @param pokemon selected pokemon
     */
    private fun populateGeneralViews(pokemon: Pokemon) {
        binding.imageViewPokemonDefault.setImageURI(pokemon.sprites.frontDefault)
        binding.imageViewPokemonShiny.setImageURI(pokemon.sprites.frontShiny)
    }

    /**
     * Inflates linearLayoutTypeContainer dynamically with pokemon types
     *
     * @param pokemon selected pokemon
     */
    private fun inflatePokemonTypes(pokemon: Pokemon) {
        mPokemonTypesAdapter.refreshList(pokemon.typeList)
    }
}