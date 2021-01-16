package pt.rfsfernandes.custom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.R;
import pt.rfsfernandes.model.service_responses.PokemonResult;


public class PokemonResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private final int VIEW_TYPE_ITEM = 0;
  private final int VIEW_TYPE_LOADING = 1;
  private List<PokemonResult> mPokemonResultList = new ArrayList<>();
  private ItemListClicked<PokemonResult> callback;
  private Context mContext;

  public PokemonResultAdapter(Context context,
                              ItemListClicked<PokemonResult> pokemonResultItemListClicked) {
    this.callback = pokemonResultItemListClicked;
    this.mContext = context;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_ITEM) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.pokemon_result_row, parent, false);
      return new PokemonResultViewHolder(view);
    } else {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_row, parent, false);
      return new LoadingViewHolder(view);
    }


  }

  @Override
  public int getItemViewType(int position) {
    return mPokemonResultList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
  }

  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
    if(holder instanceof PokemonResultViewHolder) {
      populateItemRows((PokemonResultViewHolder) holder, position);
    }

  }

  private void populateItemRows(PokemonResultViewHolder holder, int position) {

    PokemonResult item = mPokemonResultList.get(position);
    holder.mItem = item;
    holder.textViewPokemonName.setText(item.getName());
    holder.mSimpleDraweeView.setImageURI(item.getPokemonImage());
    holder.mView.setOnClickListener(e -> callback.onClick(item));

  }

  @Override
  public int getItemCount() {
    return mPokemonResultList.size();
  }

  public class PokemonResultViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView textViewPokemonName;
    public final SimpleDraweeView mSimpleDraweeView;
    public PokemonResult mItem;

    public PokemonResultViewHolder(View view) {
      super(view);
      mView = view;
      textViewPokemonName = (TextView) view.findViewById(R.id.textViewPokemonName);
      mSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.imageViewPokemonList);
      mSimpleDraweeView.setHierarchy(new GenericDraweeHierarchyBuilder(mContext.getResources())
          .setProgressBarImage(new ProgressBarDrawable())
          .build());
    }

  }

  private class LoadingViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
      super(itemView);
      progressBar = itemView.findViewById(R.id.progressBar);
    }
  }

  public void refreshList(List<PokemonResult> pokemonResults) {
    this.mPokemonResultList = pokemonResults;
    notifyDataSetChanged();
  }
}