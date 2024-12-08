package com.example.gestionalumnos

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.Serializable
import kotlin.contracts.contract


data class ModelStudent(
    val titleGrid: List<String> = listOf("DNI", "APELLIDO", "NOMBRE", "NOTA", "CALF"),
    private var studentsData: MutableList<List<String>> = mutableListOf()
) : Serializable {


    // Add a student
    fun addStudentData(context: Context, dni: String, lastName: String, firstName: String, grade: String): Boolean {
       if(!this.validateInput(context, false,dni,lastName,firstName,grade)) return false
        return try {
            val calification = this.setCalification(grade)
            val newStudent = listOf(dni, lastName, firstName, grade, calification)
            Log.d("add", "Added student: $newStudent")
            studentsData.add(newStudent)
            saveDataToFile(context) // Save updated list to file
        } catch (e: NumberFormatException) {
            Log.e("ModelStudent", "Grade must be a valid number", e)
            false
        }
    }

    private fun setCalification(grade: String):String{
        val decimalGrade = grade.toFloat()
        return when {
            decimalGrade >= 5 && decimalGrade <= 6 -> "SS"
            decimalGrade >= 7 && decimalGrade <= 8 -> "NT"
            decimalGrade > 8 -> "SB"
            else -> "R"
        }
    }


    fun validateInput(context: Context, update: Boolean =false, dni: String, lastName: String, firstName: String, grade: String): Boolean {
        if(dni.length!=10  ){
            Toast.makeText(context,"El DNI es incorreccto",Toast.LENGTH_LONG).show()
            return false
        }
        if(!update && searchByDNI(context,dni)!=null){
            Toast.makeText(context,"El DNI ya existe",Toast.LENGTH_LONG).show()
            return  false
        }

        if (dni.isBlank() || lastName.isBlank() || firstName.isBlank() || grade.isBlank() ) {
            Toast.makeText(context,"Llene todos los campos",Toast.LENGTH_LONG).show()
            return false
        }
        if(grade.toFloat()<0 || grade.toFloat()>10){
            Toast.makeText(context,"La nota debe ser de 0 hasta 10",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun editStudent(context: Context, dni: String, lastName: String, firstName: String, grade: String): Boolean {
        if(!this.validateInput(context, true,dni,lastName,firstName,grade)) return false
        return try {
            // Set the calification based on the grade
            val calification = this.setCalification(grade)

            // Prepare the updated student record
            val updatedStudent = listOf(dni, lastName, firstName, grade, calification)

            // Load existing students from the file
            studentsData.clear()
            studentsData = this.getDataFromFile(context)

            // Find the student by DNI and update their record
            val index = studentsData.indexOfFirst { it[0] == dni }
            if (index != -1) {
                // Update the record
                studentsData[index] = updatedStudent

                // Write the updated data back to the file
                val file = File(context.filesDir, "students.txt")
                val lines = studentsData.joinToString("\n") { it.joinToString(",") }
                file.writeText(lines)

                Log.d("ModelStudent", "Student data updated successfully.")
                true
            } else {
                Log.w("ModelStudent", "Student with DNI $dni not found.")
                false
            }

        } catch (e: Exception) {
            Log.e("ModelStudent", "Error updating student record", e)
            false
        }

    }
    // Search by DNI
    fun searchByDNI(contract:Context, dni: String): List<String>? {

        val studentsData = this.getDataFromFile(contract )
        if(studentsData.isEmpty()) return null

         return studentsData.find { it[0] == dni }.also {
            Log.d("search  DNI  ", " $dni: $it")
        }
    }

    // Save data to file
    /*
    fun saveDataToFile(context: Context): Boolean {
    return try {
        val file = File(context.filesDir, "students.txt")

        // Convert the latest student data to a formatted string
        val newLines = studentsData.joinToString("\n") { it.joinToString(",") }

        // If the file already exists, append only new data
        if (file.exists()) {
            file.appendText("$newLines\n")
        } else {
            // If the file doesn't exist, write all data
            file.writeText("$newLines\n")
        }

        Log.d("ModelStudent", "Data saved to ${file.absolutePath}")
        true
    } catch (e: Exception) {
        Log.e("ModelStudent", "Error saving data to file", e)
        false
    }
}
     */
    fun saveDataToFile(context: Context): Boolean {
        return try {
            val file = File(context.filesDir, "students.txt")
            val lines = studentsData.joinToString("\n") { it.joinToString(",") }
            file.writeText(lines)
            //file.writeText("")
            Log.d("ModelStudent", "Data saved to ${file.absolutePath}")
            true
        } catch (e: Exception) {
            Log.e("ModelStudent", "Error saving data to file", e)
            false
        }
    }

    // Load data from file

    fun getDataFromFile(context: Context): MutableList<List<String>> {
        return try {
            val file = File(context.filesDir, "students.txt")
            if (file.exists()) {
                val lines = file.readLines()
                studentsData.clear() // Clear existing data
                lines.forEach { line ->
                    val values = line.split(",")
                    if (values.size == 5) studentsData.add(values) // Add parsed data
                }

                Log.d("ModelStudent", "Data loaded from ${file.absolutePath}")
            } else {
                Log.w("ModelStudent", "File not found: ${file.absolutePath}")
            }
            studentsData // Return the updated list
        } catch (e: Exception) {
            Log.e("ModelStudent", "Error loading data from file", e)
            studentsData // Return the list, even if there's an error
        }
    }

}