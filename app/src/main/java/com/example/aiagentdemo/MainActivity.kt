package com.example.aiagentdemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import org.json.JSONObject
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aiagentdemo.adapter.MessageAdapter
import com.example.aiagentdemo.model.AgentMessage
import com.example.aiagentdemo.renderer.MessageRenderer
import com.example.aiagentdemo.ui.DrawerController
import com.example.aiagentdemo.ui.InputAreaController
import com.example.aiagentdemo.ui.SessionManager
import com.example.aiagentdemo.ui.StatusBannerController
import com.example.aiagentdemo.ui.theme.TitleBarController

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView

  private lateinit var titleBar: TitleBarController
  private lateinit var banner: StatusBannerController
  private lateinit var inputArea: InputAreaController
  private lateinit var drawer: DrawerController
  private lateinit var sessionManager: SessionManager
  private lateinit var messageAdapter: MessageAdapter
  private val httpClient = OkHttpClient()
  private var eventSource: EventSource? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // 初始化业务模块
    titleBar = TitleBarController(this)
    banner = StatusBannerController(this)
    inputArea = InputAreaController(this) { message ->
      // 用户发消息回调
      sessionManager.appendMessage(message)
      messageAdapter.notifyItemInserted(sessionManager.messages.size - 1)
      // ai作答
      startSseStream()
    }
    sessionManager = SessionManager(this)
    drawer = DrawerController(this, sessionManager)

    // 消息流RecyclerView绑定
    recyclerView = findViewById<RecyclerView>(R.id.recycler)
    messageAdapter = MessageAdapter(MessageRenderer(this), sessionManager.messages)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = messageAdapter

    // 各业务模块互相协作
    titleBar.setOnNewChat { sessionManager.newSession(); messageAdapter.notifyDataSetChanged() }
    titleBar.setOnSidebar { drawer.openDrawer() }
    drawer.setOnSessionSelected { sessionId ->
      sessionManager.loadSession(sessionId)
      messageAdapter.notifyDataSetChanged()
    }
  }

  private fun simulateStreaming() {
    val handler = Handler(Looper.getMainLooper())

    val chunks = listOf(
      AgentMessage("stage", "🧠 思考开始...", "thought", "start", role = "user"),
      AgentMessage(
        "markdown",
        "- 我正在分析你的打车需求...",
        "thought",
        "stream",
        append = true,
        "bot"
      ),
      AgentMessage(
        "card",
        """{"title":"推荐打车点","desc":"国贸地铁东口","cta":"导航"}""",
        "thought",
        "stream",
        role = "bot"
      ),
      AgentMessage("stage", "✅ 思考结束", "thought", "end", role = "bot"),

      AgentMessage("stage", "💬 开始正文...", "answer", "start", role = "bot"),
      AgentMessage("markdown", "- 步行 100m 至国贸东口", "answer", "stream", append = true, "bot"),
      AgentMessage(
        "card",
        """{"title":"快车推荐","desc":"约 15 元，2 分钟内到达","cta":"呼叫快车"}""",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage(
        "markdown",
        "### 🚕 附加出行建议 \n - 可以考虑地铁：国贸站换乘1号线 \n - 高峰期建议错峰打车",
        "answer",
        "stream",
        append = true,
        "bot"
      ),
      AgentMessage(
        "card",
        """{"title":"共享单车","desc":"2 分钟可骑行至地铁口","cta":"扫码骑行"}""",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage(
        "table",
        """{"header":["方案","价格","时间"],"rows":[["快车","15元","5分钟"],["地铁","3元","10分钟"]]}""",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage(
        "flowchart",
        "https://i.miji.bid/2025/06/02/c9b601d0baf5099d79c8982ac323b152.jpeg",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage(
        "custom_card",
        """{"title":"AI 建议","desc":"当前天气良好，适合步行","cta":"查看天气"}""",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage(
        "markdown",
        "#### ✅ 小贴士 \n - 可收藏常用出发点 \n - 避免高峰期等待",
        "answer",
        "stream",
        role = "bot"
      ),
      AgentMessage("done", "✅ 回复结束", role = "bot")
    )

    chunks.forEachIndexed { index, msg ->
      handler.postDelayed({
        handleMessage(msg)
      }, index * 1500L)
    }
  }

  private fun startSseStream() {
    eventSource?.cancel()
    val request = Request.Builder()
      .url("http://10.0.2.2:3000/sse")
      .build()
    eventSource = EventSources.createFactory(httpClient)
      .newEventSource(request, object : EventSourceListener() {
        override fun onEvent(source: EventSource, id: String?, type: String?, data: String) {
          if (type == "end" || data == "[DONE]") {
            source.cancel()
            return
          }
          val obj = JSONObject(data)
          val msg = AgentMessage(
            obj.optString("type"),
            obj.optString("content"),
            obj.optString("stage"),
            obj.optString("event"),
            obj.optBoolean("append"),
            obj.optString("role")
          )
          runOnUiThread { handleMessage(msg) }
        }
      })
  }

  private fun handleMessage(msg: AgentMessage) {
    sessionManager.appendMessage(msg)
    messageAdapter.notifyItemInserted(sessionManager.messages.size - 1)
    recyclerView.scrollToPosition(sessionManager.messages.size - 1)
  }
}
