package pt.rfsfernandes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.databinding.FragmentPokemonDetailsBinding;
import pt.rfsfernandes.viewmodels.MainViewModel;


public class PokemonDetailsFragment extends Fragment {
  private FragmentPokemonDetailsBinding binding;
  private MainViewModel mMainViewModel;

  public PokemonDetailsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setProgressBarImage(new ProgressBarDrawable())
        .build());
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setProgressBarImage(new ProgressBarDrawable())
        .build());
    
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();
  }

  private void initViewModel() {
    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {


      binding.imageViewPokemonDefault.setImageURI(pokemon.getSprites().getFrontDefault());
      binding.imageViewPokemonShiny.setImageURI(pokemon.getSprites().getFrontShiny());
    });
  }

}