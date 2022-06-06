package com.example.dushanbe.database

import androidx.room.*
import com.example.dushanbe.repositories.register.Register
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient")
    fun getPatients(): Flow<List<Register>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(vararg entity: Register)

    @Delete
    suspend fun deletePatient(register: Register)

}