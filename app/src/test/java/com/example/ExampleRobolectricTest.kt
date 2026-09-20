package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.LsgcfRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches LSGCF branding`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("LSGCF", appName)
    }

    @Test
    fun `lsgcf repository provides sermon videos and lessons`() {
        val repo = LsgcfRepository()
        val sermons = repo.sermonVideos
        assertTrue(sermons.isNotEmpty())
        assertEquals("GENESIS 6:1-4", sermons.first().scriptureReference)

        val lessons = repo.discipleshipLessons
        assertEquals(12, lessons.size)

        val statementOfFaith = repo.statementOfFaithSections
        assertTrue(statementOfFaith.isNotEmpty())

        val quizzes = repo.bibleQuizzes
        assertTrue(quizzes.isNotEmpty())
    }
}
