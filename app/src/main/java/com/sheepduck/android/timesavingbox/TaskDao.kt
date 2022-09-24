package com.sheepduck.android.timesavingbox

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM timesavingbox")
    fun loadAllTask(): Array<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)
}
