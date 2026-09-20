package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.BibleHighlight
import com.example.data.model.Verse
import com.example.data.repository.BibleRepository
import com.example.notification.NotificationHelper
import com.example.ui.components.DailyVerseCard
import com.example.ui.components.HighlightColorPicker
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.HolyGold
import com.example.ui.theme.SoftGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleScreen(
    bibleRepository: BibleRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val books = bibleRepository.booksList
    var selectedBookIndex by remember { mutableIntStateOf(1) } // Default to Psalms
    val currentBook = books[selectedBookIndex]

    var selectedChapter by remember {
        // Default to Psalm 23 or 1
        mutableIntStateOf(if (currentBook.id == "PSA") 23 else 1)
    }

    val verses = remember(currentBook.id, selectedChapter) {
        bibleRepository.getVerses(currentBook.id, selectedChapter)
    }

    val highlights by bibleRepository.getAllHighlights().collectAsStateWithLifecycle(initialValue = emptyList())
    val dailyVerse = remember { bibleRepository.getDailyVerse() }

    var activeVerseForAction by remember { mutableStateOf<Verse?>(null) }
    var selectedHighlightColor by remember { mutableStateOf("#FFF176") }
    var verseNoteText by remember { mutableStateOf("") }
    var showHighlightsDialog by remember { mutableStateOf(false) }
    var showVerseDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("bible_screen")
    ) {
        // Book selector scrollable row
        ScrollableTabRow(
            selectedTabIndex = selectedBookIndex,
            edgePadding = 16.dp,
            containerColor = DeepNavy,
            contentColor = Color.White,
            indicator = {},
            divider = {}
        ) {
            books.forEachIndexed { index, book ->
                val isSelected = index == selectedBookIndex
                Tab(
                    selected = isSelected,
                    onClick = {
                        selectedBookIndex = index
                        selectedChapter = if (book.id == "PSA") 23 else 1
                    },
                    modifier = Modifier.testTag("book_tab_${book.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 4.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) HolyGold else Color.White.copy(alpha = 0.12f))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = book.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) DeepNavy else Color.White,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Chapter selector bar & Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepNavy.copy(alpha = 0.05f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapters list
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val availableChapters = if (currentBook.id == "PSA") {
                    listOf(1, 23, 91, 119, 121)
                } else {
                    (1..currentBook.chaptersCount).toList()
                }

                availableChapters.forEach { chapterNum ->
                    val isChapterSelected = chapterNum == selectedChapter
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(if (isChapterSelected) DeepNavy else Color.White)
                            .clickable { selectedChapter = chapterNum }
                            .testTag("chapter_chip_$chapterNum"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$chapterNum",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isChapterSelected) Color.White else DeepNavy,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Highlights list button
            TextButton(
                onClick = { showHighlightsDialog = true },
                modifier = Modifier.testTag("view_highlights_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Highlights",
                    tint = HolyGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Saved (${highlights.size})",
                    style = MaterialTheme.typography.labelMedium,
                    color = DeepNavy,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Bible Reader content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Daily Verse Header Widget
            item {
                DailyVerseCard(
                    dailyVerse = dailyVerse,
                    onShare = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "\"${dailyVerse.text}\"\n— ${dailyVerse.reference}\n(Via Bible Connect App)"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share Daily Scripture"))
                    },
                    onTriggerNotification = {
                        NotificationHelper.showDailyVerseNotification(
                            context = context,
                            verseRef = dailyVerse.reference,
                            verseText = dailyVerse.text
                        )
                    }
                )
            }

            // Chapter Header Title
            item {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "${currentBook.name} $selectedChapter",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "Authorized King James Bible • Tap any verse to highlight, annotate, or share",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Scripture Verses
            items(verses) { verse ->
                val matchingHighlight = highlights.find {
                    it.bookId == verse.bookId && it.chapter == verse.chapter && it.verseNumber == verse.verseNumber
                }

                val highlightColor = matchingHighlight?.let {
                    try {
                        Color(android.graphics.Color.parseColor(it.colorHex)).copy(alpha = 0.45f)
                    } catch (_: Exception) {
                        HolyGold.copy(alpha = 0.3f)
                    }
                } ?: Color.Transparent

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            activeVerseForAction = verse
                            selectedHighlightColor = matchingHighlight?.colorHex ?: "#FFF176"
                            verseNoteText = matchingHighlight?.note ?: ""
                            showVerseDialog = true
                        }
                        .testTag("verse_item_${verse.verseNumber}"),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (matchingHighlight != null) highlightColor else Color.Transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                text = "${verse.verseNumber}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = HolyGold,
                                modifier = Modifier
                                    .width(28.dp)
                                    .padding(top = 2.dp)
                            )
                            Text(
                                text = verse.text,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    lineHeight = 26.sp,
                                    fontSize = 17.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (matchingHighlight != null && matchingHighlight.note.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 28.dp),
                                shape = RoundedCornerShape(6.dp),
                                color = DeepNavy.copy(alpha = 0.08f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FormatQuote,
                                        contentDescription = "Note",
                                        tint = DeepNavy,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = matchingHighlight.note,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = DeepNavy,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Sheet or Dialog for Verse Action
    if (showVerseDialog && activeVerseForAction != null) {
        val verse = activeVerseForAction!!
        val existingHighlight = highlights.find {
            it.bookId == verse.bookId && it.chapter == verse.chapter && it.verseNumber == verse.verseNumber
        }

        AlertDialog(
            onDismissRequest = { showVerseDialog = false },
            title = {
                Text(
                    text = "${verse.bookName} ${verse.chapter}:${verse.verseNumber}",
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "\"${verse.text}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Custom Highlight Color:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HighlightColorPicker(
                        selectedColorHex = selectedHighlightColor,
                        onColorSelected = { selectedHighlightColor = it }
                    )

                    OutlinedTextField(
                        value = verseNoteText,
                        onValueChange = { verseNoteText = it },
                        label = { Text("Personal Reflection or Prayer Note") },
                        placeholder = { Text("Write your thoughts or sermon takeaways...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("verse_note_input"),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bibleRepository.saveHighlight(
                                bookId = verse.bookId,
                                bookName = verse.bookName,
                                chapter = verse.chapter,
                                verseNumber = verse.verseNumber,
                                textSnippet = verse.text,
                                colorHex = selectedHighlightColor,
                                note = verseNoteText
                            )
                        }
                        showVerseDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                    modifier = Modifier.testTag("save_highlight_btn")
                ) {
                    Text("Save Highlight")
                }
            },
            dismissButton = {
                Row {
                    if (existingHighlight != null) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    bibleRepository.removeVerseHighlight(verse.bookId, verse.chapter, verse.verseNumber)
                                }
                                showVerseDialog = false
                            },
                            modifier = Modifier.testTag("remove_highlight_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Highlight",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "\"${verse.text}\" — ${verse.bookName} ${verse.chapter}:${verse.verseNumber}"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Scripture"))
                        },
                        modifier = Modifier.testTag("share_verse_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = DeepNavy)
                    }

                    TextButton(onClick = { showVerseDialog = false }) {
                        Text("Close")
                    }
                }
            }
        )
    }

    // Saved Highlights Dialog
    if (showHighlightsDialog) {
        AlertDialog(
            onDismissRequest = { showHighlightsDialog = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Saved Highlights & Notes", fontWeight = FontWeight.Bold, color = DeepNavy)
                    IconButton(onClick = { showHighlightsDialog = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            },
            text = {
                if (highlights.isEmpty()) {
                    Text(
                        "No saved highlights yet. Tap any verse in the reader to highlight with gold, emerald, sky, or rose colors!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(highlights) { hl ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${hl.bookName} ${hl.chapter}:${hl.verseNumber}",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepNavy
                                        )
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    bibleRepository.removeHighlight(hl.id)
                                                }
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "\"${hl.textSnippet}\"",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                    if (hl.note.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Note: ${hl.note}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = HolyGold,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showHighlightsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy)
                ) {
                    Text("Done")
                }
            }
        )
    }
}
