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
import pt.rfsfernandes.custom.adapters.TypesAdapter;
import pt.rfsfernandes.databinding.FragmentGeneralInfoBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.viewmodels.MainViewModel;


public class GeneralInfoFragment extends Fragment {
  private FragmentGeneralInfoBinding binding;
  private MainViewModel mMainViewModel;
  private MyApplication mMyApplication;
  private TypesAdapter mTypesAdapter;

  public GeneralInfoFragment() {
    // Required empty public constructor
  }

//  public static GeneralInfoFragment getInstance(MainViewModel mainViewModel) {
//    GeneralInfoFragment generalInfoFragment = new GeneralInfoFragment();
//    Bundle args = new Bundle();
//    arg
//    return new GeneralInfoFragment();
//  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    binding = FragmentGeneralInfoBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mMyApplication = (MyApplication) requireActivity().getApplication();
    mTypesAdapter = new TypesAdapter(requireContext());
    binding.pokemonTypeGridView.setAdapter(mTypesAdapter);
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

    mMainViewModel.getPokemonMutableLiveData().observe(getActivity(), pokemon -> {
      if (pokemon != null) {
        populateGeneralViews(pokemon);
        inflatePokemonTypes(pokemon);
        mMainViewModel.isLoading(false);
      }
    });

    mMainViewModel.getPokemonDescriptionLiveData().observe(getViewLifecycleOwner(),
        description -> {
          binding.textViewDescription.setText(description);
        });


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
    mTypesAdapter.refreshList(pokemon.getTypeList());
  }
}