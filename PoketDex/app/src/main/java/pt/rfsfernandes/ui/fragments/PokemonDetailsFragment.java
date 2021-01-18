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
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.adapters.PokemonMovesAdapter;
import pt.rfsfernandes.custom.utils.UtilsClass;
import pt.rfsfernandes.databinding.FragmentPokemonDetailsBinding;
import pt.rfsfernandes.model.pokemon.Pokemon;
import pt.rfsfernandes.model.pokemon.abilities.Ability;
import pt.rfsfernandes.model.pokemon.stats.Stats;
import pt.rfsfernandes.model.pokemon.types.PokemonType;
import pt.rfsfernandes.viewmodels.MainViewModel;


public class PokemonDetailsFragment extends Fragment {
  private FragmentPokemonDetailsBinding binding;
  private MainViewModel mMainViewModel;
  private PokemonMovesAdapter mPokemonMovesAdapter;
  private MyApplication mMyApplication;

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
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setProgressBarImage(new ProgressBarDrawable())
        .build());
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setProgressBarImage(new ProgressBarDrawable())
        .build());
    if (mMyApplication.isLandscape()) {
      binding.frameLayout.setVisibility(View.GONE);
    }
    mPokemonMovesAdapter = new PokemonMovesAdapter();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    binding.recyclerViewMoves.setLayoutManager(linearLayoutManager);
    binding.recyclerViewMoves.setAdapter(mPokemonMovesAdapter);
    RoundingParams roundingParams = RoundingParams.fromCornersRadius(16f);
    roundingParams.setBorderColor(getResources().getColor(R.color.pokemon_red, null));
    roundingParams.setBorderWidth(5f);
    binding.imageViewPokemonDefault.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setRoundingParams(roundingParams)
        .build());

    binding.imageViewPokemonShiny.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
        .setRoundingParams(roundingParams)
        .build());
    binding.progressBar.setVisibility(View.VISIBLE);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
      binding.content.setVisibility(isLoading ? View.GONE : View.VISIBLE);
      binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    });

    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      if (pokemon != null) {
        populateGeneralViews(pokemon);
        inflatePokemonTypes(pokemon);
        setStatViews(pokemon);
        fillMovesAndAbilities(pokemon);
        mMainViewModel.isLoading(false);
        mMainViewModel.getPokemonDescriptionLiveData().observe(getViewLifecycleOwner(),
            description -> {
              binding.textViewDescription.setText(description);
            });
      }
    });


  }

  /**
   * Populates general views such as images and pokemon name
   *
   * @param pokemon selected pokemon
   */
  private void populateGeneralViews(Pokemon pokemon) {
    String pokemonName = UtilsClass.toCamelCase(pokemon.getName());
    binding.frameLayout.setVisibility(View.VISIBLE);
    binding.textViewDefault.setText(pokemonName);
    binding.textViewShiny.setText(String.format("%s (%s)", pokemonName,
        getResources().getString(R.string.shiny)));

    binding.imageViewPokemonDefault.setImageURI(pokemon.getSprites().getFrontDefault());
    binding.imageViewPokemonShiny.setImageURI(pokemon.getSprites().getFrontShiny());

  }

  /**
   * Inflates linearLayoutTypeContainer dynamically with pokemon types
   *
   * @param pokemon selected pokemon
   */
  private void inflatePokemonTypes(Pokemon pokemon) {
    binding.linearLayoutTypeContainer.removeAllViews();
    for (PokemonType type :
        pokemon.getTypeList()) {
      View view = getLayoutInflater().inflate(R.layout.pokemon_type_row,
          binding.linearLayoutTypeContainer, false);
      int color = UtilsClass.returnColorId(requireContext(), type.getType().getName());
      LinearLayout linearLayoutType =
          view.findViewById(R.id.linearLayoutType);
      Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.type_background,
          null);
      if (drawable != null) {
        drawable.setTint(color);
      }
      linearLayoutType.setBackground(drawable);
      TextView textView =
          view.findViewById(R.id.textViewType);
      textView.setText(type.getType().getName());

      binding.linearLayoutTypeContainer.addView(view);

    }
  }

  /**
   * Sets each pokemon stat
   *
   * @param pokemon selected pokemon
   */
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

    float weightInKg = pokemon.getWeight() / 10f;
    binding.textViewStatWeight.setText(String.format(Locale.getDefault(), "%.1f%s", weightInKg, getString(R.string.kilograms)));

    float heightInMeters = pokemon.getHeight() / 10f;
    binding.textViewStatHeight.setText(String.format(Locale.getDefault(), "%.1f%s", heightInMeters,
        getString(R.string.meters)));

    binding.textViewBaseExperience.setText(String.valueOf(pokemon.getBaseExperience()));

  }

  /**
   * inflates dynamically linearLayoutAbilitiesContainer with each ability and refreshes the
   * moves adapter with the selected pokemon moves
   *
   * @param pokemon selected pokemon
   */
  private void fillMovesAndAbilities(Pokemon pokemon) {
    binding.linearLayoutAbilitiesContainer.removeAllViews();
    mPokemonMovesAdapter.refreshList(pokemon.getMoveslist());
    for (Ability ability :
        pokemon.getAbilitiesList()) {
      View view = getLayoutInflater().inflate(R.layout.ability_row,
          binding.linearLayoutAbilitiesContainer, false);

      TextView textViewAbilityName =
          view.findViewById(R.id.textViewAbilityName);
      textViewAbilityName.setText(UtilsClass.toCamelCase(ability.getAbility().getName()));

      TextView textViewAbilitySlot =
          view.findViewById(R.id.textViewAbilitySlot);

      view.findViewById(R.id.textViewAbilityHidden).setVisibility(ability.isHidden() ? View.INVISIBLE : View.VISIBLE);

      textViewAbilitySlot.setText(String.format("%s %s", getString(R.string.slot_n), ability.getSlot()));

      binding.linearLayoutAbilitiesContainer.addView(view);

    }
  }

}