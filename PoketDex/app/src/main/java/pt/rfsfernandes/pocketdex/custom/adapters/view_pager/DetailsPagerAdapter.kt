package pt.rfsfernandes.pocketdex.custom.adapters.view_pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.GeneralInfoFragment
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.MovesInfoFragment
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.StatsInfoFragment

/**
 * Class HeadPagerAdapter created at 1/19/21 00:19 for the project PoketDex
 * By: rodrigofernandes
 */
class DetailsPagerAdapter(fm: FragmentManager, behavior: Int, private val mContext: Context?) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        fragment = if (position == 0) {
            GeneralInfoFragment()
        } else if (position == 1) {
            StatsInfoFragment()
        } else {
            MovesInfoFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        super.getPageTitle(position)
        var title = ""
        if (mContext != null) {
            when (position) {
                0 -> title = mContext.getString(R.string.general_info)
                1 -> title = mContext.resources.getString(R.string.stats)
                2 -> title = mContext.resources.getString(R.string.moves)
            }
        }
        return title
    }
}