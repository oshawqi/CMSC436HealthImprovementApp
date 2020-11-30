package com.example.healthimprovementapp

import android.content.Intent
import android.graphics.Color.red
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var confirmPasswordTV : EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()

        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        confirmPasswordTV = findViewById(R.id.confirmPassword)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)

        regBtn!!.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()
        val confirmPassword : String = confirmPasswordTV!!.text.toString()
        //Color value for invalid selections
        val validColor = resources.getColor(R.color.valid)
        val invalidColor = resources.getColor(R.color.invalid)

        if (!validator.validEmail(email)) {
            emailTV!!.setTextColor(invalidColor)
            Toast.makeText(applicationContext, "Please enter a valid email!", Toast.LENGTH_LONG).show()
            return
        } else {
            emailTV!!.setTextColor(validColor)
        }

        if (!validator.validPassword(password)) {
            passwordTV!!.setTextColor(invalidColor)
            confirmPasswordTV!!.setTextColor(invalidColor)
            Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
            return
        } else {
            passwordTV!!.setTextColor(validColor)
            confirmPasswordTV!!.setTextColor(validColor)
        }

        if (password != confirmPassword) {
            passwordTV!!.setTextColor(invalidColor)
            confirmPasswordTV!!.setTextColor(invalidColor)
            Toast.makeText(applicationContext, "Passwords do not match!", Toast.LENGTH_LONG).show()
            return
        } else {
            passwordTV!!.setTextColor(validColor)
            confirmPasswordTV!!.setTextColor(validColor)
        }

        progressBar!!.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                    val intent = Intent(this@RegistrationActivity, Welcome::class.java)
                    intent.putExtra(USER_ID, mAuth!!.currentUser!!.uid)
                    startActivity(intent)

                } else {
                    //Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE
                }
            }.addOnFailureListener { e ->
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()}
    }

    companion object {
        val TAG = "Mine-RegistrationActivity"
        const val USER_ID = "USER_ID"
    }
}
