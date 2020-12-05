package com.example.healthimprovementapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.edit_exercise_fragment.*
import java.lang.ClassCastException
import java.lang.IllegalStateException

class EditExerciseDialogFragment(val position : Int, val name : String) : DialogFragment() {

    internal lateinit var listener : EditListener

    interface EditListener {
        fun onDialogPositiveClick(pos : Int, name : String, sets : Int, reps : Int, weight : Int)
        fun onDialogNegativeClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //verifies the host activity implements EditListener
        try {
            //instantiate listener
            listener = context as EditListener
        } catch (e : ClassCastException) {
            Log.i(TAG, "The calling class does not implement the EditListener interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.edit_exercise_fragment, null)
            val nameView = view.findViewById<TextView>(R.id.name)
            val setsView = view.findViewById<EditText>(R.id.numSets)
            val repsView = view.findViewById<EditText>(R.id.numReps)
            val weightView = view.findViewById<EditText>(R.id.weight)

            builder.setView(view)
                .setNegativeButton(R.string.cancel) { _,_ ->
                    listener.onDialogNegativeClick()
                }

                .setPositiveButton("Submit Edits") { p0, p1 ->
                    val sets = setsView.text.toString()
                    val reps = repsView.text.toString()
                    val weight = weightView.text.toString()

                    if (sets != "" && reps != "" && weight != "") {
                        listener.onDialogPositiveClick(position, name, sets.toInt(), reps.toInt(), weight.toInt())
                    } else {
                        Toast.makeText(context, "Please fill in empty fields!", Toast.LENGTH_SHORT).show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {

        fun newInstance(position : Int, name : String) : EditExerciseDialogFragment {
            return EditExerciseDialogFragment(position, name)
        }

        val TAG = "Mine-EditExerciseDialog:"
    }
}