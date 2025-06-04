package com.example.aiagentdemo.model

/**
 * Data record for a chat session.
 *
 * @property id   Unique session identifier
 * @property name Display name shown in the history list
 * @property time Human readable timestamp
 */
data class SessionRecord(
  val id: String,
  val name: String,
  val time: String
)
