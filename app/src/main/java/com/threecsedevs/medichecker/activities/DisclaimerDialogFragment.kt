package com.threecsedevs.medichecker.activities

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.threecsedevs.medichecker.R

class DisclaimerDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val disclaimerText = "All of the information that our application provide " +
                "is to provide users with information to better understand their health and their medications," +
                " and IS NOT INTENDED FOR MEDICAL ADVICE, DIAGNOSIS OR TREATMENT." +
                " Be sure to consult with a qualified physician for advice about medications."

        return MaterialAlertDialogBuilder(this.context, R.style.AlertDialog)
            .setIcon(R.drawable.ic_warning_24dp)
            .setTitle("Disclaimer")
            .setMessage(disclaimerText)
            .setPositiveButton("Agree") { dialogInterface: DialogInterface, i: Int -> }
            .setNegativeButton("Disagree") { dialogInterface: DialogInterface, i: Int ->
                this.activity?.finish()
            }
            .create()
    }
}