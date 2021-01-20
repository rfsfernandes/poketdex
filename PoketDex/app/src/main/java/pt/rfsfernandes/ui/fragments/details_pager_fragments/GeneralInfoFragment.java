package pt.rfsfernandes.ui.fragments.details_pager_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.custom.Constants;
import pt.rfsfernandes.custom.adapters.PokemonTypesAdapter;
import pt.rfsfernandes.databinding.FragmentGeneralInfoBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.ui.activities.MainActivity;
import pt.rfsfernandes.viewmodels.MainViewModel;


public class GeneralInfoFragment extends Fragment {
  private FragmentGeneralInfoBinding binding;
  private MainViewModel mMainViewModel;
  private MyApplication mMyApplication;
  private PokemonTypesAdapter mPokemonTypesAdapter;
  private boolean isGeneralInfoLoaded = false;
  private boolean isDescriptionLoaded = false;

  public GeneralInfoFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    binding = FragmentGeneralInfoBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mMyApplication = (MyApplication) requireActivity().getApplication();
    mPokemonTypesAdapter = new PokemonTypesAdapter(requireContext());
    binding.pokemonTypeGridView.setAdapter(mPokemonTypesAdapter);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();

    binding.pokemonTypeGridView.setOnItemClickListener((parent, view1, position, id) -> {
      mMainViewModel.isLoading(true);
      if (getActivity() != null) {
        ((MainActivity) getActivity()).setShowTypeInfo(true);
        mMainViewModel.getTypeAndCounters((int) id, Constants.SHOW_TYPE.POKEMON);
      }
    });

  }

  /**
   * Initiates viewModel observers
   */
  private void initViewModel() {

    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      if (pokemon != null) {

        populateGeneralViews(pokemon);
        inflatePokemonTypes(pokemon);

        isGeneralInfoLoaded = true;
        stopLoading();

      }
    });

    mMainViewModel.getPokemonDescriptionLiveData().observe(getViewLifecycleOwner(),
        description -> {
          binding.textViewDescription.setText(description);
          isDescriptionLoaded = true;
          stopLoading();
        });

  }

  /**
   * Stops the progressBar loading
   */
  private void stopLoading() {

    if (isDescriptionLoaded && isGeneralInfoLoaded) {
      mMainViewModel.isLoading(false);
      isDescriptionLoaded = false;
      isGeneralInfoLoaded = false;
    }

  }

  /**
   * Populates general views such as images and pokemon name
   *
   * @param pokemon selected pokemon
   */
  private void populateGeneralViews(Pokemon pokemon) {

    binding.imageViewPokemonDefault.setImageURI(pokemon.getSprites().getFrontDefault());
    binding.imageViewPokemonShiny.setImageURI(pokemon.getSprites().getFrontShiny());

  }

  /**
   * Inflates linearLayoutTypeContainer dynamically with pokemon types
   *
   * @param pokemon selected pokemon
   */
  private void inflatePokemonTypes(Pokemon pokemon) {
    mPokemonTypesAdapter.refreshList(pokemon.getTypeList());
  }
}