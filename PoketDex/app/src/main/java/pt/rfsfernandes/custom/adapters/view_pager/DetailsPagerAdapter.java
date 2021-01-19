package pt.rfsfernandes.custom.adapters.view_pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import pt.rfsfernandes.R;
import pt.rfsfernandes.ui.fragments.details_pager_fragments.GeneralInfoFragment;
import pt.rfsfernandes.ui.fragments.details_pager_fragments.MovesInfoFragment;
import pt.rfsfernandes.ui.fragments.details_pager_fragments.StatsInfoFragment;

/**
 * Class HeadPagerAdapter created at 1/19/21 00:19 for the project PoketDex
 * By: rodrigofernandes
 */
public class DetailsPagerAdapter extends FragmentStatePagerAdapter {
  private Context mContext;

  public DetailsPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
    super(fm, behavior);
    this.mContext = context;
  }


  @NonNull
  @Override
  public Fragment getItem(int position) {
    Fragment fragment;

    if (position == 0) {
      fragment = new GeneralInfoFragment();
    } else if (position == 1) {
      fragment = new StatsInfoFragment();
    } else {
      fragment = new MovesInfoFragment();
    }

    return fragment;
  }

  @Override
  public int getCount() {

    return 3;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    super.getPageTitle(position);
    String title = "";
    if(mContext != null) {
      switch (position) {
        case 0:
          title = mContext.getString(R.string.general_info);
          break;
        case 1:
          title = mContext.getResources().getString(R.string.stats);
          break;
        case 2:
          title = mContext.getResources().getString(R.string.moves);
          break;
      }
//      if(position == 0) {
//        title = mContext.getString(R.string.general_info);
//      } else if(position == 1) {
//        title = mContext.getResources().getString(R.string.stats);
//      }if(position == 2) {
//        title = mContext.getResources().getString(R.string.moves);
//      }
    }
    return title;
  }
}
