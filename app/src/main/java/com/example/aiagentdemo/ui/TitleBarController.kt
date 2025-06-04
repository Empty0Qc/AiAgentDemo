package com.example.aiagentdemo.ui.theme

import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.aiagentdemo.R

class TitleBarController(private val activity: AppCompatActivity) {
  private val btnNewChat = activity.findViewById<ImageButton>(R.id.btn_new_chat)
  private val btnSidebar = activity.findViewById<ImageButton>(R.id.btn_sidebar)
  private var onNewChat: (() -> Unit)? = null
  private var onSidebar: (() -> Unit)? = null

  init {
    btnNewChat.setOnClickListener { onNewChat?.invoke() }
    btnSidebar.setOnClickListener { onSidebar?.invoke() }
  }

  fun setOnNewChat(cb: () -> Unit) {
    onNewChat = cb
  }

  fun setOnSidebar(cb: () -> Unit) {
    onSidebar = cb
  }
}
