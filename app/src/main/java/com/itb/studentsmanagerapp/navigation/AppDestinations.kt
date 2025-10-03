package com.itb.studentsmanagerapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.itb.studentsmanagerapp.ui.viewmodels.StudentsViewModel
import com.itb.studentsmanagerapp.views.StudentDetailView
import com.itb.studentsmanagerapp.views.StudentsView

sealed class AppDestination(val route: String) {
    object StudentsList : AppDestination("students_list")
    object StudentDetail : AppDestination("student_detail/{studentId}")  {
        fun createRoute(studentId: Int) = "student_detail/$studentId"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: StudentsViewModel,
    startDestination: String = AppDestination.StudentsList.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = AppDestination.StudentsList.route
        ) {
            StudentsView(
                viewModel = viewModel,
                onStudentClick = { studentId ->
                    navController.navigate(AppDestination.StudentDetail.createRoute(studentId))
                }
            )
        }

        composable(
            route = AppDestination.StudentDetail.route,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId")
            if (studentId != null) {
                StudentDetailView(
                    studentId = studentId,
                    modifier = modifier,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Invalid student ID")
                }
            }
        }
    }
}