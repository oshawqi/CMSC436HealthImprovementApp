package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.ExerciseListAdaptor
import com.example.healthimprovementapp.R

class WorkoutExercisesActivity : AppCompatActivity() {

    private lateinit var mListView : ListView
    private lateinit var mListAdapter : ExerciseListAdaptor
    private lateinit var mSubmitButton : Button
    private lateinit var mWorkout : Workout

 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_exercise)

        //Set mListView and add mSubmitButton as a footer to the list
        mListView = findViewById<ListView>(R.id.exerciseList)
        mListView.setFooterDividersEnabled(true)
        mSubmitButton = findViewById(R.id.submitButton)
        mListView.addFooterView(mSubmitButton)

        //Get workout from the intent
        val workoutName = savedInstanceState?.getString(WORKOUT_NAME)
        val workoutId = savedInstanceState?.getString(WORKOUT_ID)
        val workoutExercises = savedInstanceState?.getString(WORKOUT_EXERCISES)//TODO change to array list when passed as a whole
        mWorkout = Workout(workoutName!!, workoutId!!, ArrayList<Exercise>(0))//TODO change to proper arrayList

        //Setup List Adapter
        mListAdapter = ExerciseListAdaptor(this, mWorkout.workoutName.toString())
        mListView.adapter = mListAdapter

    }

    companion object {
        const val TAG = "Mine-WorkoutExercisesActivity:"
        const val WORKOUT_NAME = "com.example.tesla.myhomelibrary.authorname"
        const val WORKOUT_ID = "com.example.tesla.myhomelibrary.authorid"
        const val WORKOUT_EXERCISES = "com.example.tesla.myhomelibrary.userid"
    }
}