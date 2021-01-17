package pt.rfsfernandes.custom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.R;
import pt.rfsfernandes.model.pokemon.moves.Moves;


public class PokemonMovesAdapter extends RecyclerView.Adapter<PokemonMovesAdapter.MovesViewHolder> {

  private List<Moves> mPokemonMoves = new ArrayList<>();

  public PokemonMovesAdapter() {
  }

  @Override
  public MovesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.moves_row, parent, false);
    return new MovesViewHolder(view);

  }

  @Override
  public void onBindViewHolder(final MovesViewHolder holder, int position) {

    populateItemRows(holder, position);
  }

  private void populateItemRows(MovesViewHolder holder, int position) {

    Moves item = mPokemonMoves.get(position);
    holder.mItem = item;
    holder.textViewPokemonName.setText(item.getMove().getName().replace("-", " "));

  }

  @Override
  public int getItemCount() {
    return mPokemonMoves.size();
  }

  public void refreshList(List<Moves> moves) {
    this.mPokemonMoves = moves;
    notifyDataSetChanged();
  }

  public class MovesViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView textViewPokemonName;
    public final View selectedView;
    public Moves mItem;

    public MovesViewHolder(View view) {
      super(view);
      mView = view;
      textViewPokemonName = view.findViewById(R.id.textViewMoves);
      selectedView = view.findViewById(R.id.viewSelect);
    }

  }
}