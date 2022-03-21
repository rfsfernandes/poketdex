package pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.custom.Constants
import pt.rfsfernandes.pocketdex.custom.Constants.MOVES_ITEM
import pt.rfsfernandes.pocketdex.custom.adapters.PokemonMovesAdapter
import pt.rfsfernandes.pocketdex.custom.callbacks.MovesItemClick
import pt.rfsfernandes.pocketdex.custom.dialogs.SimpleCustomDialog
import pt.rfsfernandes.pocketdex.databinding.FragmentMovesInfoBinding
import pt.rfsfernandes.pocketdex.model.moves.Moves
import pt.rfsfernandes.pocketdex.ui.activities.MainActivity
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

class MovesInfoFragment : Fragment(), MovesItemClick {
    private lateinit var binding: FragmentMovesInfoBinding
    private val mMainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mPokemonMovesAdapter: PokemonMovesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMovesInfoBinding.inflate(inflater, container, false)
        val view: View = binding.root
        mPokemonMovesAdapter = PokemonMovesAdapter(requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerViewMovesInfo.layoutManager = linearLayoutManager
        binding.recyclerViewMovesInfo.adapter = mPokemonMovesAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    /**
     * Initiates the viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.pokemonMutableLiveData.observe(viewLifecycleOwner) { (_, _, _, moveslist) ->
            mMainViewModel.isLoading(true)
            mMainViewModel.getMovesFromIds(moveslist)
        }
        mMainViewModel.movesInfo.observe(viewLifecycleOwner) { moves: List<Moves?>? ->
            moves?.let { mPokemonMovesAdapter.refreshList(it) }
            mMainViewModel.isLoading(false)
        }
    }

    /**
     * Shows a simple custom dialog with a title and a content
     *
     * @param title   Title of the dialog
     * @param content Content of the dialog
     */
    private fun showSimpleDialogContent(title: String, content: String) {
        val simpleCustomDialog = SimpleCustomDialog(requireActivity())
        simpleCustomDialog.show()
        simpleCustomDialog.textViewSimpleTitle?.text = title
        simpleCustomDialog.textViewSimpleText?.text = content
        mMainViewModel.isLoading(false)
    }

    override fun clickedMovesItem(title: String?, text: String?, moves_item: MOVES_ITEM?) {
        mMainViewModel.isLoading(true)
        when (moves_item) {
            MOVES_ITEM.TYPE -> if (activity != null) {
                mMainViewModel.isLoading(true)
                (activity as MainActivity?)!!.setShowTypeInfo(true)
                text?.toInt()
                    ?.let { mMainViewModel.getTypeAndCounters(it, Constants.SHOW_TYPE.MOVE) }
            }
            MOVES_ITEM.SIMPLE -> {
                if(title != null && text != null)
                    showSimpleDialogContent(title, text)
            }
        }
    }
}