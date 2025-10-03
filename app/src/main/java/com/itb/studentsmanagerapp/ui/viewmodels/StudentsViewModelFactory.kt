package com.itb.studentsmanagerapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itb.studentsmanagerapp.data.FakeStudentsDataSourse
import com.itb.studentsmanagerapp.repository.StudentsRepository
import com.itb.studentsmanagerapp.repository.StudentsRepositoryImpl

class StudentsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentsViewModel::class.java)) {
            val repository = StudentsRepositoryImpl(FakeStudentsDataSourse)
            @Suppress("UNCHECKED_CAST")
            return StudentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}