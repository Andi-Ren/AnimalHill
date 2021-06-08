package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.uw.andir2.animalhill.databinding.ActivityAnimalListBinding

class AnimalListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAnimalListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            imageView.setOnClickListener {
                navigateToDetailActivity(this@AnimalListActivity, "new animal")
            }
        }
    }

}