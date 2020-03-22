package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.document.DocumentsFragment
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspectionrequest.InspectionRequestFragment
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.note.NotesFragment

class SectionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    class TabDefinition(
        val title: Int,
        val ctor: (pos: Int) -> Fragment
    )

    override fun getItemCount(): Int = TABS.size

    override fun createFragment(position: Int): Fragment {
        return TABS[position].ctor(position)
    }

    fun getTitle(position: Int): Int = TABS[position].title

    companion object {
        private val TABS = arrayOf(
            TabDefinition(R.string.activity_inspection_request_tab_inspection_request) {
                InspectionRequestFragment()
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