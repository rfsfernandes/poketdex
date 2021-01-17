package pt.rfsfernandes.custom.adapters.item_decorators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Class CustomItemDecorator created at 1/17/21 16:53 for the project PoketDex
 * By: rodrigofernandes
 */
public class CustomItemDecorator extends RecyclerView.ItemDecoration {

  private final Drawable divider;

  public CustomItemDecorator(Context context, int resId) {
    divider = context.getResources().getDrawable(resId, null);
  }


  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();

    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount - 1; i++) {
      View child = parent.getChildAt(i);

      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

      int top = child.getBottom() + params.bottomMargin;
      int bottom = top + divider.getIntrinsicHeight();

      //divider.setBounds(left, top, right, bottom);
      divider.setBounds(top, left, bottom, right);
      divider.draw(c);
    }
  }

}
