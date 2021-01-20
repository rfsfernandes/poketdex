package pt.rfsfernandes.custom.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import pt.rfsfernandes.R;

/**
 * Class CustomDialogClass created at 1/20/21 00:22 for the project PoketDex
 * By: rodrigofernandes
 */
public class SimpleCustomDialog extends Dialog implements
    android.view.View.OnClickListener {

  private Activity c;
  private TextView textViewSimpleTitle;
  private TextView textViewSimpleText;

  public TextView getTextViewSimpleTitle() {
    return textViewSimpleTitle;
  }

  public TextView getTextViewSimpleText() {
    return textViewSimpleText;
  }

  public SimpleCustomDialog(Activity a) {
    super(a);

    this.c = a;

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.simple_dialog_layout);
    Button close = (Button) findViewById(R.id.buttonClose);
    textViewSimpleTitle = (TextView) findViewById(R.id.textViewSimpleTitle);
    textViewSimpleText = (TextView) findViewById(R.id.textViewSimpleText);
    close.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    dismiss();
  }
}
