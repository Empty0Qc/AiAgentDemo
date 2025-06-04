package com.example.aiagentdemo.ui

import android.content.Context
import com.example.aiagentdemo.model.SessionRecord
import com.example.aiagentdemo.model.AgentMessage

class SessionManager(private val context: Context) {
  val messages = mutableListOf<AgentMessage>()
  private val sessionHistory = mutableListOf<SessionRecord>()
  private var currentSessionId: String = ""

  fun getSessionHistory(): List<SessionRecord> {
    if (sessionHistory.isEmpty()) {
      for (i in 1..2) {
        sessionHistory.add(SessionRecord("session_$i", "会话 #$i", String.format("12:%02d", i * 3)))
      }
    }
    return sessionHistory
  }

  fun loadSession(sessionId: String) {
    messages.clear()
    messages.add(AgentMessage("stage", "🔄 已恢复历史会话：$sessionId", "system"))
    currentSessionId = sessionId
  }

  fun newSession() {
    messages.clear()
    messages.add(AgentMessage("stage", "🌟 新会话已开启，请开始提问", "system"))
    currentSessionId = "session_${System.currentTimeMillis()}"
  }

  fun appendMessage(msg: AgentMessage) {
    messages.add(msg)
  }
}
