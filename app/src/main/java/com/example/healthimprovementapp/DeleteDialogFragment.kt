package com.example.healthimprovementapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.AlertDialog
import android.app.Dialog

import androidx.fragment.app.DialogFragment
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout


/**
 * A simple [Fragment] subclass.
 * Use the [DeleteDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteDialogFragment(val workout: Workout) : DialogFragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage("Do you really want to Delete?")

            // User cannot dismiss dialog by hitting back button
            .setCancelable(true)

            // Set up No Button
            .setNegativeButton(
                "No"
            ) { _, _ ->
            }

            // Set up Yes Button
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                (activity as WorkoutActivity).deleteWorkout(workout)
            }.create()
    }

    companion object {

        fun newInstance(workout: Workout): DeleteDialogFragment {
            return DeleteDialogFragment(workout)
        }
    }
}
