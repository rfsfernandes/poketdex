package pt.rfsfernandes.custom.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.utils.UtilsClass;
import pt.rfsfernandes.model.moves.Moves;
import pt.rfsfernandes.model.pokemon_species.FlavourEntries;


public class PokemonMovesAdapter extends RecyclerView.Adapter<PokemonMovesAdapter.MovesViewHolder> {

  private List<Moves> mPokemonMoves = new ArrayList<>();
  private Context context;

  public PokemonMovesAdapter(Context context) {
    this.context = context;
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
    holder.textViewMoveTitle.setText(item.getName().replace("-", " "));

//    holder.textViewMoveType.setText(item.getType().getName());

    int color = UtilsClass.returnColorId(context, item.getType().getName());

    Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),
        R.drawable.type_background_round,
        null);

    if (drawable != null) {
      drawable.setTint(color);

    }

    holder.textViewMovePower.setBackground(drawable);

    String chosenFlavour = "";
    String country = Locale.getDefault().getLanguage().split("-")[0].toLowerCase();
    for (FlavourEntries flavour :
        item.getFlavourEntriesList()) {
      if (flavour.getLanguage().getName().contains(country)) {
        chosenFlavour = flavour.getFlavourText();

        break;
      }
    }

    if (chosenFlavour.isEmpty()) {
      for (FlavourEntries flavour :
          item.getFlavourEntriesList()) {
        if (flavour.getLanguage().getName().contains("en")) {
          chosenFlavour = flavour.getFlavourText();
          break;
        }
      }
    }

    holder.textViewMoveDescription.setText(chosenFlavour.replace("\n", " "));
    holder.textViewMoveEffect.setText(item.getEffectEntry().getEffect().replace("  ", " ").replace("$effect_chance%", String.valueOf(item.getEffectChance())));
    holder.textViewMoveShortEffect.setText(item.getEffectEntry().getShortEffect().replace("  ",
        " ").replace("$effect_chance%", String.valueOf(item.getEffectChance())));
    holder.textViewMovePower.setText(String.valueOf(item.getPower()));
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
    public final TextView textViewMoveTitle;
    public final TextView textViewMoveEffect;
    public final TextView textViewMoveShortEffect;
    public final TextView textViewMovePower;
    public final TextView textViewMoveDescription;
    public final View selectedView;
    public Moves mItem;

    public MovesViewHolder(View view) {
      super(view);
      mView = view;
      textViewMoveTitle = view.findViewById(R.id.textViewMoveTitle);

      textViewMoveEffect = view.findViewById(R.id.textViewMoveEffect);
      textViewMoveShortEffect = view.findViewById(R.id.textViewMoveShortEffect);
      textViewMovePower = view.findViewById(R.id.textViewMovePower);
      textViewMoveDescription = view.findViewById(R.id.textViewMoveDescription);

      selectedView = view.findViewById(R.id.viewSelect);
    }

  }
}