package com.example.aiagentdemo.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aiagentdemo.R.*
import com.example.aiagentdemo.adapter.HistoryAdapter

class DrawerController(
  private val activity: AppCompatActivity,
  private val sessionManager: SessionManager
) {
  private val drawerLayout = activity.findViewById<DrawerLayout>(id.drawerLayout)
  private val historyList = activity.findViewById<RecyclerView>(id.rvHistory)
  private val historyAdapter = HistoryAdapter { sessionId ->
    onSessionSelected?.invoke(sessionId)
    drawerLayout.closeDrawer(GravityCompat.END)
  }
  private var onSessionSelected: ((String) -> Unit)? = null

  init {
    historyList.layoutManager = LinearLayoutManager(activity)
    historyList.adapter = historyAdapter
    refreshHistory()
  }

  fun openDrawer() = drawerLayout.openDrawer(GravityCompat.END)
  fun setOnSessionSelected(cb: (String) -> Unit) {
    onSessionSelected = cb
  }

  fun refreshHistory() {
    historyAdapter.submitList(sessionManager.getSessionHistory())
  }
}
