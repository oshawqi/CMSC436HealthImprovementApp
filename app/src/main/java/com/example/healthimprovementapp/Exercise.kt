package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Exercise (val exerciseName: String = "", val numSets: Int, val numReps: Int, val weight: Int) : Parcelable {

    constructor(parcel : Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(exerciseName)
        parcel.writeInt(numSets)
        parcel.writeInt(numReps)
        parcel.writeInt(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$exerciseName (W:$weight, S:$numSets, R:$numReps)"
    }
}