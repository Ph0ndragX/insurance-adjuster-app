package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentNoteNewBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import dev.ph0ndragx.insuranceadjusterapp.model.Note
import java.util.*

class NewNoteDialogFragment : DialogFragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance }

    private var _binding: ActivityInspectionRequestFragmentNoteNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentNoteNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentDialogNoteNewToolbar.apply {
            inflateMenu(R.menu.fragment_dialog_note_new)
            title = resources.getString(R.string.activity_inspection_request_new_note_title)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.fragment_dialog_note_new_save -> {
                        model.addNote(
                            model.inspectionRequest().value,
                            Note(
                                binding.fragmentDialogNoteNewContent.text.toString(),
                                "Gall Anonim", // TODO(psmolarski) - fill username
                                Calendar.getInstance().time
                            )
                        )
                        dismiss()
                        true
                    }
                    else -> {
                        dismiss()
                        true
                    }
                }
            }
            setNavigationOnClickListener {
                dismiss()
            }
        }

        binding.fragmentDialogNoteNewContent.apply {
            setOnFocusChangeListener { _, _ ->
                dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }

        binding.fragmentDialogNoteNewContent.requestFocus()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    companion object {
        fun display(fm: FragmentManager): DialogFragment {
            val dialog = NewNoteDialogFragment()
            dialog.show(fm, "NewNoteDialogFragment")
            return dialog
        }
    }
}
