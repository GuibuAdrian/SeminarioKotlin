package com.avalith.seminariokotlin.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.avalith.seminariokotlin.R
import com.avalith.seminariokotlin.databinding.DialogLoadingBinding

class LoadingDialog(@StringRes private val string: Int = R.string.cargando): DialogFragment() {
    lateinit var dialogBinding: DialogLoadingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        return dialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogBinding.dialogLoadingTextView.setText(string)
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, null)
    }
}