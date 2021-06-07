package edu.uw.andir2.animalhill.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.model.Record
import java.text.SimpleDateFormat
import java.util.*

class RecordListAdapter : ListAdapter<Record, RecordListAdapter.RecordViewHolder>(RecordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current) // pass in record
    }

    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recordStartTime: TextView = itemView.findViewById(R.id.tvRecordStartTime)
        private val recordEndTime: TextView = itemView.findViewById(R.id.tvRecordEndTime)
        private val recordStatus: TextView = itemView.findViewById(R.id.tvRecordStatus)


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(record: Record?) {
            if (record != null) {
                recordStartTime.text = getDateTime(record.startTime)
                recordEndTime.text = getDateTime(record.endTime)
                recordStatus.text = record.status.toString()
            }

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTime(time: Long?): String? {
                return java.time.format.DateTimeFormatter.ISO_INSTANT
                    .format(time?.let { java.time.Instant.ofEpochSecond(it) })
        }

        private fun getDateTime(s: Long): String? {
            return try {
                val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm a")
                val netDate = Date(s * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }


        companion object {
            fun create(parent: ViewGroup): RecordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return RecordViewHolder(view)
            }
        }
    }

    class RecordsComparator : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.uid == newItem.uid
        }
    }
}
