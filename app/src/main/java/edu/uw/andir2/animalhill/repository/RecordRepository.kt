package edu.uw.andir2.animalhill.repository

import androidx.annotation.WorkerThread
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.model.RecordDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class RecordRepository(private val recordDao: RecordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allRecords: Flow<List<Record>> = recordDao.getAllRecords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(record: Record) {
        recordDao.insert(record)
    }
}