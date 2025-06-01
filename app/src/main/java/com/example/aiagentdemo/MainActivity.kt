package com.example.aiagentdemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aiagentdemo.adapter.MessageAdapter
import com.example.aiagentdemo.model.AgentMessage
import com.example.aiagentdemo.renderer.MessageRenderer

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: MessageAdapter
  private val messages = mutableListOf<AgentMessage>()
  private var thoughtStartTime: Long = 0L
  private lateinit var statusBanner: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    statusBanner = findViewById(R.id.statusBanner)
    // 使用 findViewById 初始化 RecyclerView
    recyclerView = findViewById(R.id.recycler)
    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = MessageAdapter(MessageRenderer(this), messages)
    recyclerView.adapter = adapter

    simulateStreaming()
  }

  private fun simulateStreaming() {
    val handler = Handler(Looper.getMainLooper())

    val chunks = listOf(
      AgentMessage("stage", "🧠 思考开始...", "thought", "start"),
      AgentMessage("markdown", "- 我正在分析你的打车需求...", "thought", "stream", append = true),
      AgentMessage(
        "card",
        """{"title":"推荐打车点","desc":"国贸地铁东口","cta":"导航"}""",
        "thought",
        "stream"
      ),
      AgentMessage("stage", "✅ 思考结束", "thought", "end"),

      AgentMessage("stage", "💬 开始正文...", "answer", "start"),
      AgentMessage("markdown", "- 步行 100m 至国贸东口", "answer", "stream", append = true),
      AgentMessage(
        "card",
        """{"title":"快车推荐","desc":"约 15 元，2 分钟内到达","cta":"呼叫快车"}""",
        "answer",
        "stream"
      ),
      AgentMessage("done", "✅ 回复结束")
    )

    chunks.forEachIndexed { index, msg ->
      handler.postDelayed({
        handleMessage(msg)
      }, index * 1500L)
    }
  }

  private fun handleMessage(msg: AgentMessage) {
    // 处理状态栏阶段信息
    if (msg.type == "stage") {
      if (msg.event == "start" && msg.stage == "thought") {
        statusBanner.text = "🧠 思考中..."
        thoughtStartTime = System.currentTimeMillis()
      }
      if (msg.event == "start" && msg.stage == "answer") {
        val duration = (System.currentTimeMillis() - thoughtStartTime) / 1000
        statusBanner.text = "💬 进入正文，思考耗时 ${duration}s"
      }
    }

    messages.add(msg)
    adapter.notifyItemInserted(messages.size - 1)
    recyclerView.scrollToPosition(messages.size - 1)
  }
}
