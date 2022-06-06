package com.example.dushanbe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dushanbe.repositories.helper.Hospital
import com.example.dushanbe.repositories.helper.HospitalType
import kotlinx.coroutines.flow.Flow

@Dao
interface HelperDao {
    @Query("SELECT * FROM hospitals")
    fun getHospitals(): Flow<List<Hospital>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitals(hospitals:List<Hospital>)

    @Query("SELECT * FROM hospital_type")
    fun getHospitalTypes(): Flow<List<HospitalType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitalTypes(hospitalTypes:List<HospitalType>)
}