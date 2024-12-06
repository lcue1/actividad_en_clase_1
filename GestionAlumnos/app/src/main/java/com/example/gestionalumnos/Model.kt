package com.example.gestionalumnos

class ModelStudent {

    val titleGrid = listOf("DNI", "APELLIDO", "NOMBRE", "NOTA", "CALF")
    private val studentsData: MutableList<List<String>> = mutableListOf()

    // Add a student
    fun addStudentData(dni: String, lastName: String, firstName: String, grade: String): Boolean {
        if (dni.isBlank() || lastName.isBlank() || firstName.isBlank() || grade.isBlank() ) {
            return false
        }
        val intGrade = grade.toFloat()
        val calification = when {
            intGrade >= 5 && intGrade <= 6 -> "SS"
            intGrade >= 7 && intGrade <= 8 -> "NT"
            intGrade > 8 -> "SB"
            else -> "R"
        }
        studentsData.add(listOf(dni, lastName, firstName, grade, calification))
        return true
    }

    // Retrieve all student data
    fun getAllStudents(): List<List<String>> {
        return studentsData
    }


    // search by dni
    fun searchByDNI(dni: String): List<String>? {
        return studentsData.find { it[0] == dni }
    }
}