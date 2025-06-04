package com.example.aiagentdemo.ui.theme

import com.google.android.material.appbar.MaterialToolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.aiagentdemo.R

class TitleBarController(private val activity: AppCompatActivity) {
  private val toolbar = activity.findViewById<MaterialToolbar>(R.id.titleBar)
  private var onNewChat: (() -> Unit)? = null
  private var onSidebar: (() -> Unit)? = null

  init {
    toolbar.setOnMenuItemClickListener { item ->
      when (item.itemId) {
        R.id.action_new_chat -> {
          onNewChat?.invoke(); true
        }
        R.id.action_sidebar -> {
          onSidebar?.invoke(); true
        }
        else -> false
      }
    }
  }

  fun setOnNewChat(cb: () -> Unit) {
    onNewChat = cb
  }

  fun setOnSidebar(cb: () -> Unit) {
    onSidebar = cb
  }
}
