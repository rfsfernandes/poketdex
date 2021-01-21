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
import pt.rfsfernandes.model.SimpleModelData;

/**
 * Class TypesAdapter created at 1/19/21 00:42 for the project PoketDex
 * By: rodrigofernandes
 */
public class TypesAdapter extends BaseAdapter {
  private final Context context;
  private List<SimpleModelData> mModelData;

  public TypesAdapter(Context context) {
    this.context = context;
    this.mModelData = new ArrayList<>();
  }

  @Override
  public int getCount() {
    return mModelData.size();
  }

  @Override
  public SimpleModelData getItem(int position) {
    return mModelData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.pokemon_type_row, parent, false);
    }

    SimpleModelData typeData = getItem(position);

    TextView textViewType = convertView.findViewById(R.id.textViewType);
    textViewType.setText(typeData.getName());

    LinearLayout linearLayoutType = convertView.findViewById(R.id.linearLayoutType);
    int color = UtilsClass.returnColorId(context, typeData.getName());

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
   * Assigns a value to the global List of ModelData and notifies the adapter of that change
   *
   * @param pokemonTypes New list
   */
  public void refreshList(List<SimpleModelData> pokemonTypes) {
    this.mModelData = pokemonTypes;
    notifyDataSetChanged();
  }

}
