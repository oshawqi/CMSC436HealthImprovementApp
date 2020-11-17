package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.R

class WorkoutExercisesActivity : AppCompatActivity() {

    private lateinit var mListView : ListView
    private lateinit var mSubmitButton : Button

 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_exercise)

        //Set mListView and add mSubmitButton as a footer to the list
        mListView = findViewById<ListView>(R.id.exerciseList)
        mListView.setFooterDividersEnabled(true)
        mSubmitButton = findViewById(R.id.submitButton)
        mListView.addFooterView(mSubmitButton)


    }

}