package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.DatabaseReference


public class Workout(val workoutId: String? = "", val workoutName: String? = "", var workoutExercises: ArrayList<Exercise>) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        ArrayList<Exercise>().apply{
            parcel.readList(this as List<Exercise>,Exercise::class.java.classLoader)
        }
    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workoutId)
        parcel.writeString(workoutName)
        parcel.writeList(workoutExercises as List<Exercise>)
    }

    fun writeToDatabase(database : DatabaseReference) {
        if (database == null) {
            return
        }

        val extDb = database.child(workoutId!!).child(workoutName!!).setValue("THIS WORKED!!!")



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