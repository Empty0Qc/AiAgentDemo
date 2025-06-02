package com.example.aiagentdemo.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiagentdemo.model.AgentMessage
import com.example.aiagentdemo.renderer.MessageRenderer

class MessageAdapter(
  private val renderer: MessageRenderer,
  private val messages: MutableList<AgentMessage>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

  inner class MessageViewHolder(val item: View) : RecyclerView.ViewHolder(item)

  override fun getItemViewType(position: Int): Int {
    return when (messages[position].type) {
      "markdown" -> 0
      "card" -> 1
      "stage" -> 2
      "done" -> 3
      "table" -> 4
      "flowchart" -> 5
      "custom_card" -> 6
      else -> -1
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
    val view = when (viewType) {
      0 -> renderer.renderMarkdown("")  // empty placeholder
      1 -> renderer.renderCard("{}")
      2 -> renderer.renderStage("")
      3 -> renderer.renderStage("✅ 回复结束")
      4 -> renderer.renderTable("""{"header":[],"rows":[]}""")
      5 -> renderer.renderFlowchart("")
      6 -> renderer.renderCustomCard("{}")
      else -> renderer.renderDefault("未知类型")
    }
    return MessageViewHolder(view)
  }

  override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
    val msg = messages[position]
    val view = when (msg.type) {
      "markdown" -> renderer.renderMarkdown(msg.content)
      "card" -> renderer.renderCard(msg.content)
      "stage" -> renderer.renderStage(msg.content)
      "done" -> renderer.renderStage(msg.content)
      "table" -> renderer.renderTable(msg.content)
      "flowchart" -> renderer.renderFlowchart(msg.content)
      "custom_card" -> renderer.renderCustomCard(msg.content)
      else -> renderer.renderDefault(msg.content)
    }
    (holder.itemView as? ViewGroup)?.removeAllViews()
    (holder.itemView as? ViewGroup)?.addView(view)
  }

  override fun getItemCount(): Int = messages.size
}
