package com.itb.studentsmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.itb.studentsmanagerapp.navigation.AppNavHost
import com.itb.studentsmanagerapp.ui.theme.StudentsManagerAppTheme
import com.itb.studentsmanagerapp.ui.viewmodels.StudentsViewModel
import com.itb.studentsmanagerapp.ui.viewmodels.StudentsViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: StudentsViewModel by viewModels { StudentsViewModelFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentsManagerAppTheme {
                StudentsAppLaunch(viewModel)

            }
        }
    }
}

@Composable
fun StudentsAppLaunch(viewModel: StudentsViewModel) {
    AppNavHost(viewModel = viewModel,
        modifier = Modifier.fillMaxSize(),
        navController = rememberNavController())

}