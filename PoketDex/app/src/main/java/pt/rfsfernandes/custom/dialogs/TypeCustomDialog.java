package pt.rfsfernandes.custom.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import pt.rfsfernandes.R;
import pt.rfsfernandes.custom.adapters.PokemonTypesAdapter;
import pt.rfsfernandes.custom.adapters.TypesAdapter;
import pt.rfsfernandes.custom.utils.UtilsClass;

/**
 * Class CustomDialogClass created at 1/20/21 00:22 for the project PoketDex
 * By: rodrigofernandes
 */
public class TypeCustomDialog extends Dialog implements
    View.OnClickListener {

  private Activity c;
  private GridView gridViewDoubleDamage;
  private GridView gridViewHalfDamage;
  private GridView gridViewNoDamage;
  private View includeTypeRow;
  private TextView textViewComparisonType;

  private TypesAdapter mPokemonTypesAdapterDoubleDamage;
  private TypesAdapter mPokemonTypesAdapterHalfDamage;
  private TypesAdapter mPokemonTypesAdapterNoDamage;

  public TypesAdapter getPokemonTypesAdapterDoubleDamage() {
    return mPokemonTypesAdapterDoubleDamage;
  }

  public TypesAdapter getPokemonTypesAdapterHalfDamage() {
    return mPokemonTypesAdapterHalfDamage;
  }

  public TypesAdapter getPokemonTypesAdapterNoDamage() {
    return mPokemonTypesAdapterNoDamage;
  }

  public TextView getTextViewComparisonType() {
    return textViewComparisonType;
  }

  public Activity getC() {
    return c;
  }


  public TypeCustomDialog(Activity a) {
    super(a);

    this.c = a;

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.type_dialog_layout);
    Button close = (Button) findViewById(R.id.buttonClose);

    gridViewDoubleDamage = (GridView) findViewById(R.id.gridViewDoubleDamage);
    gridViewHalfDamage = (GridView) findViewById(R.id.gridViewHalfDamage);
    gridViewNoDamage = (GridView) findViewById(R.id.gridViewNoDamage);
    includeTypeRow = (View) findViewById(R.id.includeTypeRow);

    textViewComparisonType = (TextView) findViewById(R.id.textViewComparisonType);

    mPokemonTypesAdapterDoubleDamage = new TypesAdapter(getContext());
    mPokemonTypesAdapterHalfDamage = new TypesAdapter(getContext());
    mPokemonTypesAdapterNoDamage = new TypesAdapter(getContext());

    gridViewDoubleDamage.setAdapter(mPokemonTypesAdapterDoubleDamage);
    gridViewHalfDamage.setAdapter(mPokemonTypesAdapterHalfDamage);
    gridViewNoDamage.setAdapter(mPokemonTypesAdapterNoDamage);

    close.setOnClickListener(this);
  }

  public void setType(String type){
    TextView textViewType = includeTypeRow.findViewById(R.id.textViewType);
    textViewType.setText(type);

    LinearLayout linearLayoutType = includeTypeRow.findViewById(R.id.linearLayoutType);
    int color = UtilsClass.returnColorId(c, type);

    Drawable drawable = ResourcesCompat.getDrawable(c.getResources(),
        R.drawable.type_background,
        null);
    if (drawable != null) {
      drawable.setTint(color);
    }
    linearLayoutType.setBackground(drawable);
  }

  @Override
  public void onClick(View v) {
    dismiss();
  }
}
