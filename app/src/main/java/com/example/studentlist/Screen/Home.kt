package com.example.studentlist.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studentlist.Data.Students
import com.example.studentlist.Navigation.ADD_STUDENT
import com.example.studentlist.Navigation.HOME_SCREEN
import com.example.studentlist.Navigation.STUDENT_DETAILS
import com.example.studentlist.R
import com.example.studentlist.UiState
import com.example.studentlist.ViewModel.AddStudentViewModel
import com.example.studentlist.ui.theme.TopBarView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(viewModel: AddStudentViewModel, modifier: Modifier, navHostController: NavHostController) {
    val context = LocalContext.current
    val studentsListState by viewModel.studentsListState.collectAsState()

    // Fetch the students when this composable is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchStudents()
    }



    StatusBarColor()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.main),
                onClick = {
                    navHostController.navigate(ADD_STUDENT)
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.main)),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SoluLab Tutorial",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.solulabpng),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BottomSheetSwipeUp(
                            modifier = Modifier
                                .align(TopCenter)
                                .padding(top = 15.dp)
                        )
                    }
                    //Spacer(modifier = Modifier.height(10.dp))

                    when (studentsListState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp),
                                    color = colorResource(id = R.color.main)
                                )
                            }
                        }

                        is UiState.Success -> {
                            val students =
                                (studentsListState as UiState.Success<List<Students>>).data
                            StudentsList(students, navHostController)
                        }

                        is UiState.Failure -> {
                            Text(
                                text = (studentsListState as UiState.Failure).error,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {}
                    }
                }

            }
        }
    }
}


@Composable
fun StatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colorScheme.primary.luminance() > 0.5f
    val statusBarColor = colorResource(id = R.color.main)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
}

@Composable
fun StudentsList(
    studentsList: List<Students>,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {
        items(studentsList) {
            StudentStatus(students = it, navHostController)
        }
    }
}

@Composable
fun StudentStatus(
    students: Students,
    navHostController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .height(75.dp)
            .clickable {
                navHostController.navigate("$STUDENT_DETAILS/${students.name}/${students.std}/${students.stream}/${students.gender}/${students.state}/${students.city}/${students.phNo}/${students.date_joining}")
            },
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name: ${students.name}", style = TextStyle(
                        color = Color.Black, fontSize = 14.sp,
                        lineHeight = 24.sp, fontWeight = FontWeight.SemiBold
                    ), color = colorResource(id = R.color.main)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Class: ${students.std}", style = TextStyle(
                        color = Color.Black, fontSize = 12.sp,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = students.stream, style = TextStyle(
                        color = Color.Black, fontSize = 12.sp,
                        lineHeight = 24.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = students.gender, style = TextStyle(
                        color = Color.Black, fontSize = 12.sp,
                        lineHeight = 24.sp, fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun BottomSheetSwipeUp(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(
                Color.LightGray,
                RoundedCornerShape(90.dp)
            )
            .width(90.dp)
            .height(5.dp)
    )
}