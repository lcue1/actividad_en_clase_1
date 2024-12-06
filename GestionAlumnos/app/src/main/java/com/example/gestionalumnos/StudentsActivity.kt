package com.example.gestionalumnos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StudentsActivity : AppCompatActivity() {
    //Atributes
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
    }

    private fun loadData() {
        loadTitleGrid()


       loadDataStudentsGrid()//load students data
    }

    //remember remove records
    private fun loadTitleGrid() {//Load the headers columns
        modelStudent.addStudentData("12345678", "luis", "John", "8.5",)
        modelStudent.addStudentData("12345678", "Smith", "John", "8.5",)

        // Add titles
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


    private fun loadDataStudentsGrid() {
        modelStudent.getAllStudents().forEachIndexed { rowIndex, row ->
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
                    goAnotherActivity(this,EditDeleteStudentActivity::class.java)
                }


                gridStudents.addView(textView)
            }
        }
    }




    private fun initAtributes() {
        gridStudents=findViewById(R.id.gridStudents)
        modelStudent=ModelStudent()

    }
}