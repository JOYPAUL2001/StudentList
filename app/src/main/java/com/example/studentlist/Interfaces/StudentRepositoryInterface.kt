package com.example.studentlist.Interfaces

import com.example.studentlist.Data.Students
import com.example.studentlist.UiState

interface StudentRepositoryInterface {
    suspend fun addStudent(student: Students): UiState<String>
    suspend fun getStudents(): UiState<List<Students>>
    suspend fun updateStudent(student: Students): UiState<String>
}