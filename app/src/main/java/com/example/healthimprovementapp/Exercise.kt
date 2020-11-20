package com.example.healthimprovementapp.com.example.healthimprovementapp

import java.io.Serializable

data class Exercise (val exerciseName: String = "", val numSets: Int, val numReps: Int, val weight: Int) : Serializable