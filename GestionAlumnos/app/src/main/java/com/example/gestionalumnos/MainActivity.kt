package com.example.gestionalumnos

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var studentBtn:Button
    private lateinit var addStudentBtn:Button
    private lateinit var exitBtn:Button
    val modelStudent=ModelStudent()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAtributes()
        setButtonsListeners()// setonclickListener to buttons


    }

    private fun setButtonsListeners() {
        studentBtn.setOnClickListener { goAnotherActivity(this,StudentsActivity::class.java) }
        addStudentBtn.setOnClickListener {
          //  val save = modelStudent.addStudentData(this,"12345678", "Smith", "John", "6",)
            goAnotherActivity(this,EditDeleteStudentActivity::class.java)
        }

        exitBtn.setOnClickListener {
            moveTaskToBack(true) // Moves the app to the background
            finish() // Closes the current activity

        }

        //add more activities...
    }

    private fun initAtributes() {
        studentBtn=findViewById(R.id.student_btn)
        addStudentBtn=findViewById(R.id.add_student_btn)
        exitBtn=findViewById(R.id.exit_btn)
    }




}