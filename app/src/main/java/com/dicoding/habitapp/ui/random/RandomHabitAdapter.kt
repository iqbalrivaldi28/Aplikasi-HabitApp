package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        private val priority : ImageView = itemView.findViewById(R.id.pager_priority_level)
        private val startime : TextView = itemView.findViewById(R.id.pager_tv_start_time)
        private val pageTitle : TextView = itemView.findViewById(R.id.pager_tv_title)
        private val btnCountDown : Button = itemView.findViewById(R.id.btn_open_count_down)
        private val minute : TextView = itemView.findViewById(R.id.pager_tv_minutes)

        fun bind(pageType: PageType, pageData: Habit) {
            pageTitle.text = pageData.title
            startime.text = pageData.startTime
            minute.text = pageData.minutesFocus.toString()

            when(pageType)
            {
                PageType.HIGH -> priority.setImageResource(R.drawable.ic_priority_high)
                PageType.MEDIUM -> priority.setImageResource(R.drawable.ic_priority_medium)
                else -> priority.setImageResource(R.drawable.ic_priority_low)
            }

            btnCountDown.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
