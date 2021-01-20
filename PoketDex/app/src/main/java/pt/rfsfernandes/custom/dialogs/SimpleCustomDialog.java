package pt.rfsfernandes.custom.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import pt.rfsfernandes.R;
import pt.rfsfernandes.databinding.SimpleDialogLayoutBinding;

/**
 * Class CustomDialogClass created at 1/20/21 00:22 for the project PoketDex
 * By: rodrigofernandes
 */
public class SimpleCustomDialog extends Dialog implements
    android.view.View.OnClickListener {

  private SimpleDialogLayoutBinding binding;
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
    binding = SimpleDialogLayoutBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();

    this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(view);
    Button close = binding.buttonClose;
    textViewSimpleTitle = binding.textViewSimpleTitle;
    textViewSimpleText = binding.textViewSimpleText;
    close.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    dismiss();
  }
}
