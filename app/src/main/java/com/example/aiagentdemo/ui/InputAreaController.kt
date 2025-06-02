package com.example.aiagentdemo.ui

import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.aiagentdemo.R
import com.example.aiagentdemo.model.AgentMessage

class InputAreaController(
  activity: AppCompatActivity, private val onSend: (AgentMessage) -> Unit
) {
  private val inputField = activity.findViewById<EditText>(R.id.inputField)
  private val sendPauseBtn = activity.findViewById<ImageButton>(R.id.sendPauseButton)
  private val btnDeepSearch = activity.findViewById<Button>(R.id.btn_deep_search)
  private val btnWebSearch = activity.findViewById<Button>(R.id.btn_web_search)
  private var isSending = false
  private var deepSearchSelected = false
  private var webSearchSelected = false
  init {
    sendPauseBtn.setOnClickListener {
      isSending = !isSending
      sendPauseBtn.setImageResource(
        if (isSending) R.drawable.ic_pause else android.R.drawable.ic_menu_send
      )
      if (isSending) {
        val text = inputField.text.toString().trim()
        if (text.isNotEmpty()) {
          // 发送消息
          onSend(AgentMessage(type = "markdown", content = text, role = "user"))
          // ...
          inputField.setText("")
        }
      } else {
        // 暂停AI生成逻辑
      }
    }
    // 这里也可以添加深度/联网搜索的监听


    btnDeepSearch.setOnClickListener {
      deepSearchSelected = !deepSearchSelected
      btnDeepSearch.setBackgroundResource(
        if (deepSearchSelected) R.drawable.bg_btn_search_selected else R.drawable.bg_btn_search_default
      )
      btnDeepSearch.setTextColor(
        if (deepSearchSelected) Color.GRAY else Color.BLACK
      )
      // 可扩展业务逻辑
    }
    btnWebSearch.setOnClickListener {
      webSearchSelected = !webSearchSelected
      btnWebSearch.setBackgroundResource(
        if (webSearchSelected) R.drawable.bg_btn_search_selected else R.drawable.bg_btn_search_default
      )
      btnWebSearch.setTextColor(
        if (webSearchSelected) Color.GRAY else Color.BLACK
      )
      // 可扩展业务逻辑
    }
  }
}
