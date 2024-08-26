package com.example.studentlist.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentlist.Data.Students
import com.example.studentlist.Repository.StudentRepository
import com.example.studentlist.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddStudentViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _studentAddState = MutableStateFlow<UiState<String>?>(null)
    val studentAddState: StateFlow<UiState<String>?> = _studentAddState

    private val _studentsListState = MutableStateFlow<UiState<List<Students>>?>(null)
    val studentsListState: StateFlow<UiState<List<Students>>?> = _studentsListState

    private val _studentUpdateState = MutableStateFlow<UiState<String>?>(null)
    val studentUpdateState: StateFlow<UiState<String>?> = _studentUpdateState

    fun addStudent(student: Students) {
        viewModelScope.launch {
            _studentAddState.value = UiState.Loading
            val result = repository.addStudent(student)
            _studentAddState.value = result
        }
    }

    fun fetchStudents() {
        viewModelScope.launch {
            _studentsListState.value = UiState.Loading
            val result = repository.getStudents()
            _studentsListState.value = result
        }
    }

    fun updateStudent(student: Students) {
        viewModelScope.launch {
            _studentUpdateState.value = UiState.Loading
            val result = repository.updateStudent(student)
            _studentUpdateState.value = result
        }
    }


    fun setLoadingState() {
        _studentAddState.value = UiState.Loading
    }

    fun resetState() {
        _studentAddState.value = null // Reset state after success or failure
    }
}

/*
Use StateFlow when**:

You are already using Kotlin coroutines and prefer a more modern and coroutine-friendly approach.
You need more control over the flow of data, especially when dealing with multiple collectors.
Lifecycle management is not needed or is handled elsewhere.


Use LiveData when**:

You need lifecycle awareness, particularly if you want your observers (like activities or fragments)
to be updated only when they are active.
You want a simpler API that integrates seamlessly with Android components like ViewModel.


Conclusion:
If your project is heavily coroutine-based, StateFlow might be more appropriate.
If you’re dealing with UI data in an Android architecture where lifecycle awareness is crucial,
LiveData might be more suitable.
 */
