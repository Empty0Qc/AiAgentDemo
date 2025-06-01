package com.example.aiagentdemo.model

data class AgentMessage(
  val type: String,             // "markdown", "stage", "card", "plugin", "done"
  val content: String,
  val stage: String? = null,    // 如 "thought", "answer"
  val event: String? = null,    // "start", "stream", "end"
  val append: Boolean = false   // 是否打字机拼接
)