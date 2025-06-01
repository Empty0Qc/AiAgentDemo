package com.example.aiagentdemo.renderer

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aiagentdemo.R
import io.noties.markwon.Markwon
import org.json.JSONObject

class MessageRenderer(private val context: Context) {
  val markwon = Markwon.create(context)

  fun renderMarkdown(content: String): View {
    val view = LayoutInflater.from(context).inflate(R.layout.item_markdown, null)
    val text = view.findViewById<TextView>(R.id.textMarkdown)
    markwon.setMarkdown(text, content)
    return view
  }

  fun renderCard(json: String): View {
    val view = LayoutInflater.from(context).inflate(R.layout.item_card, null)
    val obj = JSONObject(json)
    view.findViewById<TextView>(R.id.title).text = obj.optString("title")
    view.findViewById<TextView>(R.id.desc).text = obj.optString("desc")
    view.findViewById<Button>(R.id.cta).apply {
      text = obj.optString("cta", "操作")
      setOnClickListener {
        Toast.makeText(context, "点击了 ${text}", Toast.LENGTH_SHORT).show()
      }
    }
    return view
  }

  fun renderStage(content: String): View {
    val tv = TextView(context)
    tv.text = content
    tv.setTextColor(Color.parseColor("#888888"))
    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    tv.gravity = Gravity.CENTER_HORIZONTAL
    tv.setPadding(20, 16, 20, 16)
    return tv
  }

  fun renderDefault(content: String): View {
    val tv = TextView(context)
    tv.text = content
    return tv
  }
}
