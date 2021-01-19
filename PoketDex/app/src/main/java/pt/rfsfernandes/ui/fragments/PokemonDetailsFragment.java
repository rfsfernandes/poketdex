package pt.rfsfernandes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.adapters.PokemonMovesAdapter;
import pt.rfsfernandes.custom.adapters.view_pager.DetailsPagerAdapter;
import pt.rfsfernandes.databinding.FragmentPokemonDetailsBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
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
    mDetailsPagerAdapter = new DetailsPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    binding.viewPagerDetails.setAdapter(mDetailsPagerAdapter);
    maxPage = mDetailsPagerAdapter.getCount();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.imageButtonLeft.setOnClickListener(e -> {
      if (mMyApplication.isCanPlaySounds()) {
        mMyApplication.getMediaPlayerMenuSound().start();
      }
      if (binding.viewPagerDetails.getCurrentItem() == 0) {
        mMainViewModel.changePage(mDetailsPagerAdapter.getCount());
      } else {
        mMainViewModel.changePage(binding.viewPagerDetails.getCurrentItem() - 1);
      }

    });

    binding.imageButtonRight.setOnClickListener(e -> {
      if (mMyApplication.isCanPlaySounds()) {
        mMyApplication.getMediaPlayerMenuSound().start();
      }
      if (binding.viewPagerDetails.getCurrentItem() == maxPage - 1) {
        mMainViewModel.changePage(0);
      } else {
        mMainViewModel.changePage(binding.viewPagerDetails.getCurrentItem() + 1);
      }

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
      binding.textViewPagerTitle.setText(title);
    });

  }

  /**
   * inflates dynamically linearLayoutAbilitiesContainer with each ability and refreshes the
   * moves adapter with the selected pokemon moves
   *
   * @param pokemon selected pokemon
   */
  private void fillMovesAndAbilities(Pokemon pokemon) {
//    binding.linearLayoutAbilitiesContainer.removeAllViews();
//    mPokemonMovesAdapter.refreshList(pokemon.getMoveslist());
//    for (Ability ability :
//        pokemon.getAbilitiesList()) {
//      View view = getLayoutInflater().inflate(R.layout.ability_row,
//          binding.linearLayoutAbilitiesContainer, false);
//
//      TextView textViewAbilityName =
//          view.findViewById(R.id.textViewAbilityName);
//      textViewAbilityName.setText(UtilsClass.toCamelCase(ability.getAbility().getName()));
//
//      TextView textViewAbilitySlot =
//          view.findViewById(R.id.textViewAbilitySlot);
//
//      view.findViewById(R.id.textViewAbilityHidden).setVisibility(ability.isHidden() ? View.INVISIBLE : View.VISIBLE);
//
//      textViewAbilitySlot.setText(String.format("%s %s", getString(R.string.slot_n), ability.getSlot()));
//
//      binding.linearLayoutAbilitiesContainer.addView(view);
//
//    }
  }

}