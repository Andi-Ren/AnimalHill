package edu.uw.andir2.animalhill.repository

import androidx.annotation.WorkerThread
import edu.uw.andir2.animalhill.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
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

    fun allAnimals(): List<Animal>

    suspend fun addAnimal(animal:Animal)

    suspend fun deleteAnimal()

}


class DataRepoRoom(private val recordDao: RecordDao, private val animalDao: AnimalDao): DataRepo {

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

    override fun allAnimals(): List<Animal> {
        return animalDao.getAllAnimals()
    }

    override suspend fun addAnimal(animal: Animal) {
        animalDao.insertAnimal(animal)
    }

    override suspend fun deleteAnimal() {
        animalDao.deleteAnimals()
    }

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

