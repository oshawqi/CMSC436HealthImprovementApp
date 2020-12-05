package com.example.healthimprovementapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.lang.Exception

class SubmitDialogFragment : DialogFragment() {

    private lateinit var listener : SubmitListener

    interface SubmitListener {
        fun onSubmitCopy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as SubmitListener
        } catch(e : Exception) {
            Log.i(TAG, e.toString())
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setIcon(R.drawable.logo)
                .setMessage("Changes were made to this workout. Would you like to" +
            " save them?")
                .setPositiveButton("Yes") { _,_ ->
                    listener.onSubmitCopy()
                }

                .setNegativeButton("No") {_,_ ->}
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {

        fun newInstance() : SubmitDialogFragment {
            return SubmitDialogFragment()
        }
        val TAG = "Mine-SubmitDialogFragment:"
    }
}