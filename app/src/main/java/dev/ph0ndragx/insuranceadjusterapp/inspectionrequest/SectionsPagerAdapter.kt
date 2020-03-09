package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.document.DocumentsFragment
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspectionrequest.InspectionRequestFragment
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.note.NotesFragment

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val fab: FloatingActionButton
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), ViewPager.OnPageChangeListener {

    interface FabFragment {
        fun shareFab(fab: FloatingActionButton)
    }

    class TabDefinition(
        val title: Int,
        val ctor: (pos: Int) -> Fragment
    )

    private val createdFragments: MutableMap<Int, Fragment> = HashMap()

    override fun getItem(position: Int): Fragment {
        if (!createdFragments.containsKey(position)) {
            createdFragments[position] = TABS[position].ctor(position)
        }

        return createdFragments[position]!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TABS[position].title)
    }

    override fun getCount(): Int {
        return TABS.size
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val fragment = getItem(position)
        if (fragment is FabFragment) {
            fragment.shareFab(fab)
        } else {
            fab.hide()
        }
    }

    companion object {
        private val TABS = arrayOf(
            TabDefinition(R.string.activity_inspection_request_tab_inspection_request) {
                InspectionRequestFragment()
            },
            TabDefinition(R.string.activity_inspection_request_tab_claim) {
                PlaceholderFragment()
            },
            TabDefinition(R.string.activity_inspection_request_tab_inspection) {
                PlaceholderFragment()
            },
            TabDefinition(R.string.activity_inspection_request_tab_documents) {
                DocumentsFragment()
            },
            TabDefinition(R.string.activity_inspection_request_tab_notes) {
                NotesFragment()
            }
        )
    }
}