package com.example.healthimprovementapp

import android.content.Intent
import android.os.Bundle
import android.security.ConfirmationAlreadyPresentingException
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Welcome : AppCompatActivity() {

    //Button Variables
    private lateinit var mWeightLossButton : Button
    private lateinit var mBulkUpButton : Button
    private lateinit var mEnduranceButton : Button
    private lateinit var mFlexibilityButton : Button

    //Variable to hold the UID string from login screen
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        mWeightLossButton = findViewById<Button>(R.id.weight_loss_button)
        mBulkUpButton = findViewById<Button>(R.id.bulk_up_button)
        mEnduranceButton = findViewById<Button>(R.id.endurance_button)
        mFlexibilityButton = findViewById<Button>(R.id.flexibility_button)

        //Acquire the UID from the intent LoginActivity passed in
        uid = intent.getStringExtra(USER_ID)!!



        //TODO add a history button to the end of the GUI, add an on click listener for the history button
        //to start a HistoryActivity that hasn't been implemented yet.

        //On Click listeners start the WorkoutActivity through the workoutSelected function.
        mWeightLossButton.setOnClickListener{
            Log.i(TAG, "Weight Loss Selected")
            workoutSelected(WEIGHT_LOSS)
        }

        mBulkUpButton.setOnClickListener{
            Log.i(TAG, "Bulk Up Selected")
            workoutSelected(BULK_UP)

        }

        mEnduranceButton.setOnClickListener{
            Log.i(TAG, "Endurance Selected")
            workoutSelected(ENDURANCE)
        }

        mFlexibilityButton.setOnClickListener{
            Log.i(TAG, "Flexibility Selected")
            workoutSelected(FLEXIBILITY)
        }
    }

    //Function starts the WorkoutActivity, passing it the user's ID and the provided workout type.
    private fun workoutSelected(workoutType : String?) {
        val intent = Intent(applicationContext, WorkoutActivity::class.java)
        intent.putExtra(USER_ID, uid)
        intent.putExtra(WORKOUT_TYPE, workoutType)
        startActivity(intent)
    }

    companion object {
        //TODO -> add constants relating to the workout types.
        val TAG = "Mine-Welcome: "
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
    }

}