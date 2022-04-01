package pt.rfsfernandes.pocketdex.custom.adapters.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.GeneralInfoFragment
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.MovesInfoFragment
import pt.rfsfernandes.pocketdex.ui.fragments.details_pager_fragments.StatsInfoFragment

/**
 * Class HeadPagerAdapter created at 1/19/21 00:19 for the project PoketDex
 * By: rodrigofernandes
 */
class DetailsPagerAdapter(val fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 3
    }

    fun getPageTitle(position: Int): CharSequence {
        var title = ""
        when (position) {
            0 -> title = fa.getString(R.string.general_info)
            1 -> title = fa.resources.getString(R.string.stats)
            2 -> title = fa.resources.getString(R.string.moves)
        }
        return title
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> {
                GeneralInfoFragment()
            }
            1 -> {
                StatsInfoFragment()
            }
            else -> {
                MovesInfoFragment()
            }
        }
        return fragment
    }
}