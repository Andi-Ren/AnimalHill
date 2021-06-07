package edu.uw.andir2.animalhill.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.uw.andir2.animalhill.databinding.ActivityReminderBinding

class ReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {

        }
    }

}