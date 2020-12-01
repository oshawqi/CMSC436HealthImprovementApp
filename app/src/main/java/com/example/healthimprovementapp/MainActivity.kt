package com.example.healthimprovementapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var registerBtn: Button? = null
    private var loginBtn: Button? = null
    private val user = FirebaseAuth.getInstance().currentUser                                       //TESTING
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        //TESTING
        if (user != null) {
            //User is signed in
            Toast.makeText(applicationContext, "Welcome Back!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Welcome::class.java)
            intent.putExtra(USER_ID, mAuth!!.currentUser!!.uid)
            startActivity(intent)
        }
        setContentView(R.layout.activity_main)

        initializeViews()

        registerBtn!!.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        loginBtn!!.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViews() {
        registerBtn = findViewById(R.id.register)
        loginBtn = findViewById(R.id.login)
    }

    companion object {
        const val USER_ID = "USER_ID"
    }

}
