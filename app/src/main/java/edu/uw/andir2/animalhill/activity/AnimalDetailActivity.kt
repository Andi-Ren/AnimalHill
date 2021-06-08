package edu.uw.andir2.animalhill.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.databinding.ActivityAnimalDetailBinding
import coil.load

const val NAME = "name"
const val DESC = "desc"
const val URL = "url"

fun navigateToDetailActivity(context: Context, name: String, desc: String, url: String) {
    val intent = Intent(context, AnimalDetailActivity::class.java)
    val bundle = Bundle().apply {
        putString(NAME, name)
        putString(DESC, desc)
        putString(URL, url)
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
            val currentDesc = intent.getStringExtra(DESC)
            val currentURL = intent.getStringExtra(URL)
            name.text = currentName
            description.text = currentDesc
            animalImg.load(currentURL)
        }

        var actionBar = supportActionBar
        actionBar!!.title = "Animal Detail"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}