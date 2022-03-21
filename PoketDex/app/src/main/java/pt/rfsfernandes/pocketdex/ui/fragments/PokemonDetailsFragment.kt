package pt.rfsfernandes.pocketdex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.adapters.view_pager.DetailsPagerAdapter
import pt.rfsfernandes.pocketdex.databinding.FragmentPokemonDetailsBinding
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel

class PokemonDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsBinding
    private val mMainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mMyCustomApplication: MyCustomApplication
    private lateinit var mDetailsPagerAdapter: DetailsPagerAdapter
    private var maxPage = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        val view: View = binding.root
        mMyCustomApplication = requireActivity().application as MyCustomApplication
        setupViews()
        return view
    }

    /**
     * Sets up views
     */
    private fun setupViews() {
        mDetailsPagerAdapter = DetailsPagerAdapter(childFragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, context)
        binding.viewPagerDetails.adapter = mDetailsPagerAdapter
        maxPage = mDetailsPagerAdapter.count
        binding.viewPagerDetails.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (positionOffset == 0f) {
                    binding.textViewPagerTitle.text = mDetailsPagerAdapter.getPageTitle(position)
                }
            }

            override fun onPageSelected(position: Int) {
                binding.textViewPagerTitle.text = mDetailsPagerAdapter.getPageTitle(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButtonLeft.setOnClickListener { e: View? ->
            if (mMyCustomApplication.canPlaySounds) {
                mMyCustomApplication.mediaPlayerMenuSound!!.start()
            }
            val position: Int = if (binding.viewPagerDetails.currentItem == 0) {
                mDetailsPagerAdapter.count
            } else {
                binding.viewPagerDetails.currentItem - 1
            }
            mMainViewModel.changePage(position, false, requireActivity())
        }
        binding.imageButtonRight.setOnClickListener { e: View? ->
            if (mMyCustomApplication.canPlaySounds) {
                mMyCustomApplication.mediaPlayerMenuSound!!.start()
            }
            val position: Int = if (binding.viewPagerDetails.currentItem == maxPage - 1) {
                0
            } else {
                binding.viewPagerDetails.currentItem + 1
            }
            mMainViewModel.changePage(position, false, requireActivity())
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.isLoadingMutableLiveData.observe(viewLifecycleOwner) { isLoading: Boolean ->
            binding.progressBarGeneral.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        mMainViewModel.pokemonMutableLiveData.observe(viewLifecycleOwner) { pokemon: Pokemon? ->
            if (pokemon != null) {
                binding.textViewPokemonNumber.text = String.format("%s%s",
                        resources.getString(R.string.no), pokemon.id)
            }
        }
        mMainViewModel.detailsPagerLiveData.observe(viewLifecycleOwner) { page: Int? -> binding.viewPagerDetails.setCurrentItem(page!!, true) }
    }
}