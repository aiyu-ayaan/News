package com.aiyu.news.ui.fragment.delete

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteSelectedDialog : DialogFragment() {

    private val viewModel: DeleteAllViewModel by viewModels()
    private val args: DeleteSelectedDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Do you want to delete ${args.articles.size}")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                args.articles.onEach {
                    viewModel.delete(it)
                }
                dismiss()

            }.create()
}