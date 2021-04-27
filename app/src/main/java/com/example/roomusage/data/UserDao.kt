package com.example.roomusage.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomusage.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // If there is a new same new user, ignore that.
    suspend fun addUser(user : User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<User>>

    @Update
    suspend fun updateUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}