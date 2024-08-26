package com.example.studentlist.Repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.example.studentlist.Data.Students
import com.example.studentlist.Interfaces.StudentRepositoryInterface
import com.example.studentlist.UiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentRepository(private val context: Context) : StudentRepositoryInterface {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun addStudent(student: Students): UiState<String> {
        val deviceId = getDeviceId(context)

        return try {
            val studentsCollection = firestore.collection("devices")
                .document(deviceId)
                .collection("students")

            // Generate a new document reference (with a unique ID)
            val newStudentRef = studentsCollection.document()
            val studentWithId = student.copy(studentId = newStudentRef.id)

            // Add the student with the generated studentId
            newStudentRef.set(studentWithId).await()
            UiState.Success("Student added successfully")
        } catch (e: Exception) {
            UiState.Failure(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getStudents(): UiState<List<Students>> {
        val deviceId = getDeviceId(context)

        return try {
            val snapshot = firestore.collection("devices")
                .document(deviceId)
                .collection("students")
                .get()
                .await()

            val studentsList = snapshot.documents.map { documentSnapshot ->
                documentSnapshot.toObject(Students::class.java)!!.copy(studentId = documentSnapshot.id)
            }

            UiState.Success(studentsList)
        } catch (e: Exception) {
            UiState.Failure(e.localizedMessage ?: "An error occurred while fetching students")
        }
    }

    override suspend fun updateStudent(student: Students): UiState<String> {
        val deviceId = getDeviceId(context)

        return try {
            val studentsCollection = firestore.collection("devices")
                .document(deviceId)
                .collection("students")

            studentsCollection.document(student.studentId).set(student).await()
            UiState.Success("Student updated successfully")
        } catch (e: Exception) {
            UiState.Failure(e.localizedMessage ?: "An error occurred while updating the student")
        }
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
