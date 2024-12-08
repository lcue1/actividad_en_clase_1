package com.example.gestionalumnos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.collections.ArrayList

class StudentsActivity : AppCompatActivity() {
    //Atributes
    private lateinit var backBtn:Button
    private lateinit var addBtn:Button
    private lateinit var gridStudents:GridLayout
    private  lateinit var modelStudent:ModelStudent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAtributes()
        loadData()
        addBtn.setOnClickListener {
            goAnotherActivity(this,EditDeleteStudentActivity::class.java)
        }


    }

    private fun loadData() {
        loadTitleGrid()
       loadDataStudentsGrid()//load students data
    }

    //set title to each column in grid layout
    private fun loadTitleGrid() {//Load the headers columns
        modelStudent.titleGrid.forEachIndexed { index, title ->
            val textView = TextView(this).apply {
                text = title
                textSize = 18f
                setPadding(10, 16, 10, 16)
                setTextColor(Color.WHITE)
                layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(0) // First row for titles
                    columnSpec = GridLayout.spec(index)
                }
            }
            gridStudents.addView(textView)
        }
    }

//Load data in  grid layout and set listener to each row
    private fun loadDataStudentsGrid() {
        modelStudent.getDataFromFile(this).forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                val textView = TextView(this).apply {
                    text = cell
                    textSize = 16f
                    setPadding(10, 25, 10, 25)
                    setTextColor(Color.WHITE)
                    layoutParams = GridLayout.LayoutParams().apply {
                        rowSpec = GridLayout.spec(rowIndex + 1) // Rows start at 1
                        columnSpec = GridLayout.spec(colIndex)
                    }
                }

                textView.setOnClickListener{
                    val intent = Intent(this, EditDeleteStudentActivity::class.java)
                    intent.putExtra("model_student", modelStudent)
                    intent.putExtra("student_data",ArrayList(row))
                    startActivity(intent)
                }


                gridStudents.addView(textView)

            }
        }
    }

    private fun initAtributes() {
        backBtn=findViewById(R.id.back_btn)
        addBtn=findViewById(R.id.addBBtn)
        backBtn.setOnClickListener { goAnotherActivity(this,MainActivity::class.java) }
        gridStudents=findViewById(R.id.gridStudents)
        modelStudent=ModelStudent()

    }
}