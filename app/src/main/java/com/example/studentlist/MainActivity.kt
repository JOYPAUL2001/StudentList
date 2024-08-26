package com.example.studentlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.studentlist.Navigation.MainNavigation
import com.example.studentlist.Repository.StudentRepository
import com.example.studentlist.Screen.Home
import com.example.studentlist.ViewModel.AddStudentViewModel
import com.example.studentlist.ViewModel.AddStudentViewModelFactory
import com.example.studentlist.ui.theme.StudentListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Create an instance of the repository
        val repository = StudentRepository(this)

        // Create an instance of the ViewModelFactory
        val viewModelFactory = AddStudentViewModelFactory(repository)

        // Get the AddStudentViewModel using the ViewModelProvider and the factory
        val studentViewModel =
            ViewModelProvider(this, viewModelFactory)[AddStudentViewModel::class.java]
        setContent {
            StudentListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        studentViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}