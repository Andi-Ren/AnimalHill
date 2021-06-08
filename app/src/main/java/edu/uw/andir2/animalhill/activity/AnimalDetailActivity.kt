package edu.uw.andir2.animalhill.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.uw.andir2.animalhill.databinding.ActivityAnimalDetailBinding

const val NAME = "name"

fun navigateToDetailActivity(context: Context, name: String) {
    val intent = Intent(context, AnimalDetailActivity::class.java)
    val bundle = Bundle().apply {
        putString(NAME, name)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class AnimalDetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAnimalDetailBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            val currentName = intent.getStringExtra(NAME)
            name.text = currentName
        }
    }
}