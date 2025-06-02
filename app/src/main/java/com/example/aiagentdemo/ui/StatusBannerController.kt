package com.example.aiagentdemo.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiagentdemo.R

class StatusBannerController(activity: AppCompatActivity) {
  private val banner = activity.findViewById<TextView>(R.id.statusBanner)
  fun show(msg: String) {
    banner.text = msg
    banner.visibility = View.VISIBLE
  }

  fun hide() {
    banner.visibility = View.GONE
  }
}
