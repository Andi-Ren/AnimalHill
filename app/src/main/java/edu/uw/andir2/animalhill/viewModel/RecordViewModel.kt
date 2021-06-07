package edu.uw.andir2.animalhill.viewModel

import androidx.lifecycle.*
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.repository.DataRepoRoom
import edu.uw.andir2.animalhill.repository.RecordRepository
import kotlinx.coroutines.launch

class RecordViewModel(private val repository: DataRepoRoom) : ViewModel() {
//class RecordViewModel(private val repository: RecordRepository) : ViewModel() {


    // Using LiveData and caching what allRecords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allRecords: LiveData<List<Record>> = repository.allRecords().asLiveData()
    //val allRecords: LiveData<List<Record>> = repository.allRecords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(record: Record) = viewModelScope.launch {
        repository.addRecord(record)
        //repository.insert(record)
    }
}

class RecordViewModelFactory(private val repository: DataRepoRoom) : ViewModelProvider.Factory {
//class RecordViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}