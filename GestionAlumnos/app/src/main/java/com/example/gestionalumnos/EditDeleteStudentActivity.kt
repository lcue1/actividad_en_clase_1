package com.example.gestionalumnos

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.collections.ArrayList

class EditDeleteStudentActivity : AppCompatActivity() {
    //Atributes
    private  var studentData:ArrayList<String> ?= null
    private  lateinit var backBtn:Button
    private lateinit var title:String
    private lateinit var titleActivity:TextView
    private lateinit var dniEditText:EditText
    private lateinit var lNameEditText:EditText
    private lateinit var fNameEditText:EditText
    private lateinit var gradeEditText:EditText
    private lateinit var btnButtonLinear:LinearLayout
    private var  editBtn:Button? =null
    private var  deleteBtn:Button? =null
    private var addStudentBtn:Button?=null
    private lateinit var modelStudent:ModelStudent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_delete_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAtributes()
        setButtonsListeners()


    }

    private fun initAtributes() {
        studentData = intent.getStringArrayListExtra("student_data")
        backBtn=findViewById(R.id.back_btn)
        title = "Agregar alumno"
        titleActivity= findViewById(R.id.title_activity)

        dniEditText = findViewById(R.id.dni_edit_text)
        lNameEditText = findViewById(R.id.l_name_edit_text)
        fNameEditText = findViewById(R.id.f_name_edit_text)
        gradeEditText = findViewById(R.id.grade_edit_text)
        btnButtonLinear = findViewById(R.id.btn_bellow_linear)
        if(studentData!=null){
            Log.d("datastudent",studentData.toString())
            title = "Editar"
            dniEditText.setText(studentData!![0].toString())
            dniEditText.isEnabled = false
            lNameEditText.setText(studentData!![1].toString())
            fNameEditText.setText(studentData!![2].toString())
            gradeEditText.setText(studentData!![3].toString())
            editBtn = createButton(this, "Editar",ContextCompat.getColor(this, R.color.bg_btn))
            btnButtonLinear.addView( editBtn)
            deleteBtn = createButton(this,  "X",ContextCompat.getColor(this, R.color.red))
            btnButtonLinear.addView( deleteBtn)
        }
        modelStudent= ModelStudent()
        addStudentBtn = createButton(this,  "Agregar",ContextCompat.getColor(this, R.color.bg_btn))
        btnButtonLinear.addView( addStudentBtn)// add rows to linear layout
        titleActivity.text=title

    }



    private fun setButtonsListeners() {
        backBtn.setOnClickListener { goAnotherActivity(this, StudentsActivity::class.java) }
        addStudentBtn?.setOnClickListener {
            val add = modelStudent.addStudentData(this,dniEditText.text.toString(), lNameEditText.text.toString(), fNameEditText.text.toString(),gradeEditText.text.toString(),)
            if(add){
                Toast.makeText(this, "SAlumno guardado eitosamente",Toast.LENGTH_LONG).show()
                goAnotherActivity(this, StudentsActivity::class.java)
            }
        }
        editBtn?.setOnClickListener {
            val edit = modelStudent.editStudent(this,dniEditText.text.toString(), lNameEditText.text.toString(), fNameEditText.text.toString(),gradeEditText.text.toString(),)
            if(edit){
                Toast.makeText(this, "SAlumno Actualizado",Toast.LENGTH_LONG).show()
                goAnotherActivity(this, StudentsActivity::class.java)
            }
        }
        deleteBtn?.setOnClickListener {
            val delStudent = modelStudent.deleteStudent(this, dniEditText.text.toString())
            if(delStudent){
                Toast.makeText(this, "SAlumno eliminado",Toast.LENGTH_LONG).show()
                goAnotherActivity(this, StudentsActivity::class.java)
            }
        }
    }

}

