package pt.rfsfernandes.ui.fragments.details_pager_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.adapters.PokemonMovesAdapter;
import pt.rfsfernandes.databinding.FragmentMovesInfoBinding;
import pt.rfsfernandes.databinding.FragmentStatsInfoBinding;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MovesInfoFragment extends Fragment {

  private FragmentMovesInfoBinding binding;
  private MainViewModel mMainViewModel;
  private PokemonMovesAdapter mPokemonMovesAdapter;

  public MovesInfoFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = FragmentMovesInfoBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mPokemonMovesAdapter = new PokemonMovesAdapter(getContext());
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    binding.recyclerViewMovesInfo.setLayoutManager(linearLayoutManager);
    binding.recyclerViewMovesInfo.setAdapter(mPokemonMovesAdapter);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();
  }

  private void initViewModel(){
    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      mMainViewModel.getMovesFromIds(pokemon.getMoveslist());
    });

    mMainViewModel.getMovesInfo().observe(getViewLifecycleOwner(), moves -> {
      mPokemonMovesAdapter.refreshList(moves);
    });
  }
}