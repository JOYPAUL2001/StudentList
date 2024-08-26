package com.example.studentlist.Screen

import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studentlist.Data.Students
import com.example.studentlist.Navigation.HOME_SCREEN
import com.example.studentlist.R
import com.example.studentlist.UiState
import com.example.studentlist.ViewModel.AddStudentViewModel

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun StudentDetails(
//    student: Students,
    viewModel: AddStudentViewModel, navHostController: NavHostController,
    name: String,
    std: String,
    stream: String,
    gender: String,
    state: String,
    city: String,
    phNo: String,
    admission: String
) {

    val student = Students(
        name = name,
        std = std,
        stream = stream,
        gender = gender,
        state = state,
        city = city,
        phNo = phNo,
        date_joining = admission
    )

    val context = LocalContext.current
    val studentUpdateState by viewModel.studentUpdateState.collectAsState()

    // State to manage dialog visibility and selected item
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Pair<String, String>?>(null) }


    StatusBarColor()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(color = colorResource(id = R.color.main)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.solulabpng),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        MarqueeText(
            text = "Welcome! here you can find the details of a student.",
            fontSize = 14,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .background(Color.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BottomSheetSwipeUp(
                        modifier = Modifier
                            .align(TopCenter)
                            .padding(top = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Student Information",
                        color = colorResource(id = R.color.main),
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(8) { index ->
                        when (index) {
                            0 -> FeatureButton("Name", name) {
                                selectedItem = "Name" to name
                                showDialog = true
                            }

                            1 -> FeatureButton("Standard", std) {
                                selectedItem = "Standard" to std
                                showDialog = true
                            }

                            2 -> FeatureButton("Stream", stream) {
                                selectedItem = "Stream" to stream
                                showDialog = true
                            }

                            3 -> FeatureButton("Admission date", admission) {
                                selectedItem = "Admission date" to admission
                                showDialog = true
                            }

                            4 -> FeatureButton("Gender", gender) {
                                selectedItem = "Gender" to gender
                                showDialog = true
                            }

                            5 -> FeatureButton("Contact number", phNo) {
                                selectedItem = "Contact number" to phNo
                                showDialog = true
                            }

                            6 -> FeatureButton("Home town", city) {
                                selectedItem = "Home town" to city
                                showDialog = true
                            }

                            7 -> FeatureButton("Home state", state) {
                                selectedItem = "Home state" to state
                                showDialog = true
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog && selectedItem != null) {
        CustomAlertDialog(
            title = "Update ${selectedItem!!.first}",
            initialText = selectedItem!!.second,
            onCancel = { showDialog = false },
            onUpdate = { newValue ->
                val updatedStudent = student.copy(
                    name = if (selectedItem!!.first == "Name") newValue else student.name,
                    std = if (selectedItem!!.first == "Standard") newValue else student.std,
                    date_joining = if (selectedItem!!.first == "Admission date") newValue else student.date_joining,
                    gender = if (selectedItem!!.first == "Gender") newValue else student.gender,
                    stream = if (selectedItem!!.first == "Stream") newValue else student.stream,
                    city = if (selectedItem!!.first == "Home town") newValue else student.city,
                    state = if (selectedItem!!.first == "Home state") newValue else student.state,
                    phNo = if (selectedItem!!.first == "Contact number") newValue else student.phNo,
                )
                viewModel.updateStudent(updatedStudent)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
    when (studentUpdateState) {
        is UiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp),
                color = colorResource(
                    id = R.color.main
                )
            )
        }
        is UiState.Success -> {
            Toast.makeText(
                context,
                (studentUpdateState as UiState.Success<String>).data,
                Toast.LENGTH_SHORT
            ).show()
            navHostController.navigate(HOME_SCREEN)
            viewModel.resetState()
        }

        is UiState.Failure -> {
            Toast.makeText(
                context,
                (studentUpdateState as UiState.Failure).error,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
        }

        null -> {

        }
    }

}

@Composable
fun FeatureButton(text1: String, text2: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text1,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = text2,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.secD),
                        RoundedCornerShape(90.dp)
                    )
                    .fillMaxWidth()
                    .height(4.dp)
            )

        }
    }
}

@Composable
fun MarqueeText(
    text: String,
    fontSize: Int = 16
) {
    var textWidth by remember { mutableFloatStateOf(0f) }
    var containerWidth by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = containerWidth,
        targetValue = -textWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .height(24.dp)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                containerWidth = layoutCoordinates.size.width.toFloat()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        // Ensure the Text composable calculates its full width
        Row(
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = fontSize.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Visible, // Change to ensure text is fully displayed
                modifier = Modifier
                    .offset(x = with(LocalDensity.current) { animatedOffset.toDp() })
                    .onGloballyPositioned { layoutCoordinates ->
                        textWidth = layoutCoordinates.size.width.toFloat()
                    }
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    title: String,
    initialText: String,
    onCancel: () -> Unit,
    onUpdate: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter new value") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.main),
                        focusedLabelColor = colorResource(id = R.color.main),
                        cursorColor = colorResource(id = R.color.main),
                        textColor = Color.Black,
                        backgroundColor = Color.Transparent
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdate(text)
                    onDismissRequest()
                }
            ) {
                Text("Update", color = colorResource(id = R.color.main))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = Color.DarkGray)
            }
        },
        shape = RoundedCornerShape(15.dp)
    )
}


