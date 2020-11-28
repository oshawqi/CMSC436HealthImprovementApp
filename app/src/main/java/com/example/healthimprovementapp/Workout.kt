package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Parcel
import android.os.Parcelable


public class Workout(val workoutId: String? = "", val workoutName: String? = "", var workoutExercises: ArrayList<Exercise>) : Parcelable {
    public val name : String? = workoutName
    public val id : String? = workoutId
    public val exerciseList = workoutExercises

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        TODO("workoutExercises")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workoutId)
        parcel.writeString(workoutName)
    }

    override fun describeContents(): Int {
        return 0
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