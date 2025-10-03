package com.itb.studentsmanagerapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.itb.studentsmanagerapp.data.model.Student
import com.itb.studentsmanagerapp.ui.viewmodels.StudentListUIState
import com.itb.studentsmanagerapp.ui.viewmodels.StudentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsView(
    viewModel: StudentsViewModel,
    onStudentClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All students") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    )
            )
        }
    ) { paddingValues ->
        ContentStudentsView(
            uiState = uiState,
            onStudentClick = onStudentClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ContentStudentsView(
    uiState: StudentListUIState,
    modifier: Modifier = Modifier,
    onStudentClick: (Int) -> Unit
) 
{
    when (uiState) {
        is StudentListUIState.Success -> {
            if (uiState.students.isEmpty()) {
                Text("No students found")
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        items( uiState.students) { student ->
                            StudentRow(student, onStudentClick = onStudentClick,
                                modifier = Modifier.fillMaxWidth())
                         }
                    }
                }

        }
        is StudentListUIState.Loading -> {
            Text(text = "Page is loading...")
        }
        is StudentListUIState.Error -> {
            Text(text = uiState.message)
        }
    }
}

@Composable
fun StudentRow(student: Student, modifier: Modifier, onStudentClick: (Int) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = student.image)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = student.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = student.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.course,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = { onStudentClick(student.id) }) {
                Text("More")
            }
        }
    }
}
