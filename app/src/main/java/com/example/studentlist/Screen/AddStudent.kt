package com.example.studentlist.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studentlist.Data.Students
import com.example.studentlist.Navigation.HOME_SCREEN
import com.example.studentlist.R
import com.example.studentlist.UiState
import com.example.studentlist.ViewModel.AddStudentViewModel
import com.example.studentlist.ui.theme.TopBarView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddStudent(viewModel: AddStudentViewModel, navHostController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var standard by remember { mutableStateOf("") }
    var stream by remember { mutableStateOf("") }
    var dateOfJoining by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phNumber by remember { mutableStateOf("") }

    val context = LocalContext.current
    val studentAddState by viewModel.studentAddState.collectAsState()

    Scaffold(
        topBar = {
            TopBarView(title = "Add Student",
                {
                    navHostController.navigate(HOME_SCREEN)
                }
            )
        },
        modifier = Modifier.statusBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = "Please include the student's details here.",
                    style = MaterialTheme.typography.titleMedium, fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
                )

                // Add each field and ensure state is updated on value change
                ConfirmFieldWithLabel(
                    label = "Name",
                    value = name,
                    placeholders = "Enter your name...",
                    onValueChange = { name = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "Standard",
                    value = standard,
                    placeholders = "1/2/3../X",
                    onValueChange = { standard = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "Admission Date",
                    value = dateOfJoining,
                    placeholders = "XX-XX-20XX",
                    onValueChange = { dateOfJoining = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "Gender",
                    value = gender,
                    placeholders = "Male/Female",
                    onValueChange = { gender = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "Stream",
                    value = stream,
                    placeholders = "Physics/Mathematics...",
                    onValueChange = { stream = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "Phone Number",
                    value = phNumber,
                    placeholders = "XXXXXX4014",
                    onValueChange = { phNumber = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "City",
                    value = city,
                    placeholders = "Kolkata/Berlin/XXXX...",
                    onValueChange = { city = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ConfirmFieldWithLabel(
                    label = "State",
                    value = state,
                    placeholders = "West Bengal/Tamil......",
                    onValueChange = { state = it }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.isNotBlank() && standard.isNotBlank() && phNumber.isNotBlank() && !dateOfJoining.contains(
                                "/"
                            )
                        ) {
                            viewModel.setLoadingState()
                            val student = Students(
                                name = name,
                                std = standard,
                                stream = stream,
                                date_joining = dateOfJoining,
                                city = city,
                                state = state,
                                gender = gender,
                                phNo = phNumber
                            )
                            viewModel.addStudent(student)
                        } else {
                            if (dateOfJoining.contains("/")) {
                                Toast.makeText(
                                    context,
                                    "Don't put / in the admission date field! Please follow this convention (XX-XX-X0X5)",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill all the required fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.main),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Add Student")
                }
                when (studentAddState) {
                    is UiState.Loading -> {
                        //Text("Adding student...", color = Color.Gray)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp, bottom = 10.dp),
                            color = colorResource(
                                id = R.color.main
                            )
                        )
                    }

                    is UiState.Success -> {
                        Toast.makeText(
                            context,
                            (studentAddState as UiState.Success<String>).data,
                            Toast.LENGTH_SHORT
                        ).show()
                        navHostController.navigate(HOME_SCREEN)
                        viewModel.resetState()
                    }

                    is UiState.Failure -> {
                        Toast.makeText(
                            context,
                            (studentAddState as UiState.Failure).error,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetState()
                    }

                    null -> {

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmFieldWithLabel(
    label: String,
    value: String,
    placeholders: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(placeholders, style = TextStyle(fontSize = 14.sp), color = Color.Gray)
                }
            },
            value = value,
            onValueChange = onValueChange,
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp),
            textStyle = TextStyle(fontSize = 14.sp, textAlign = TextAlign.Start),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.main),
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}
