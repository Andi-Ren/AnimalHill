package edu.uw.andir2.animalhill.repository

import androidx.annotation.WorkerThread
import edu.uw.andir2.animalhill.model.Animal
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.model.RecordDao
import edu.uw.andir2.animalhill.model.Records
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//Get animal data, records and stuff

////an abstract class that defines what we need
//interface DataRepo {
//    suspend fun getRecords(): Records
//
//}

interface DataRepo {
    fun allRecords(): Flow<List<Record>>

    suspend fun addRecord(record: Record)

    suspend fun deleteRecord()

    //suspend fun getAnimals(): List<Animal>
}

interface DataService {
    @GET("https://raw.githubusercontent.com/Andi-Ren/AnimalHill/coco/app/animals.json")
    suspend fun fetchAnimal(): Animal
}


class DataRepoRoom(private val recordDao: RecordDao): DataRepo {

    override fun allRecords(): Flow<List<Record>> {
        return recordDao.getAllRecords()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun addRecord(record: Record) {
        recordDao.insert(record)
    }

    override suspend fun deleteRecord() {
        recordDao.deleteAll()
    }

    private val animalService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DataService::class.java)

}

//class DataRepository: DataRepo {
//    override suspend fun getRecords(): List<Record> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun addRecord(record: Record) {
//        TODO("Not yet implemented")
//    }
//
//}

