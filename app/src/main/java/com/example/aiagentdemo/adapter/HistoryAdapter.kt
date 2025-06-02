package com.example.aiagentdemo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aiagentdemo.R

class HistoryAdapter(
  private val onItemClick: (String) -> Unit
) : ListAdapter<SessionRecord, HistoryAdapter.HistoryVH>(SessionDiff) {

  class HistoryVH(val view: View) : RecyclerView.ViewHolder(view)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
    return HistoryVH(v)
  }

  override fun onBindViewHolder(holder: HistoryVH, position: Int) {
    val item = getItem(position)
    holder.view.findViewById<TextView>(R.id.tvSessionName).text = item.name
    holder.view.findViewById<TextView>(R.id.tvSessionTime).text = item.time
    holder.view.setOnClickListener { onItemClick(item.id) }
  }

  object SessionDiff : DiffUtil.ItemCallback<SessionRecord>() {
    override fun areItemsTheSame(oldItem: SessionRecord, newItem: SessionRecord) =
      oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SessionRecord, newItem: SessionRecord) =
      oldItem == newItem
  }

}

data class SessionRecord(val id: String, val name: String, val time: String)
