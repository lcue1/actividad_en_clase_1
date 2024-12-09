package com.example.gestionalumnos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
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
    private lateinit var searchEditText:EditText
    private lateinit var allStudentsBtn:Button
    private lateinit var pasedStudnetsBtn:Button
    private lateinit var failedStudentsBtn:Button
    private lateinit var grade10Btn:Button
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
        setEventListener()

    }


    private fun loadData() {
        loadTitleGrid()
       loadDataStudentsGrid(modelStudent.getDataFromFile(this))//load students data
    }

    //set title to each column in grid layout
    private fun loadTitleGrid() {//Load the headers columns
        modelStudent.titleGrid.forEachIndexed { index, title ->
            val textView = TextView(this).apply {
                text = title
                textSize = 18f
                setPadding(80, 16, 80, 16)
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
    private fun loadDataStudentsGrid(studentsData:MutableList<List<String>>) {
    gridStudents.removeAllViews()//clear data in grid
    loadTitleGrid()//load clear data
        studentsData.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                val textView = TextView(this).apply {
                    text = cell
                    textSize = 16f
                    setPadding(80, 25, 80, 25)
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

        //Search btns

         searchEditText = findViewById(R.id.search_edit_text)
         allStudentsBtn = findViewById(R.id.all_students_btn)
         pasedStudnetsBtn = findViewById(R.id.pased_student_btn)
        failedStudentsBtn = findViewById(R.id.failed_student_btn)
        grade10Btn=findViewById(R.id.grade_10_btn)

    }


    private fun setEventListener() {

        addBtn.setOnClickListener {//open activity to add new student
            goAnotherActivity(this,EditDeleteStudentActivity::class.java)
        }
        allStudentsBtn.setOnClickListener {//show all students
            loadDataStudentsGrid(modelStudent.getDataFromFile(this))
        }
        pasedStudnetsBtn.setOnClickListener {// show pased students
            val pasedStudents = modelStudent.getDataFromFile(this).filter { it[3].toFloat() >=7}
            loadDataStudentsGrid(pasedStudents.toMutableList())
        }
        failedStudentsBtn.setOnClickListener {// show pased students
            val pasedStudents = modelStudent.getDataFromFile(this).filter { it[3].toFloat() <7}
            loadDataStudentsGrid(pasedStudents.toMutableList())
        }
        grade10Btn.setOnClickListener {// show pased students
            val pasedStudents = modelStudent.getDataFromFile(this).filter { it[3].toDouble() == 10.0}
            loadDataStudentsGrid(pasedStudents.toMutableList())
        }
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                val enteredText = searchEditText.text.toString() // Capture the data from EditText
                val dniTyped = searchEditText.text.toString()
                val recordSearched = modelStudent.getDataFromFile(this).filter { it[0] == dniTyped }
                Log.d("search", recordSearched.toString())
                loadDataStudentsGrid(recordSearched.toMutableList())

                //Close keyboard
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)

                true
            } else {
                false // Let the system handle other actions
            }
        }

    }
}