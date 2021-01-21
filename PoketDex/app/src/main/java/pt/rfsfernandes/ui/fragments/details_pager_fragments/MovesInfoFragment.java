package pt.rfsfernandes.ui.fragments.details_pager_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.custom.Constants;
import pt.rfsfernandes.custom.adapters.PokemonMovesAdapter;
import pt.rfsfernandes.custom.callbacks.MovesItemClick;
import pt.rfsfernandes.custom.dialogs.SimpleCustomDialog;
import pt.rfsfernandes.databinding.FragmentMovesInfoBinding;
import pt.rfsfernandes.ui.activities.MainActivity;
import pt.rfsfernandes.viewmodels.MainViewModel;

public class MovesInfoFragment extends Fragment implements MovesItemClick {

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
    mPokemonMovesAdapter = new PokemonMovesAdapter(getContext(), this);

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

  /**
   * Initiates the viewModel observers
   */
  private void initViewModel() {
    mMainViewModel.getPokemonMutableLiveData().observe(getViewLifecycleOwner(), pokemon -> {
      mMainViewModel.isLoading(true);
      mMainViewModel.getMovesFromIds(pokemon.getMoveslist());
    });

    mMainViewModel.getMovesInfo().observe(getViewLifecycleOwner(), moves -> {
      mPokemonMovesAdapter.refreshList(moves);
      mMainViewModel.isLoading(false);
    });
  }

  @Override
  public void clickedMovesItem(String title, String text, Constants.MOVES_ITEM moves_item) {
    mMainViewModel.isLoading(true);
    switch (moves_item) {
      case TYPE:
        if (getActivity() != null) {
          mMainViewModel.isLoading(true);
          ((MainActivity) getActivity()).setShowTypeInfo(true);
          mMainViewModel.getTypeAndCounters(Integer.parseInt(text), Constants.SHOW_TYPE.MOVE);
        }
        break;
      case SIMPLE:
        showSimpleDialogContent(title, text);
        break;
    }
  }

  /**
   * Shows a simple custom dialog with a title and a content
   *
   * @param title   Title of the dialog
   * @param content Content of the dialog
   */
  private void showSimpleDialogContent(String title, String content) {
    SimpleCustomDialog simpleCustomDialog = new SimpleCustomDialog(getActivity());
    simpleCustomDialog.show();
    simpleCustomDialog.getTextViewSimpleTitle().setText(title);
    simpleCustomDialog.getTextViewSimpleText().setText(content);
    mMainViewModel.isLoading(false);
  }

}