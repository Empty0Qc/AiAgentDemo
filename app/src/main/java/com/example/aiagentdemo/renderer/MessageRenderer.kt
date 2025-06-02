package com.example.aiagentdemo.renderer

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
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

  fun renderTable(json: String): View {
    val view = LayoutInflater.from(context).inflate(R.layout.item_table, null)
    val table = view.findViewById<TableLayout>(R.id.tableLayout)
    val obj = JSONObject(json)
    val headers = obj.optJSONArray("header")
    val rows = obj.optJSONArray("rows")

    if (headers != null) {
      val headerRow = TableRow(context)
      for (i in 0 until headers.length()) {
        val tv = TextView(context)
        tv.text = headers.getString(i)
        tv.setPadding(8, 8, 8, 8)
        tv.setTypeface(null, android.graphics.Typeface.BOLD)
        headerRow.addView(tv)
      }
      table.addView(headerRow)
    }

    if (rows != null) {
      for (i in 0 until rows.length()) {
        val row = rows.getJSONArray(i)
        val tableRow = TableRow(context)
        for (j in 0 until row.length()) {
          val tv = TextView(context)
          tv.text = row.getString(j)
          tv.setPadding(8, 8, 8, 8)
          tableRow.addView(tv)
        }
        table.addView(tableRow)
      }
    }

    return view
  }

  fun renderFlowchart(url: String): View {
    val view = LayoutInflater.from(context).inflate(R.layout.item_flowchart, null)
    val img = view.findViewById<ImageView>(R.id.flowchartImage)
    Glide.with(context).load(url).into(img)
    return view
  }

  fun renderCustomCard(json: String): View {
    val view = LayoutInflater.from(context).inflate(R.layout.item_custom_card, null)
    val obj = JSONObject(json)
    view.findViewById<TextView>(R.id.customTitle).text = obj.optString("title")
    view.findViewById<TextView>(R.id.customDesc).text = obj.optString("desc")
    view.findViewById<Button>(R.id.customCTA).apply {
      text = obj.optString("cta")
      setOnClickListener {
        Toast.makeText(context, "点击了 $text", Toast.LENGTH_SHORT).show()
      }
    }
    return view
  }

}
