package com.example.healthimprovementapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout

class WorkoutListAdapter(private val context : Context) : BaseAdapter() {

    private val workouts : ArrayList<Workout> = ArrayList<Workout>()

    constructor(context : Context, workouts : ArrayList<Workout>) : this(context) {
        this.workouts.addAll(workouts)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val workout = workouts[position]
        val viewHolder : ViewHolder

        if (convertView == null) {

            viewHolder = ViewHolder()
            val mLayoutInflater = LayoutInflater.from(context)
            val newView = mLayoutInflater.inflate(R.layout.workout_list, parent, false)

            newView.tag = viewHolder
            viewHolder.mItemLayout = newView.findViewById(R.id.workoutList)
            viewHolder.mTitleView = newView.findViewById(R.id.workoutName)
            viewHolder.mExercisesView = newView.findViewById(R.id.workoutExercises)

        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        //fill in default data for workouts
        viewHolder.mTitleView!!.text = workout.workoutName
        viewHolder.mExercisesView!!.text = exerciseString(workout)

        return viewHolder.mItemLayout!!
    }

    private fun exerciseString(workout : Workout) : String {
        val roughString = workout.workoutExercises.toString() //returns [ s1, s2, s3, ..., sn]
        return roughString.substring(1, roughString.length-1)
    }

    fun add(workout : Workout) {
        Log.i(TAG, "Adding Workout to adapter")
        workouts.add(workout)
        notifyDataSetChanged()
    }

    fun clear() {
        workouts.clear()
    }

    fun addAll(inWorkouts : List<Workout>) {
        workouts.addAll(inWorkouts)
    }

    override fun getItem(position: Int): Workout {
        return workouts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return workouts.size
    }

    internal class ViewHolder {
        var mItemLayout : RelativeLayout? = null
        var mTitleView : TextView? = null
        var mExercisesView : TextView? = null
    }

    companion object {
        val TAG = "Mine-WorkoutListAdapter"
    }
}