package com.itb.studentsmanagerapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itb.studentsmanagerapp.data.model.Student
import com.itb.studentsmanagerapp.repository.StudentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface StudentListUIState {
    object Loading : StudentListUIState

    data class Success (val students: List<Student>) : StudentListUIState
    data class Error(val message: String) : StudentListUIState
}

class StudentsViewModel (
    private val studentsRepository: StudentsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<StudentListUIState>(StudentListUIState.Loading)
    val uiState : StateFlow<StudentListUIState> = _uiState.asStateFlow()

    private val _selectedStudent = MutableStateFlow<Student?>(value = null)

    val selectedStudent : StateFlow<Student?> = _selectedStudent.asStateFlow()

    init {
        fetchStudent()
    }

    fun fetchStudent() {
        viewModelScope.launch {
            _uiState.value = StudentListUIState.Loading
            try {
                val students = studentsRepository.getStudents()
                _uiState.value = StudentListUIState.Success(students)

            } catch (e: Exception) {
                _uiState.value = StudentListUIState.Error(e.message ?: "List of students not found")
            }

        }
    }

    fun selectStudents(studentId: Int) {
        val currentUIState = _uiState.value

        if (currentUIState is StudentListUIState.Success) {
            _selectedStudent.value = currentUIState.students.find { it.id == studentId}
        } else {
            println("Invalid UI state for $studentId")
        }
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }
}