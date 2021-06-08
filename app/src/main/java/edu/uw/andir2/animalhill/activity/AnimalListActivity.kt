package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import edu.uw.andir2.animalhill.databinding.ActivityAnimalListBinding

class AnimalListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAnimalListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            animalPic1.setOnClickListener {
                navigateToDetailActivity(this@AnimalListActivity, "new animal 1")
            }

            animalPic2.setOnClickListener {
                navigateToDetailActivity(this@AnimalListActivity, "new animal 2")
            }

            animalPic3.setOnClickListener {
                navigateToDetailActivity(this@AnimalListActivity, "new animal 3")
            }
        }

        var actionBar = supportActionBar
        actionBar!!.title = "Animal List"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}