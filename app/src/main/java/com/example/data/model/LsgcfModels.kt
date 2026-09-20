package com.example.data.model

data class SermonVideo(
    val id: String,
    val title: String,
    val speaker: String,
    val date: String,
    val series: String,
    val duration: String,
    val scriptureRef: String,
    val description: String,
    val videoGradientColors: List<Long> = listOf(0xFF2C1810, 0xFF6E381A, 0xFFC4973B)
)

data class DoctrinePillar(
    val id: String,
    val title: String,
    val subtitle: String,
    val summary: String,
    val keyVerses: List<String>,
    val explanation: String
)

data class DiscipleshipLesson(
    val number: Int,
    val title: String,
    val memoryVerse: String,
    val scripturePassage: String,
    val summary: String,
    val keyTakeaways: List<String>,
    val reflectionQuestion: String,
    var isCompleted: Boolean = false
)

data class StatementOfFaithArticle(
    val articleNumber: String,
    val title: String,
    val statement: String,
    val scripturalProofs: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String
)
