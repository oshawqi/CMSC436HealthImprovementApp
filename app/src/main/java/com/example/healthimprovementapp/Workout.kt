package com.example.healthimprovementapp.com.example.healthimprovementapp

import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise

data class Workout (val workoutId: String = "", val workoutName: String = "", val workoutExercises: List<Exercise> = emptyList())