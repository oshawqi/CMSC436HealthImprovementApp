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
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var confirmPasswordTV : EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()

    private var mAuth: FirebaseAuth? = null
    private var mDatabase : DatabaseReference? = null

    private lateinit var userID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("users")

        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        confirmPasswordTV = findViewById(R.id.confirmPassword)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)

        regBtn!!.setOnClickListener { registerNewUser() }
    }

    private fun addDefaultWorkouts() {
        var databaseID = mDatabase!!.push().key

        //Add basic Bulk-up Workouts
        val databaseBulkWorkouts = FirebaseDatabase.getInstance().getReference("users").child(userID).child("workouts").child(
            BULK_UP)
            //Chest Day
            val benchPress = Exercise("Bench Press", 3, 8, 145)
            val inclinePress = Exercise("Incline Dumbbell Press", 3, 6, 65)
            val pecFly = Exercise("Pectoral Fly", 4, 10, 45)
            var chestDayList = ArrayList<Exercise>()
            chestDayList.add(benchPress)
            chestDayList.add(inclinePress)
            chestDayList.add(pecFly)
            val chestDay = Workout("chestDay", "Std. Chest Day", chestDayList)
            databaseBulkWorkouts.child(databaseID!!).setValue(chestDay)

            //Leg Day
            val squats = Exercise("Squats", 3, 8, 205)
            val calfRaises = Exercise("Calf Raises", 5, 15, 95)
            val quadExtensions = Exercise("Quadricep Extensions", 3, 10, 105)
            var legDayList = ArrayList<Exercise>()
            legDayList.add(squats)
            legDayList.add(calfRaises)
            legDayList.add(quadExtensions)
            val legDay = Workout("legDay", "Std. Leg Day", legDayList)
            databaseID = mDatabase!!.push().key
            databaseBulkWorkouts.child(databaseID!!).setValue(legDay)

            //Back Day
            val dumbbellRow = Exercise("Dumbbell Row", 3, 8, 85)
            val barbellRow = Exercise("Barbell Row", 3, 6, 155)
            var backDayList = ArrayList<Exercise>()
            backDayList.add(dumbbellRow)
            backDayList.add(barbellRow)
            val backDay = Workout("backDay", "Std. Back Day", backDayList)
            databaseID = mDatabase!!.push().key
            databaseBulkWorkouts.child(databaseID!!).setValue(backDay)




        //Add Basic WeightLoss Workouts
        val databaseWeightLossWorkouts = FirebaseDatabase.getInstance().getReference("users").child(userID).child("workouts").child(
            WEIGHT_LOSS)
            //2-Mile Run
            val twoMileRun = Exercise("Two Mile Run", 1, 1, 0)
            var twoMileRunList = ArrayList<Exercise>()
            twoMileRunList.add(twoMileRun)
            val runDay = Workout("runDay", "Run Day", twoMileRunList)
            databaseID = mDatabase!!.push().key
            databaseWeightLossWorkouts.child(databaseID!!).setValue(runDay)

            //Tabata
            val burpees = Exercise("Burpees", 8, 15, 0)
            val jumpSquats = Exercise("Jump Squats", 4, 20, 0)
            val pushUps = Exercise("Pushups", 3, 12, 0)
            val tabataList = ArrayList<Exercise>()
            tabataList.add(burpees)
            tabataList.add(jumpSquats)
            tabataList.add(pushUps)
            val tabataDay = Workout("tabataDay", "Tabata Day", tabataList)
            databaseID = mDatabase!!.push().key
            databaseWeightLossWorkouts.child(databaseID!!).setValue(tabataDay)




        //Add Basic Flexibility Workouts
        val databaseFlexibilityWorkouts = FirebaseDatabase.getInstance().getReference("users").child(userID).child("workouts").child(
            FLEXIBILITY)
            //Core
            val bicycleKicks = Exercise("Bicycle Kicks", 5, 30, 0)
            val bananaStretches = Exercise("Banana Stretches", 5, 10, 0)
            var coreList = ArrayList<Exercise>()
            coreList.add(bicycleKicks)
            coreList.add(bananaStretches)
            val coreDay = Workout("coreDay", "Core Stretches Day", coreList)
            databaseID = mDatabase!!.push().key
            databaseFlexibilityWorkouts.child(databaseID!!).setValue(coreDay)



        //Add Basic Endurance Workouts
        val databaseEnduranceWorkouts = FirebaseDatabase.getInstance().getReference("users").child(userID).child("workouts").child(
            ENDURANCE)
            //Arms
            val pullupAndHold = Exercise("Pull-up and Hold", 10, 1, 0)
            val crossPunches = Exercise("Cross Punches", 10, 20, 0)
            var armList = ArrayList<Exercise>()
            armList.add(pullupAndHold)
            armList.add(crossPunches)
            val armDay = Workout("armDay", "Arm Endurance Exercises", armList)
            databaseID = mDatabase!!.push().key
            databaseEnduranceWorkouts.child(databaseID!!).setValue(armDay)
            //Core
            val planks = Exercise("Planks", 5, 1, 0)
            val sidePlanks = Exercise("Side Planks", 5, 1, 0)
            val crunches = Exercise("Crunches", 3, 20, 0)
            var coreEnduranceList = ArrayList<Exercise>()
            coreEnduranceList.add(planks)
            coreEnduranceList.add(sidePlanks)
            coreEnduranceList.add(crunches)
            val coreEnduranceDay = Workout("coreEnduranceDay", "Core Endurance Excercises", coreEnduranceList)
            databaseID = mDatabase!!.push().key
            databaseEnduranceWorkouts.child(databaseID!!).setValue(coreEnduranceDay)

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

                    val uid = mAuth!!.currentUser!!.uid
                    val user = User(uid, ArrayList<Workout>())
                    mDatabase!!.setValue(user)

                    //Sets up default workouts
                    userID = uid
                    addDefaultWorkouts()

                    val intent = Intent(this@RegistrationActivity, Welcome::class.java)
                    intent.putExtra(USER_ID, user.userId)
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
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
    }
}
