package com.example.healthimprovementapp

import android.content.Intent
import android.os.Bundle
import android.security.ConfirmationAlreadyPresentingException
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Welcome : AppCompatActivity() {

    //Button Variables
    private lateinit var mWeightLossButton : Button
    private lateinit var mBulkUpButton : Button
    private lateinit var mEnduranceButton : Button
    private lateinit var mFlexibilityButton : Button
    private lateinit var mSignoutButton : Button
    private lateinit var mHistoryButton : Button
    private lateinit var mAuth: FirebaseAuth

    //Variable to hold the UID string from login screen
    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        mWeightLossButton = findViewById<Button>(R.id.weight_loss_button)
        mBulkUpButton = findViewById<Button>(R.id.bulk_up_button)
        mEnduranceButton = findViewById<Button>(R.id.endurance_button)
        mFlexibilityButton = findViewById<Button>(R.id.flexibility_button)
        mSignoutButton = findViewById<Button>(R.id.signout_button)
        mHistoryButton = findViewById<Button>(R.id.history_button)
        mAuth = FirebaseAuth.getInstance()

        //Acquire the UID from the intent LoginActivity passed in
        uid = intent.getStringExtra(USER_ID)

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(applicationContext, WorkoutActivity::class.java)
        intent.putExtra(USER_ID, uid)

        mWeightLossButton.setOnClickListener{
            Log.i(TAG, "Weight Loss Selected")
            intent.putExtra(WORKOUT_TYPE, WEIGHT_LOSS)
            startActivity(intent)
        }

        mBulkUpButton.setOnClickListener{
            Log.i(TAG, "Bulk Up Selected")
            intent.putExtra(WORKOUT_TYPE, BULK_UP)
            startActivity(intent)
        }

        mEnduranceButton.setOnClickListener{
            Log.i(TAG, "Endurance Selected")
            intent.putExtra(WORKOUT_TYPE, ENDURANCE)
            startActivity(intent)
        }

        mFlexibilityButton.setOnClickListener{
            Log.i(TAG, "Flexibility Selected")
            intent.putExtra(WORKOUT_TYPE, FLEXIBILITY)
            startActivity(intent)
        }


        mHistoryButton.setOnClickListener {
            Log.i(TAG, "History Selected")
            val intent = Intent(this, UserHistoryActivity::class.java)
            intent.putExtra(USER_ID, uid)
            startActivity(intent)
        }

        mSignoutButton.setOnClickListener {
            Log.i(TAG, "Signing Out User")
            Toast.makeText(this, "Signing Out...", Toast.LENGTH_LONG).show()

            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val TAG = "Mine-Welcome"
        val USER = "USER"
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
    }
}