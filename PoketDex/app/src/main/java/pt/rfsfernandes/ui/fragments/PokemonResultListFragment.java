package pt.rfsfernandes.ui.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.custom.adapters.ItemListClicked;
import pt.rfsfernandes.custom.adapters.PokemonResultAdapter;
import pt.rfsfernandes.databinding.FragmentPokemonResultListBinding;
import pt.rfsfernandes.model.service_responses.PokemonResult;
import pt.rfsfernandes.ui.activities.MainActivity;
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
  private MediaPlayer mMediaPlayer;

  public PokemonResultListFragment() {
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = FragmentPokemonResultListBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    mPokemonResultAdapter = new PokemonResultAdapter(requireContext(), this);

    binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.list.setAdapter(mPokemonResultAdapter);
    if (getActivity() != null) {
      mMediaPlayer = ((MyApplication) getActivity().getApplication()).getMediaPlayerMenuSound();
    }

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.progressBarResultList.setVisibility(View.VISIBLE);

    initViewModel();

    binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (!isLoading && linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == mPokemonResultList.size() - 1) {

          mMainViewModel.loadResults();
          isLoading = true;

        }

      }
    });

  }

  private void initViewModel() {
    mMainViewModel.getPokemonListResponseMutableLiveData().observe(getViewLifecycleOwner(),
        results -> {
          binding.progressBarResultList.setVisibility(View.GONE);
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
    if(mMediaPlayer != null) {
      mMediaPlayer.start();
    }
    mMainViewModel.setSelected(object.getListPosition());
    if (getActivity() != null && getActivity() instanceof MainActivity) {
      ((MainActivity) getActivity()).onItemClick(object.getListPosition());
    }
    mPokemonResultAdapter.notifyDataSetChanged();

  }
}