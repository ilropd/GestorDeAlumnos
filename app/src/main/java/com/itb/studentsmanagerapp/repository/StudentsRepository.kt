package com.itb.studentsmanagerapp.repository

import com.itb.studentsmanagerapp.data.FakeStudentsDataSourse
import com.itb.studentsmanagerapp.data.model.Student

interface StudentsRepository {
    suspend fun getStudents() : List<Student>
}

class StudentsRepositoryImpl(
    private val remoteDataSource: FakeStudentsDataSourse
) : StudentsRepository {
    override suspend fun getStudents(): List<Student> {
        return remoteDataSource.students
    }
}

