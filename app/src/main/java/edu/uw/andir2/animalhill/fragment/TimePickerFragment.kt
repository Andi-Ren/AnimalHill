package edu.uw.andir2.animalhill.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.transition.MaterialFade
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.FragmentTimePickerBinding
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.repository.DataRepo
import edu.uw.andir2.animalhill.repository.RecordRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.floor


class TimePickerFragment: Fragment() {
    private lateinit var binding: FragmentTimePickerBinding
    private lateinit var dataRepository: DataRepo
    private lateinit var animalHillApp: AnimalHillApplication
    private var startUnixTS: Long = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
    private var timer: CountDownTimer? = null
    private var appbarRef: View? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        animalHillApp = context.applicationContext as AnimalHillApplication
        dataRepository = animalHillApp.repository
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimePickerBinding.inflate(inflater)
        appbarRef = container?.rootView?.findViewById(R.id.app_bar_container)
        TransitionManager.beginDelayedTransition(binding.root, MaterialFade())
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
                    startUnixTS =  TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                    clockToTimerView()
                    startTimer(picker)
                }
                picker.show(childFragmentManager, "TAG")
            }
            fabBottom.setOnClickListener {
                finishTimer(false)
                timerToClockView()
            }
        }

        return binding.root
    }

    private fun finishTimer(status: Boolean) {
        appbarRef?.visibility = View.VISIBLE
        val endUnixTS:Long = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val record = Record(null, startUnixTS, endUnixTS, status, "102")
        lifecycleScope.launch {
            dataRepository.addRecord(record)
        }
        timer?.cancel()
    }

    private fun startTimer(picker: MaterialTimePicker) {
        with(binding) {
            appbarRef?.visibility = View.GONE
            val newHour: Int = picker.hour
            val newMinute: Int = picker.minute
            val totalS = (newHour * 60 + newMinute) * 60
            val totalMillis = (totalS * 1000).toLong()

            progressBar.max = totalS
            progressBar.progress = 0

            timer = object : CountDownTimer(totalMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val timeStr = formatTimeStr(millisUntilFinished)
                    tvCountDown.text = timeStr
                    progressBar.incrementProgressBy(1)
                }

                override fun onFinish() {
                    finishTimer(true)
                    timerToClockView()
                }
            }.start()
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