package com.example.healthimprovementapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout

//TODO: The custom list view which should display workout names is not working yet
internal class ListViewAdapter(context: Context, resource: Int, objects: ArrayList<Workout>) :
    ArrayAdapter<Workout>(context, resource, objects) {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val workout = getItem(position) as Workout

        val newView: View

        // Check for recycled View
        if (null == convertView) {

            // Not recycled. Create the View
            newView = mLayoutInflater.inflate(R.layout.list_item, parent, false)

            // Cache View information in ViewHolder Object
            val viewHolder = ViewHolder()
            newView.tag = viewHolder
            viewHolder.textView = newView.findViewById(R.id.text)

        } else {
            newView = convertView
        }

        // Set the View's data

        // Retrieve the viewHolder Object
        val storedViewHolder = newView.tag as ViewHolder

        //Set the data in the data View
        storedViewHolder.textView.text = workout.name

        return newView
    }

    // The ViewHolder class. See:
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
    internal class ViewHolder {
        lateinit var textView: TextView
    }
}
