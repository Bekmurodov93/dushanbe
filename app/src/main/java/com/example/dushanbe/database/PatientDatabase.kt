package com.example.dushanbe.database

import androidx.room.*
import com.example.dushanbe.repositories.auth.User
import com.example.dushanbe.repositories.helper.Hospital
import com.example.dushanbe.repositories.helper.HospitalType
import com.example.dushanbe.repositories.register.Register

import com.example.dushanbe.utils.Constants.DB_VERSION
import javax.inject.Singleton

@Database(
    entities = [User::class, Register::class,HospitalType::class,Hospital::class],
    version = DB_VERSION,
)
@Singleton
abstract class PatientDatabase : RoomDatabase() {
    abstract fun profileDao():ProfileDao
    abstract fun patientDao():PatientDao
    abstract fun helperDao():HelperDao
}
