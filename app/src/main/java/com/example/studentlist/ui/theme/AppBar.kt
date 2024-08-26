package com.example.studentlist.ui.theme

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.studentlist.R

@Composable
fun TopBarView(
    title: String,
    onBackNavClicked: () -> Unit = {}
) {
    val navigationIcon: (@Composable () -> Unit)? = {
        IconButton(onClick = {
            onBackNavClicked()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = null
            )
        }

    }
    TopAppBar(
        title = {
            Text(
                text = title, color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontStyle = FontStyle.Normal,
            )
        },

        backgroundColor = colorResource(id = R.color.main),
        navigationIcon = navigationIcon
    )

}