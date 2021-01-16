package pt.rfsfernandes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.custom.adapters.ItemListClicked;
import pt.rfsfernandes.custom.adapters.PokemonResultAdapter;
import pt.rfsfernandes.databinding.FragmentPokemonResultListBinding;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.viewmodels.MainViewModel;

/**
 * A fragment representing a list of Items.
 */
public class PokemonResultListFragment extends Fragment implements ItemListClicked<PokemonResult> {

  private MainViewModel mMainViewModel;
  private PokemonResultAdapter mPokemonResultAdapter;
  private FragmentPokemonResultListBinding binding;
  private List<PokemonResult> mPokemonResultList = new ArrayList<>();
  private boolean isLoading = false;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public PokemonResultListFragment() {
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = FragmentPokemonResultListBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mPokemonResultAdapter = new PokemonResultAdapter(this);

    binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.list.setAdapter(mPokemonResultAdapter);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViewModel();

//    if (((LinearLayoutManager) binding.list.getLayoutManager()).findLastCompletelyVisibleItemPosition() == mPokemonResultList.size() - 1) {
//      //bottom of list!
//      mMainViewModel.loadResults();
//    }

    binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

//        if (!isLoading) {
        if (!isLoading && linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == mPokemonResultList.size() - 1) {
          //bottom of list!
          mMainViewModel.loadResults();
          isLoading = true;
//            isLoading = true;
        }
//        }
      }
    });

  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(getViewLifecycleOwner(),
        results -> {
          isLoading = false;
          mPokemonResultList = results;
          mPokemonResultAdapter.refreshList(results);
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onClick(PokemonResult object) {
    Toast.makeText(requireContext(), object.getName(), Toast.LENGTH_SHORT).show();
  }
}