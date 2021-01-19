package pt.rfsfernandes.custom.adapters.view_pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import pt.rfsfernandes.ui.fragments.details_pager_fragments.GeneralInfoFragment;
import pt.rfsfernandes.ui.fragments.details_pager_fragments.StatsInfoFragment;

/**
 * Class HeadPagerAdapter created at 1/19/21 00:19 for the project PoketDex
 * By: rodrigofernandes
 */
public class DetailsPagerAdapter extends FragmentStatePagerAdapter {


  public DetailsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
    super(fm, behavior);
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
      fragment = new GeneralInfoFragment();
    }

    return fragment;
  }

  @Override
  public int getCount() {

    return 3;
  }
}
