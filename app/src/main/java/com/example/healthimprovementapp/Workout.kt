package com.example.healthimprovementapp.com.example.healthimprovementapp

import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import java.io.Serializable

//data class Workout (val workoutId: String = "", val workoutName: String = "", var workoutExercises: ArrayList<Exercise>)

public class Workout(val workoutId: String = "", val workoutName: String = "", var workoutExercises: ArrayList<Exercise>) {
    public val name : String = workoutName
    public val id : String = workoutId
    public val exerciseList = workoutExercises

}

