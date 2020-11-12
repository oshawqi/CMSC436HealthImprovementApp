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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        mWeightLossButton = findViewById<Button>(R.id.weight_loss_button)
        mBulkUpButton = findViewById<Button>(R.id.bulk_up_button)
        mEnduranceButton = findViewById<Button>(R.id.endurance_button)
        mFlexibilityButton = findViewById<Button>(R.id.flexibility_button)

        mWeightLossButton.setOnClickListener{
            Log.i(TAG, "Weight Loss Selected")
        }

        mBulkUpButton.setOnClickListener{
            Log.i(TAG, "Bulk Up Selected")
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
    }

}