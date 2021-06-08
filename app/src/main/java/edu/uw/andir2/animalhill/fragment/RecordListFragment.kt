package edu.uw.andir2.animalhill.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.adapter.RecordListAdapter
import edu.uw.andir2.animalhill.databinding.FragmentRecordListBinding
import edu.uw.andir2.animalhill.model.Animal
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.repository.DataRepoRoom
import edu.uw.andir2.animalhill.repository.RecordRepository
import edu.uw.andir2.animalhill.viewModel.RecordViewModel
import edu.uw.andir2.animalhill.viewModel.RecordViewModelFactory
import kotlinx.coroutines.launch

class RecordListFragment : Fragment() {
    private lateinit var binding: FragmentRecordListBinding
    private lateinit var animalHillApp: AnimalHillApplication
    private lateinit var repository: DataRepoRoom
    //private lateinit var repository: RecordRepository

    private val recordViewModel: RecordViewModel by viewModels {
        RecordViewModelFactory(repository)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        animalHillApp = context.applicationContext as AnimalHillApplication
        repository = animalHillApp.repository
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordListBinding.inflate(inflater)
        loadRecords()
        loadAnimals()
        val adapter = RecordListAdapter()
        recordViewModel.allRecords.observe(viewLifecycleOwner, { records ->
            // Update the cached copy of the words in the adapter.
            records?.let { adapter.submitList(it) }
        })

        with(binding) {
            rvRecords.adapter = adapter
        }
        return binding.root
    }

    private fun loadRecords() {
        lifecycleScope.launch {
            repository.deleteRecord()
            repository.addRecord(Record(null, 1623025224, 1623055824, false, "102"))
            repository.addRecord(Record(null, 1623055224, 1623056224, true, "102"))
        }
    }

    private fun loadAnimals() {
        lifecycleScope.launch {
            repository.deleteAnimal()
            repository.addAnimal(Animal(null,"cat","cool cat","a cat that bully wolves","Nan",1623055824))
            repository.addAnimal(Animal(null,"pig","mama pig","mama pig can climb tree","Nan",1623255854))
            repository.addAnimal(Animal(null, "fox", "red fox", "no description", "Nan",1623251154))
            repository.addAnimal(Animal(null, "fox", "orange fox", "from Thailand", "Nan",1623222154))

        }
    }
    //System.currentTimeMillis()
}