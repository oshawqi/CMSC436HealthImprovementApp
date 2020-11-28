package com.example.healthimprovementapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout

class ExerciseListAdaptor(private val context : Context, private val workoutName : String) : BaseAdapter() {

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
            viewHolder.mSetsEditText = newView.findViewById(R.id.setsEditText)
            viewHolder.mRepsEditText = newView.findViewById(R.id.repsEditText)
            viewHolder.mWeightEditText = newView.findViewById(R.id.weightEditText)

        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        //TODO -> fill in default data in the EditTexts as hints for reps, weight, sets.
        viewHolder.mTitleView!!.text = exercise.exerciseName

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

    fun add(exercise : Exercise) : Int {
        exercises.add(exercise)
        notifyDataSetChanged()
        return count - 1
    }

    fun removeAt(position: Int) {
        exercises.removeAt(position)
    }

    internal class ViewHolder {
        var mItemLayout : RelativeLayout? = null
        var mTitleView : TextView? = null
        var mSetsEditText : EditText? = null
        var mRepsEditText : EditText? = null
        var mWeightEditText : EditText? = null
    }
}