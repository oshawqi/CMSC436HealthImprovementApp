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

    }

    override fun onStart() {
        super.onStart()

        mWeightLossButton.setOnClickListener{
            Log.i(TAG, "Weight Loss Selected")
        }

        mBulkUpButton.setOnClickListener{
            Log.i(TAG, "Bulk Up Selected")

            //Create an intent to open WorkoutActivity
            val intent = Intent(applicationContext, WorkoutActivity::class.java)

            intent.putExtra(USER_ID, uid)
            intent.putExtra(WORKOUT_TYPE, BULK_UP)
            startActivity(intent)
        }

        mEnduranceButton.setOnClickListener{
            Log.i(TAG, "Endurance Selected")
        }

        mFlexibilityButton.setOnClickListener{
            Log.i(TAG, "Flexibility Selected")
        }


    }

    companion object {
        val TAG = "Project-Welcome"
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
    }

}