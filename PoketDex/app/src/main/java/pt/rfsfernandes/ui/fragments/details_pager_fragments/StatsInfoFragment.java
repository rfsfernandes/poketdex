package pt.rfsfernandes.ui.fragments.details_pager_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.R;
import pt.rfsfernandes.databinding.FragmentStatsInfoBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon.stats.Stats;
import pt.rfsfernandes.viewmodels.MainViewModel;


public class StatsInfoFragment extends Fragment {

  private FragmentStatsInfoBinding binding;
  private MainViewModel mMainViewModel;


  public StatsInfoFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = FragmentStatsInfoBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();
  }

  /**
   * Initiates viewModel observers
   */
  private void initViewModel() {

    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      if (pokemon != null) {
        mMainViewModel.isLoading(true);
        setStatViews(pokemon);
        mMainViewModel.isLoading(false);
      }
    });


  }

  /**
   * Sets each pokemon stat
   *
   * @param pokemon selected pokemon
   */
  private void setStatViews(Pokemon pokemon) {
    List<Stats> statsList = pokemon.getStatsList();

    if (statsList.get(0) != null) {
      binding.textViewStatsHp.setText(String.valueOf(statsList.get(0).getBaseStat()));
    }

    if (statsList.get(1) != null) {
      binding.textViewStatsAttack.setText(String.valueOf(statsList.get(1).getBaseStat()));
    }

    if (statsList.get(2) != null) {
      binding.textViewStatsDefense.setText(String.valueOf(statsList.get(2).getBaseStat()));
    }

    if (statsList.get(3) != null) {
      binding.textViewStatsSpecialAttack.setText(String.valueOf(statsList.get(3).getBaseStat()));
    }

    if (statsList.get(4) != null) {
      binding.textViewStatsSpecialDefense.setText(String.valueOf(statsList.get(4).getBaseStat()));
    }

    if (statsList.get(5) != null) {
      binding.textViewStatsSpeed.setText(String.valueOf(statsList.get(5).getBaseStat()));
    }

    if(getContext() != null) {
      float weightInKg = pokemon.getWeight() / 10f;
      binding.textViewStatsWeight.setText(String.format(Locale.getDefault(), "%.1f%s", weightInKg,
          getContext().getString(R.string.kilograms)));

      float heightInMeters = pokemon.getHeight() / 10f;
      binding.textViewStatsHeight.setText(String.format(Locale.getDefault(), "%.1f%s", heightInMeters,
          getContext().getString(R.string.meters)));
    }

    binding.textViewStatsBaseExperience.setText(String.valueOf(pokemon.getBaseExperience()));

  }

}