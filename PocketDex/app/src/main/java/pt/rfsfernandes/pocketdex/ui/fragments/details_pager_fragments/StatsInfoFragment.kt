package pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.databinding.FragmentStatsInfoBinding
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.model.pokemon.stats.Stats
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel
import java.util.*

class StatsInfoFragment : Fragment() {
    private lateinit var binding: FragmentStatsInfoBinding
    private val mMainViewModel: MainViewModel by sharedViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentStatsInfoBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.pokemonMutableLiveData.observe(viewLifecycleOwner) { pokemon: Pokemon? ->
            if (pokemon != null) {
                mMainViewModel.isLoading(true)
                setStatViews(pokemon)
                mMainViewModel.isLoading(false)
            }
        }
    }

    /**
     * Sets each pokemon stat
     *
     * @param pokemon selected pokemon
     */
    private fun setStatViews(pokemon: Pokemon) {
        val statsList: List<Stats?> = pokemon.statsList
        if (statsList[0] != null) {
            binding.textViewStatsHp.text = statsList[0]!!.baseStat.toString()
        }
        if (statsList[1] != null) {
            binding.textViewStatsAttack.text = statsList[1]!!.baseStat.toString()
        }
        if (statsList[2] != null) {
            binding.textViewStatsDefense.text = statsList[2]!!.baseStat.toString()
        }
        if (statsList[3] != null) {
            binding.textViewStatsSpecialAttack.text = statsList[3]!!.baseStat.toString()
        }
        if (statsList[4] != null) {
            binding.textViewStatsSpecialDefense.text = statsList[4]!!.baseStat.toString()
        }
        if (statsList[5] != null) {
            binding.textViewStatsSpeed.text = statsList[5]!!.baseStat.toString()
        }
        if (context != null) {
            val weightInKg = pokemon.weight / 10f
            binding.textViewStatsWeight.text = String.format(Locale.getDefault(), "%.1f%s", weightInKg,
                    requireContext().getString(R.string.kilograms))
            val heightInMeters = pokemon.height / 10f
            binding.textViewStatsHeight.text = String.format(Locale.getDefault(), "%.1f%s", heightInMeters,
                    requireContext().getString(R.string.meters))
        }
        binding.textViewStatsBaseExperience.text = pokemon.baseExperience.toString()
    }
}