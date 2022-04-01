package pt.rfsfernandes.pocketdex.ui.fragments

import android.content.Context.WINDOW_SERVICE
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.Surface.ROTATION_0
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.adapters.view_pager.DetailsPagerAdapter
import pt.rfsfernandes.pocketdex.databinding.FragmentPokemonDetailsBinding
import pt.rfsfernandes.pocketdex.model.pokemon.Pokemon
import pt.rfsfernandes.pocketdex.viewmodels.MainViewModel


class PokemonDetailsFragment : Fragment() {
    private var binding: FragmentPokemonDetailsBinding? = null
    private val mMainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mMyCustomApplication: MyCustomApplication
    private lateinit var mDetailsPagerAdapter: DetailsPagerAdapter
    private var maxPage = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        val view: View = binding?.root!!
        mMyCustomApplication = requireActivity().application as MyCustomApplication
        setupViews()

        return view
    }

    /**
     * Sets up views
     */
    private fun setupViews() {
        mDetailsPagerAdapter = DetailsPagerAdapter(requireActivity())
        binding?.viewPagerDetails?.adapter = mDetailsPagerAdapter
        maxPage = mDetailsPagerAdapter.itemCount
        binding?.viewPagerDetails?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffset == 0f) {
                    binding?.textViewPagerTitle?.text = mDetailsPagerAdapter.getPageTitle(position)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.textViewPagerTitle?.text = mDetailsPagerAdapter.getPageTitle(position)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.imageButtonLeft?.setOnClickListener { e: View? ->
            if (mMyCustomApplication.canPlaySounds) {
                mMyCustomApplication.mediaPlayerMenuSound!!.start()
            }
            val position: Int? = if (binding?.viewPagerDetails?.currentItem == 0) {
                mDetailsPagerAdapter.itemCount
            } else {
                binding?.viewPagerDetails?.currentItem?.minus(1)
            }
            if (position != null) {
                mMainViewModel.changePage(position, false, requireActivity())
            }
        }
        binding?.imageButtonRight?.setOnClickListener { e: View? ->
            if (mMyCustomApplication.canPlaySounds) {
                mMyCustomApplication.mediaPlayerMenuSound!!.start()
            }
            val position: Int? = if (binding?.viewPagerDetails?.currentItem == maxPage - 1) {
                0
            } else {
                binding?.viewPagerDetails?.currentItem?.plus(1)
            }
            position?.let { mMainViewModel.changePage(it, false, requireActivity()) }
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /**
     * Initiates viewModel observers
     */
    private fun initViewModel() {
        mMainViewModel.isLoadingMutableLiveData.observe(viewLifecycleOwner) { isLoading: Boolean ->
            binding?.progressBarGeneral?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        mMainViewModel.pokemonMutableLiveData.observe(viewLifecycleOwner) { pokemon: Pokemon? ->
            if (pokemon != null) {
                binding?.textViewPokemonNumber?.text = String.format("%s%s",
                        resources.getString(R.string.no), pokemon.id)
            }
        }
        mMainViewModel.detailsPagerLiveData.observe(viewLifecycleOwner) { page: Int? -> binding?.viewPagerDetails?.setCurrentItem(page!!, true) }
    }
}