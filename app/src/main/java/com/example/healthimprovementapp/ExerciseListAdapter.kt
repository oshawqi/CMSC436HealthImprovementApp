package com.example.healthimprovementapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout

class ExerciseListAdapter(private val context : Context) : BaseAdapter() {

    private val exercises : ArrayList<Exercise> = ArrayList<Exercise>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val exercise = exercises[position]
        val viewHolder : ViewHolder

        if (convertView == null) {

            viewHolder = ViewHolder()
            val mLayoutInflater = LayoutInflater.from(context)
            val newView = mLayoutInflater.inflate(R.layout.exercise_list, parent, false)

            newView.tag = viewHolder
            viewHolder.mItemLayout = newView.findViewById(R.id.exerciseList)
            viewHolder.mTitleView = newView.findViewById(R.id.exerciseName)
            viewHolder.mSetsView = newView.findViewById(R.id.setsView)
            viewHolder.mRepsView = newView.findViewById(R.id.repsView)
            viewHolder.mWeightView = newView.findViewById(R.id.weightView)

        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        //fill in default data in the EditTexts as hints for reps, weight, sets.
        viewHolder.mTitleView!!.text = exercise.exerciseName.toString()
        viewHolder.mWeightView!!.text = exercise.weight.toString()
        viewHolder.mSetsView!!.text = exercise.numSets.toString()
        viewHolder.mRepsView!!.text = exercise.numReps.toString()

        return viewHolder.mItemLayout!!
    }

    override fun getItem(position: Int): Any {
        return exercises[position]
    }

    override fun getItemId(position: Int): Long {
        //TODO -> look into saving the id for the database here and be able to retrieve it
        //from here
        return position.toLong()
    }

    override fun getCount(): Int {
        return exercises.size
    }

    fun add(exercise : Exercise) {
        Log.i(TAG, "Adding exercise to adapter")
        exercises.add(exercise)
        notifyDataSetChanged()
    }

    fun addAll(inExercises : List<Exercise>) {
        exercises.addAll(inExercises)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        exercises.removeAt(position)
    }

    internal class ViewHolder {
        var mItemLayout : RelativeLayout? = null
        var mTitleView : TextView? = null
        var mSetsView : TextView? = null
        var mRepsView : TextView? = null
        var mWeightView : TextView? = null
    }

    companion object {
        val TAG = "Mine-ExerciseListAdapter:"
    }
}