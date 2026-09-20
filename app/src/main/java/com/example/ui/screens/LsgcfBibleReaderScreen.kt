package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.data.model.BibleBook
import com.example.data.model.Verse
import com.example.data.repository.BibleRepository
import com.example.ui.theme.HighlightEmerald
import com.example.ui.theme.HighlightGold
import com.example.ui.theme.HighlightPurple
import com.example.ui.theme.HighlightRose
import com.example.ui.theme.HighlightSky
import com.example.ui.theme.LsgcfBg
import com.example.ui.theme.LsgcfCardBorder
import com.example.ui.theme.LsgcfGold
import com.example.ui.theme.LsgcfTextCategory
import com.example.ui.theme.LsgcfTextMuted
import com.example.ui.theme.LsgcfTextPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LsgcfBibleReaderScreen(
    bibleRepository: BibleRepository,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    val books = bibleRepository.booksList
    // Default to Genesis (index 0) and Chapter 6 to match reference screenshot!
    var selectedBookIndex by remember { mutableIntStateOf(0) }
    val currentBook = books.getOrElse(selectedBookIndex) { books.first() }

    var selectedChapter by remember { mutableIntStateOf(6) }

    // Font size scaling (defaults to 18.sp)
    var fontSizeSp by remember { mutableFloatStateOf(18f) }

    val verses = remember(currentBook.id, selectedChapter) {
        bibleRepository.getVerses(currentBook.id, selectedChapter)
    }

    val highlights by bibleRepository.getAllHighlights().collectAsStateWithLifecycle(initialValue = emptyList())

    // Selector Sheet State
    var showBookChapterPicker by remember { mutableStateOf(false) }

    // Verse Action Dialog State
    var selectedVerseForAction by remember { mutableStateOf<Verse?>(null) }
    var verseNoteInput by remember { mutableStateOf("") }
    var activeHighlightColorHex by remember { mutableStateOf("#FFF176") }

    // Scroll to top when chapter changes
    LaunchedEffect(currentBook.id, selectedChapter) {
        listState.scrollToItem(0)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LsgcfBg)
            .testTag("lsgcf_bible_reader_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top App Bar matching Reference 4
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(
                    onClick = { onBack?.invoke() },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("bible_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LsgcfTextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Dropdown center selector: "Genesis 6 ∨"
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { showBookChapterPicker = true }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("bible_book_chapter_selector"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentBook.name} $selectedChapter",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = LsgcfTextPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select chapter",
                        tint = LsgcfTextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Font size buttons: A- and A+
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            if (fontSizeSp > 14f) fontSizeSp -= 2f
                        },
                        modifier = Modifier.size(36.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "A-",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LsgcfTextMuted
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    TextButton(
                        onClick = {
                            if (fontSizeSp < 26f) fontSizeSp += 2f
                        },
                        modifier = Modifier.size(36.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "A+",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LsgcfTextMuted
                        )
                    }
                }
            }

            // Sub-header notice: "Hold a verse to bookmark"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = null,
                    tint = LsgcfGold,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Hold a verse to bookmark",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = LsgcfGold
                )
            }

            // Scripture Verses List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(verses) { index, verse ->
                    val highlight = highlights.find {
                        it.bookId == verse.bookId && it.chapter == verse.chapter && it.verseNumber == verse.verseNumber
                    }

                    VerseRow(
                        verse = verse,
                        highlightColorHex = highlight?.colorHex,
                        note = highlight?.note,
                        fontSizeSp = fontSizeSp,
                        onVerseClick = {
                            selectedVerseForAction = verse
                            verseNoteInput = highlight?.note ?: ""
                            activeHighlightColorHex = highlight?.colorHex ?: "#FFF176"
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }

            // Sticky Bottom Chapter Navigation Bar matching Reference 4: "< Prev | CH. 6 | Next >"
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bible_chapter_bottom_bar"),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Column {
                    HorizontalDivider(color = LsgcfCardBorder, thickness = 0.8.dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Prev Button
                        val hasPrev = selectedChapter > 1 || selectedBookIndex > 0
                        TextButton(
                            onClick = {
                                if (selectedChapter > 1) {
                                    selectedChapter -= 1
                                } else if (selectedBookIndex > 0) {
                                    selectedBookIndex -= 1
                                    selectedChapter = books[selectedBookIndex].chaptersCount
                                }
                            },
                            enabled = hasPrev
                        ) {
                            Text(
                                text = "<  Prev",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (hasPrev) LsgcfTextPrimary else LsgcfTextMuted.copy(alpha = 0.4f)
                            )
                        }

                        // Center Chapter Label
                        Text(
                            text = "CH. $selectedChapter",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LsgcfTextCategory,
                            letterSpacing = 1.sp
                        )

                        // Next Button
                        val hasNext = selectedChapter < currentBook.chaptersCount || selectedBookIndex < books.size - 1
                        TextButton(
                            onClick = {
                                if (selectedChapter < currentBook.chaptersCount) {
                                    selectedChapter += 1
                                } else if (selectedBookIndex < books.size - 1) {
                                    selectedBookIndex += 1
                                    selectedChapter = 1
                                }
                            },
                            enabled = hasNext
                        ) {
                            Text(
                                text = "Next  >",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (hasNext) LsgcfTextPrimary else LsgcfTextMuted.copy(alpha = 0.4f)
                            )
                        }
                    }
                }
            }
        }

        // Book & Chapter Picker Bottom Sheet
        if (showBookChapterPicker) {
            ModalBottomSheet(
                onDismissRequest = { showBookChapterPicker = false },
                containerColor = Color.White,
                sheetState = rememberModalBottomSheetState()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .testTag("book_chapter_picker_sheet")
                ) {
                    Text(
                        text = "Select Scripture",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = LsgcfTextPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "BOOK",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        itemsIndexed(books) { index, book ->
                            val isSelected = index == selectedBookIndex
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) LsgcfGold.copy(alpha = 0.15f) else Color.Transparent)
                                    .clickable {
                                        selectedBookIndex = index
                                        selectedChapter = 1
                                    }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = book.name,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) LsgcfGold else LsgcfTextPrimary
                                )
                                Text(
                                    text = book.testament,
                                    fontSize = 12.sp,
                                    color = LsgcfTextMuted
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "CHAPTER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LsgcfTextCategory,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (ch in 1..currentBook.chaptersCount) {
                            val isCurrent = ch == selectedChapter
                            val borderColor = if (isCurrent) LsgcfGold else LsgcfCardBorder
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isCurrent) LsgcfGold else LsgcfBg)
                                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
                                    .clickable {
                                        selectedChapter = ch
                                        showBookChapterPicker = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$ch",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color.White else LsgcfTextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Verse Action Dialog (Highlight, Bookmark, Share, Add Note)
        selectedVerseForAction?.let { verse ->
            AlertDialog(
                onDismissRequest = { selectedVerseForAction = null },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${currentBook.name} ${verse.chapter}:${verse.verseNumber}",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = LsgcfTextPrimary
                        )
                        IconButton(onClick = { selectedVerseForAction = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = LsgcfTextPrimary)
                        }
                    }
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "\"${verse.text}\"",
                            fontFamily = FontFamily.Serif,
                            fontSize = 14.sp,
                            color = LsgcfTextPrimary,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "HIGHLIGHT COLOR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LsgcfTextCategory,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val colors = listOf(
                            "#FFF176" to HighlightGold,
                            "#A5D6A7" to HighlightEmerald,
                            "#90CAF9" to HighlightSky,
                            "#F48FB1" to HighlightRose,
                            "#CE93D8" to HighlightPurple
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            colors.forEach { (hex, col) ->
                                val isSelected = activeHighlightColorHex == hex
                                val circleBorder = if (isSelected) LsgcfTextPrimary else Color.Transparent
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(col)
                                        .border(width = 2.dp, color = circleBorder, shape = CircleShape)
                                        .clickable { activeHighlightColorHex = hex },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Black,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = verseNoteInput,
                            onValueChange = { verseNoteInput = it },
                            label = { Text("Study Note / Reflection", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 3
                        )
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                val shareText = "${currentBook.name} ${verse.chapter}:${verse.verseNumber} - \"${verse.text}\" (LSGCF Bible)"
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Scripture"))
                            }
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = LsgcfTextPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share", color = LsgcfTextPrimary)
                        }

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    bibleRepository.saveHighlight(
                                        bookId = verse.bookId,
                                        bookName = currentBook.name,
                                        chapter = verse.chapter,
                                        verseNumber = verse.verseNumber,
                                        textSnippet = verse.text,
                                        colorHex = activeHighlightColorHex,
                                        note = verseNoteInput
                                    )
                                    selectedVerseForAction = null
                                    snackbarHostState.showSnackbar("Verse saved with highlight!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = LsgcfGold)
                        ) {
                            Text("Save Highlight", color = Color.White)
                        }
                    }
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        )
    }
}

@Composable
private fun VerseRow(
    verse: Verse,
    highlightColorHex: String?,
    note: String?,
    fontSizeSp: Float,
    onVerseClick: () -> Unit
) {
    val highlightBg = remember(highlightColorHex) {
        if (highlightColorHex != null) {
            try {
                Color(android.graphics.Color.parseColor(highlightColorHex)).copy(alpha = 0.35f)
            } catch (e: Exception) {
                Color.Transparent
            }
        } else {
            Color.Transparent
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(highlightBg)
            .clickable { onVerseClick() }
            .padding(horizontal = 4.dp, vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Verse Number in gold
        Text(
            text = "${verse.verseNumber}",
            fontSize = (fontSizeSp * 0.75f).sp,
            fontWeight = FontWeight.Bold,
            color = LsgcfGold,
            modifier = Modifier
                .width(28.dp)
                .padding(top = 2.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = verse.text,
                fontFamily = FontFamily.Serif,
                fontSize = fontSizeSp.sp,
                color = LsgcfTextPrimary,
                lineHeight = (fontSizeSp * 1.55f).sp
            )

            if (!note.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(LsgcfCardBorder.copy(alpha = 0.4f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = LsgcfGold,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = note,
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = LsgcfTextMuted
                    )
                }
            }
        }
    }
}
