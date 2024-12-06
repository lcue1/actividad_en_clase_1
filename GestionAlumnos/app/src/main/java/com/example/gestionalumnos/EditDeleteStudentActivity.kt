package com.example.gestionalumnos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.collections.ArrayList

class EditDeleteStudentActivity : AppCompatActivity() {
    //Atributes
    private  lateinit var backBtn:Button
    private  var studentData:ArrayList<String> ?= null
    private lateinit var title:String
    private lateinit var titleActivity:TextView
    private lateinit var dniEditText:EditText
    private lateinit var lNameEditText:EditText
    private lateinit var fNameEditText:EditText
    private lateinit var gradeEditText:EditText
    private lateinit var btnButtonLinear:LinearLayout
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

        backBtn.setOnClickListener { goAnotherActivity(this, StudentsActivity::class.java) }

    }

    private fun initAtributes() {
        backBtn=findViewById(R.id.back_btn)
        title = "Agregar alumno"
        studentData = intent.getStringArrayListExtra("data_student")
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
            lNameEditText.setText(studentData!![1].toString())
            fNameEditText.setText(studentData!![2].toString())
            gradeEditText.setText(studentData!![3].toString())
            btnButtonLinear.addView( createButton("Editar",ContextCompat.getColor(this, R.color.bg_btn)))
            btnButtonLinear.addView( createButton("X",ContextCompat.getColor(this, R.color.red)))
        }
        btnButtonLinear.addView( createButton("Agregar",ContextCompat.getColor(this, R.color.bg_btn)))
        titleActivity.text=title
    }

    private fun createButton(textBtn:String, bgButton:Int):Button {
        return Button(this).apply {
            text = textBtn
            textSize = 18f
            setPadding(10, 16, 10, 16)
            setBackgroundColor(bgButton)
            setTextColor(ContextCompat.getColor(context, R.color.text_color_1))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(100, 16, 100, 16) // Optional: Add margins for spacing
            }
        }
    }

}