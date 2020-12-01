package com.example.healthimprovementapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import com.example.healthimprovementapp.com.example.healthimprovementapp.WorkoutExercisesActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.user_history.view.*

class UserHistoryActivity : AppCompatActivity() {

    private lateinit var uid : String
    private var mHistoryList : ListView? = null
    private var mListAdapter : WorkoutListAdapter? = null
    private var mReturnButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_history)

        uid = intent.getStringExtra(USER_ID)
        if (uid == null) {
            Log.i(TAG, "User not received from intent")
            finish()
        }

        mHistoryList = findViewById(R.id.historyList)
        mListAdapter = WorkoutListAdapter(this)
        mHistoryList!!.adapter = mListAdapter

        mReturnButton = findViewById(R.id.returnButton)

        //mListAdapter!!.addAll(user.workouts)

        mReturnButton!!.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            intent.putExtra(USER_ID, uid)
            startActivity(intent)
        }

        mHistoryList!!.setOnItemClickListener { adapterView, view, i, l ->
            val workout = mListAdapter!!.getItem(i)
            val intent = Intent(this, WorkoutExercisesActivity::class.java)
            intent.putExtra(WORKOUT_NAME, workout)
            intent.putExtra(USER_ID, uid)
            startActivity(intent)
        }

    }

    companion object {
        val TAG = "Mine-UserHistory"
        val USER = "USER"
        val USER_ID = "USER_ID"
        const val WORKOUT_NAME = "WORKOUT_NAME"
    }
}