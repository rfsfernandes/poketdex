package pt.rfsfernandes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.adapters.PokemonMovesAdapter;
import pt.rfsfernandes.custom.adapters.view_pager.DetailsPagerAdapter;
import pt.rfsfernandes.databinding.FragmentPokemonDetailsBinding;
import pt.rfsfernandes.viewmodels.MainViewModel;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class PokemonDetailsFragment extends Fragment {
  private FragmentPokemonDetailsBinding binding;
  private MainViewModel mMainViewModel;
  private MyApplication mMyApplication;
  private PokemonMovesAdapter mPokemonMovesAdapter;
  private DetailsPagerAdapter mDetailsPagerAdapter;
  private int maxPage = 1;

  public PokemonDetailsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mMyApplication = (MyApplication) requireActivity().getApplication();
    setupViews();
    return view;
  }

  /**
   * Sets up views
   */
  private void setupViews() {
    mDetailsPagerAdapter = new DetailsPagerAdapter(getChildFragmentManager(),
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getContext());
    binding.viewPagerDetails.setAdapter(mDetailsPagerAdapter);
    maxPage = mDetailsPagerAdapter.getCount();
    binding.viewPagerDetails.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset == 0) {
          binding.textViewPagerTitle.setText(mDetailsPagerAdapter.getPageTitle(position));
        }

      }

      @Override
      public void onPageSelected(int position) {

        binding.textViewPagerTitle.setText(mDetailsPagerAdapter.getPageTitle(position));

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.imageButtonLeft.setOnClickListener(e -> {
      if (mMyApplication.isCanPlaySounds()) {
        mMyApplication.getMediaPlayerMenuSound().start();
      }
      int position;
      if (binding.viewPagerDetails.getCurrentItem() == 0) {
        position = mDetailsPagerAdapter.getCount();
      } else {
        position = binding.viewPagerDetails.getCurrentItem() - 1;
      }

      mMainViewModel.changePage(position, false);

    });

    binding.imageButtonRight.setOnClickListener(e -> {
      if (mMyApplication.isCanPlaySounds()) {
        mMyApplication.getMediaPlayerMenuSound().start();
      }
      int position;
      if (binding.viewPagerDetails.getCurrentItem() == maxPage - 1) {
        position = 0;
      } else {
        position = binding.viewPagerDetails.getCurrentItem() + 1;
      }
      
      mMainViewModel.changePage(position, false);

    });

  }

  @Override
  public void onResume() {
    super.onResume();
    initViewModel();
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  /**
   * Initiates viewModel observers
   */
  private void initViewModel() {
    mMainViewModel.getIsLoadingMutableLiveData().observe(getViewLifecycleOwner(), isLoading -> {
//      binding.content.setVisibility(isLoading ? View.GONE : View.VISIBLE);
      binding.progressBarGeneral.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    });

    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      if (pokemon != null) {
//        binding.textViewPagerTitle.setText("");
        binding.textViewPokemonNumber.setText(String.format("%s%s",
            getResources().getString(R.string.no), pokemon.getId()));

      }
    });

    mMainViewModel.getDetailsPagerLiveData().observe(getViewLifecycleOwner(), page -> {
      binding.viewPagerDetails.setCurrentItem(page, true);
    });

    mMainViewModel.getDetailsTitleLiveData().observe(getViewLifecycleOwner(), title -> {
//      binding.textViewPagerTitle.setText(title);
    });

  }

}