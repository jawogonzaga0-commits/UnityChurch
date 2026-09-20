package com.example.data.model

data class BibleBook(
    val id: String,
    val name: String,
    val testament: String, // "Old Testament" or "New Testament"
    val chaptersCount: Int,
    val category: String // "Gospels", "Epistles", "Wisdom", "Law", "History"
)

data class Verse(
    val bookId: String,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String
)

data class BibleHighlight(
    val id: Long = 0,
    val bookId: String,
    val bookName: String,
    val chapter: Int,
    val verseNumber: Int,
    val textSnippet: String,
    val colorHex: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class DailyVerse(
    val reference: String,
    val text: String,
    val theme: String,
    val devotion: String,
    val date: String
)
