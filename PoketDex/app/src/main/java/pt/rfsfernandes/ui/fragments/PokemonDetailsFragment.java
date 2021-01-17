package pt.rfsfernandes.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.utils.UtilsClass;
import pt.rfsfernandes.databinding.FragmentPokemonDetailsBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon.stats.Stats;
import pt.rfsfernandes.model.pokemon.types.PokemonType;
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
    binding.frameLayout.setVisibility(View.GONE);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();
  }

  private void initViewModel() {
    mMainViewModel.getIsLoadingMutableLiveData().observe(getViewLifecycleOwner(), isLoading -> {
      binding.content.setVisibility(isLoading ? View.GONE : View.VISIBLE);
      binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    });

    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      if (pokemon != null) {
        populateGeneralViews(pokemon);
        inflatePokemonTypes(pokemon);
        setStatViews(pokemon);
        mMainViewModel.isLoading(false);
      }

    });
  }

  private void populateGeneralViews(Pokemon pokemon) {
    String pokemonName = UtilsClass.toCamelCase(pokemon.getName());
    binding.frameLayout.setVisibility(View.VISIBLE);
    binding.textViewDefault.setText(pokemonName);

    binding.textViewShiny.setText(String.format("%s (%s)", pokemonName,
        getResources().getString(R.string.shiny)));

    RoundingParams roundingParams = RoundingParams.fromCornersRadius(8f);
    roundingParams.setBorderColor(getResources().getColor(R.color.colorAccent, null));



    binding.imageViewPokemonDefault.setImageURI(pokemon.getSprites().getFrontDefault());
    binding.imageViewPokemonShiny.setImageURI(pokemon.getSprites().getFrontShiny());
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setRoundingParams(roundingParams)
        .build());

    binding.imageViewPokemonShiny.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setRoundingParams(roundingParams)
        .build());
  }

  private void inflatePokemonTypes(Pokemon pokemon) {
    binding.linearLayoutTypeContainer.removeAllViews();
    for (PokemonType type :
        pokemon.getTypeList()) {
      View view = getLayoutInflater().inflate(R.layout.pokemon_type_row,
          binding.linearLayoutTypeContainer, false);
      int color = UtilsClass.returnColorId(requireContext(), type.getType().getName());
      LinearLayout linearLayoutType =
          (LinearLayout) view.findViewById(R.id.linearLayoutType);
      Drawable drawable = getResources().getDrawable(R.drawable.type_background, null);
      drawable.setTint(color);
      linearLayoutType.setBackground(drawable);
      TextView textView =
          (TextView) view.findViewById(R.id.textViewType);
      textView.setText(type.getType().getName());

      binding.linearLayoutTypeContainer.addView(view);

    }
  }

  private void setStatViews(Pokemon pokemon) {
    List<Stats> statsList = pokemon.getStatsList();

    if (statsList.get(0) != null) {
      binding.textViewStatHp.setText(String.valueOf(statsList.get(0).getBaseStat()));
    }

    if (statsList.get(1) != null) {
      binding.textViewStatAttack.setText(String.valueOf(statsList.get(1).getBaseStat()));
    }

    if (statsList.get(2) != null) {
      binding.textViewStatDefense.setText(String.valueOf(statsList.get(2).getBaseStat()));
    }

    if (statsList.get(3) != null) {
      binding.textViewStatSpecialAttack.setText(String.valueOf(statsList.get(3).getBaseStat()));
    }

    if (statsList.get(4) != null) {
      binding.textViewStatSpecialDefense.setText(String.valueOf(statsList.get(4).getBaseStat()));
    }

    if (statsList.get(5) != null) {
      binding.textViewStatSpeed.setText(String.valueOf(statsList.get(5).getBaseStat()));
    }



  }

}