package edu.uw.andir2.animalhill.repository

import androidx.annotation.WorkerThread
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.model.Records
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

//Get animal data, records and stuff

////an abstract class that defines what we need
//interface DataRepo {
//    suspend fun getRecords(): Records
//
//}

interface DataRepo {
    suspend fun getRecords(): List<Record>

    suspend fun addRecord(record: Record)

    //suspend fun getAnimals(): List<Animal>
}


class DataRepoRoom(private val recordDao: RecordDAO): DataRepo {
    override suspend fun getRecords(): List<Record> {
        return recordDao.getAllRecords()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun addRecord(record: Record) {
        recordDao.insert(record)
    }

}

class DataRepository: DataRepo {
    override suspend fun getRecords(): List<Record> {
        TODO("Not yet implemented")
    }

    override suspend fun addRecord(record: Record) {
        TODO("Not yet implemented")
    }

}

