package edu.uw.andir2.animalhill.fragment

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.FragmentTimePickerBinding
import java.util.concurrent.TimeUnit
import kotlin.math.floor


class TimePickerFragment: Fragment() {
    private lateinit var binding: FragmentTimePickerBinding
    private lateinit var animalHillApp: AnimalHillApplication
    private var timer: CountDownTimer? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        animalHillApp = context.applicationContext as AnimalHillApplication
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimePickerBinding.inflate(inflater)
        with(binding) {
            val clockFormat = TimeFormat.CLOCK_24H
            progressBar.setOnClickListener {
                val picker = MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(0)
                    .setMinute(0)
                    .setTitleText("Set Timer")
                    .build()

                picker.addOnPositiveButtonClickListener {
                    clockToTimerView()
                    initTimer(picker)
                    timer?.start()
                }
                picker.show(childFragmentManager, "TAG")
            }
            fabBottom.setOnClickListener {
                cancelTimer()
                timerToClockView()
            }
        }

        return binding.root
    }

    private fun cancelTimer() {
        // TODO: Record Failed Attempt
        timer?.cancel()
    }

    private fun initTimer(picker: MaterialTimePicker) {
        with(binding) {
            val newHour: Int = picker.hour
            val newMinute: Int = picker.minute
            val totalS = (newHour * 60 + newMinute) * 60
            val totalMillis = (totalS * 1000).toLong()

            progressBar.max = totalS
            progressBar.progress = 0

            timer = object : CountDownTimer(totalMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val timeStr = formatTimeStr(millisUntilFinished);
                    tvCountDown.text = timeStr
                    progressBar.incrementProgressBy(1)
                }

                override fun onFinish() {
                    // TODO: Record Successful Attempt
                    fabBottom.visibility = View.GONE
                }
            }
        }
    }

    private fun formatTimeStr(millisUntilFinished: Long): String {
        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                    TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(
                            millisUntilFinished
                        )
                    ),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            millisUntilFinished
                        )
                    )
        )
    }

    private fun clockToTimerView() {
        with(binding) {
            progressBar.isClickable = false
            textClock1.visibility = View.INVISIBLE
            tvCountDown.visibility = View.VISIBLE
            fabBottom.visibility = View.VISIBLE
        }
    }

    private fun timerToClockView() {
        with(binding) {
            progressBar.isClickable = true
            textClock1.visibility = View.VISIBLE
            tvCountDown.visibility = View.INVISIBLE
            fabBottom.visibility = View.GONE
            progressBar.max = 100
            progressBar.progress = 100
        }
    }
}