package pt.rfsfernandes.custom.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.utils.UtilsClass;
import pt.rfsfernandes.model.pokemon.types.PokemonType;

/**
 * Class TypesAdapter created at 1/19/21 00:42 for the project PoketDex
 * By: rodrigofernandes
 */
public class PokemonTypesAdapter extends BaseAdapter {
  private final Context context;
  private List<PokemonType> mPokemonTypes;

  public PokemonTypesAdapter(Context context) {
    this.context = context;
    this.mPokemonTypes = new ArrayList<>();
  }

  @Override
  public int getCount() {
    return mPokemonTypes.size();
  }

  @Override
  public PokemonType getItem(int position) {
    return mPokemonTypes.get(position);
  }

  @Override
  public long getItemId(int position) {
    return Integer.parseInt(this.mPokemonTypes.get(position).getType().getUrlId());
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.pokemon_type_row, parent, false);
    }

    PokemonType pokemonType = getItem(position);

    TextView textViewType = convertView.findViewById(R.id.textViewType);
    textViewType.setText(pokemonType.getType().getName());

    LinearLayout linearLayoutType = convertView.findViewById(R.id.linearLayoutType);
    int color = UtilsClass.returnColorId(context, pokemonType.getType().getName());

    Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),
        R.drawable.type_background,
        null);
    if (drawable != null) {
      drawable.setTint(color);
    }
    linearLayoutType.setBackground(drawable);

    return convertView;
  }

  /**
   * Assigns a value to the global List of PokemonType and notifies the adapter of that change
   *
   * @param pokemonTypes New list
   */
  public void refreshList(List<PokemonType> pokemonTypes) {
    this.mPokemonTypes = pokemonTypes;
    notifyDataSetChanged();
  }

}
