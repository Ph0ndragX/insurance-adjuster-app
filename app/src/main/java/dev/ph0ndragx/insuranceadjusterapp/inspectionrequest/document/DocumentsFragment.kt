package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.document

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.common.FILE_SELECT_CODE
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentDocumentListBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import dev.ph0ndragx.insuranceadjusterapp.model.Document
import kotlinx.android.synthetic.main.activity_inspection_request.*
import java.io.FileNotFoundException
import java.util.*


class DocumentsFragment : Fragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance }

    private var _binding: ActivityInspectionRequestFragmentDocumentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: DocumentsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentDocumentListBinding.inflate(inflater, container, false)

        recyclerView = DocumentsRecyclerViewAdapter()

        with(binding.root as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerView
        }

        model.inspectionRequest().observe(viewLifecycleOwner, Observer {
            recyclerView.updateData(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as InspectionRequestActivity
        shareFab(activity.fab)
    }

    private fun shareFab(fab: FloatingActionButton) {
        fab.apply {
            show()
            setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    addCategory(Intent.CATEGORY_DEFAULT)
                }

                startActivityForResult(
                    Intent.createChooser(intent, null).apply {
                        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        ))
                    },
                    FILE_SELECT_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            FILE_SELECT_CODE -> {

                var fileName = ""

                data?.data?.also { returnUri ->
                    /* val inputPFD = */ try {
                        requireActivity().contentResolver.query(returnUri, null, null, null, null)?.use {
                            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            it.moveToFirst()
                            fileName = it.getString(nameIndex)
                        }
                        requireActivity().contentResolver.openFileDescriptor(returnUri, "r")
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        Log.e("MainActivity", "File not found.")
                        return
                    }

                    // val inputStream = FileInputStream(inputPFD.fileDescriptor) TODO(psmolarski) read bytes and upload to server

                    model.addDocument(
                        model.inspectionRequest().value!!,
                        Document(
                            fileName,
                            "Kermit", // TODO(psmolarki) fill username,
                            Date()
                        )
                    )
                }
            }
        }
    }
}
