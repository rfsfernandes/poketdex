package pt.rfsfernandes.pocketdex.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.custom.adapters.PokemonResultAdapter
import pt.rfsfernandes.pocketdex.custom.callbacks.ItemListClicked
import pt.rfsfernandes.pocketdex.databinding.FragmentPokemonResultListBinding
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult
import pt.rfsfernandes.pocketdex.ui.activities.MainActivity
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

/**
 * A fragment representing a list of Items.
 */
class PokemonResultListFragment : Fragment(), ItemListClicked<PokemonResult?> {
    private val mMainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mPokemonResultAdapter: PokemonResultAdapter
    private lateinit var binding: FragmentPokemonResultListBinding
    private var mPokemonResultList: List<PokemonResult?>? = ArrayList()
    private var isLoading = false
    private var mMediaPlayer: MediaPlayer? = null
    private lateinit var mMyCustomApplication: MyCustomApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonResultListBinding.inflate(inflater, container, false)
        val view: View = binding.root
        mPokemonResultAdapter = PokemonResultAdapter(requireContext(), this)
        mMyCustomApplication = requireActivity().application as MyCustomApplication
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = mPokemonResultAdapter
        mMediaPlayer = mMyCustomApplication.mediaPlayerMenuSound
        return view
    }

    override fun onResume() {
        super.onResume()
        if (!mMyCustomApplication.isLandscape) {
            mMainViewModel.deselectAll()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBarResultList.visibility = View.VISIBLE
        initViewModel()
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading && linearLayoutManager != null &&
                    linearLayoutManager.findLastVisibleItemPosition() == mPokemonResultList!!.size - 1 &&
                    (activity as MainActivity).mQuery?.isEmpty() == true) {

                    mMainViewModel.getPokemonList()
                    isLoading = true
                }
            }
        })
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.pokemonListResponseMutableLiveData.observe(
            viewLifecycleOwner
        ) { results: List<PokemonResult?>? ->
            binding.progressBarResultList.visibility = View.GONE
            isLoading = false
            mPokemonResultList = results?.filterNotNull()?.sortedBy { it.listPosition }
            mPokemonResultList?.let { mPokemonResultAdapter.submitList(mPokemonResultList, Runnable {
                print(it)
            }) }
        }
    }

    override fun onClick(obj: PokemonResult?, position: Int) {
        mMyCustomApplication.playMenuSound()
        obj?.let { mMainViewModel.setSelected(it.listPosition) }
        if (activity != null && activity is MainActivity) {
            obj?.let { (activity as MainActivity?)!!.onItemClick(it.listPosition) }
        }
        mPokemonResultAdapter.notifyItemChanged(position)
    }
}