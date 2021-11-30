package com.nanlagger.packinglist.features.editName.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.nanlagger.packinglist.core.common.BaseDialogFragment
import com.nanlagger.packinglist.features.editName.R
import com.nanlagger.packinglist.features.editName.databinding.FragmentEditNameBinding
import com.nanlagger.packinglist.features.editName.di.EditNameComponentHolder
import com.nanlagger.utils.viewbinding.viewBinding
import javax.inject.Inject

class EditNameDialog : BaseDialogFragment() {

    @Inject
    lateinit var factory: EditNameViewModel.Factory

    private val viewModel: EditNameViewModel by viewModels { factory }

    private var binding: FragmentEditNameBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EditNameComponentHolder.createOrGetComponent(screenName, parentScreenName).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onAttach()
        viewModel.info.observe(viewLifecycleOwner) {
            dialog?.setTitle(it.title)
            binding?.editName?.setText(it.name)
        }
        binding?.editName?.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.saveName(binding?.editName?.text?.toString() ?: "")
                dismissAllowingStateLoss()
                true
            } else {
                false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentEditNameBinding.inflate(LayoutInflater.from(requireContext())).also { binding = it }
        return AlertDialog.Builder(requireActivity(), theme)
            .setTitle(R.string.edit_name_input_name_hint)
            .setView(binding.root)
            .setPositiveButton(R.string.save) { _, _ -> viewModel.saveName(binding.editName.text?.toString() ?: "") }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        EditNameComponentHolder.deleteComponent(screenName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(): EditNameDialog {
            return EditNameDialog()
        }
    }
}