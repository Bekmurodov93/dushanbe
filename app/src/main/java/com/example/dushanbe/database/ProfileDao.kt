package com.example.dushanbe.database

import androidx.room.*
import com.example.dushanbe.repositories.auth.User
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile")
    fun getProfile(): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromoter(vararg entity: User)

}