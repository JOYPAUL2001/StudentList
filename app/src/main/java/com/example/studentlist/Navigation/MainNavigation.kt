package com.example.studentlist.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentlist.Data.Students
import com.example.studentlist.Screen.AddStudent
import com.example.studentlist.Screen.Home
import com.example.studentlist.Screen.StudentDetails
import com.example.studentlist.ViewModel.AddStudentViewModel

@Composable
fun MainNavigation(viewModel: AddStudentViewModel, modifier: Modifier) {

    val navHostController = rememberNavController()
    //val student: Students

    NavHost(navController = navHostController, startDestination = HOME_SCREEN) {
        composable(ADD_STUDENT) {
            AddStudent(viewModel, navHostController)
        }
        composable(
            route = "$STUDENT_DETAILS/{name}/{std}/{stream}/{gender}/{state}/{city}/{phNo}/{date_joining}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("std") { type = NavType.StringType },
                navArgument("stream") { type = NavType.StringType },
                navArgument("gender") { type = NavType.StringType },
                navArgument("state") { type = NavType.StringType },
                navArgument("city") { type = NavType.StringType },
                navArgument("phNo") { type = NavType.StringType },
                navArgument("date_joining") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val std = backStackEntry.arguments?.getString("std") ?: ""
            val stream = backStackEntry.arguments?.getString("stream") ?: ""
            val gender = backStackEntry.arguments?.getString("gender") ?: ""
            val state = backStackEntry.arguments?.getString("state") ?: ""
            val city = backStackEntry.arguments?.getString("city") ?: ""
            val phNo = backStackEntry.arguments?.getString("phNo") ?: ""
            val admission = backStackEntry.arguments?.getString("date_joining") ?: ""
            StudentDetails(viewModel, navHostController, name, std, stream, gender, state, city, phNo, admission)
        }
        composable(HOME_SCREEN) {
            Home(viewModel, modifier = modifier, navHostController)
        }
    }

}

const val HOME_SCREEN = "home screen"
const val ADD_STUDENT = "add student"
const val STUDENT_DETAILS = "student details"