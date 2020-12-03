package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.DatabaseReference
import java.lang.Exception


public class Workout(val workoutId: String? = "", val workoutName: String? = "", var workoutExercises: ArrayList<Exercise>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        ArrayList<Exercise>().apply{
            parcel.readList(this as List<Exercise>,Exercise::class.java.classLoader)
        }
    ){}

    constructor(data : Map<String,Object>) : this(
        data.getValue("workoutId") as String,
        data.getValue("workoutName") as String,
        ArrayList<Exercise>()
    ) {
        for (exerciseData in data.getValue("workoutExercises") as ArrayList<Object>) {
            Log.i("Mine", exerciseData.toString())
            val eMap = exerciseData as Map<String, Object>
            val exercise = Exercise(eMap.getValue("exerciseName") as String, (eMap.getValue("numReps") as Long).toInt(),
                (eMap.getValue("numSets") as Long).toInt(), (eMap.getValue("weight") as Long).toInt())
            this.workoutExercises.add(exercise)
        }
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workoutId)
        parcel.writeString(workoutName)
        parcel.writeList(workoutExercises as List<Exercise>)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        val numExercises = workoutExercises.size.toString()
        return "Name: $workoutName, ID: $workoutId, with $numExercises exercises"
    }


    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }
}