package com.example.healthimprovementapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        //Check from database if the user has made a selection already
        //if so create an intent to send them to the main activity


    }
}