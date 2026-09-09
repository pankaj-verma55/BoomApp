package com.example.boomapp.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R

// Color constants
private val ScreenBackground = Color(0xFFFAF7F2)
private val TextDark = Color(0xFF2E2623)
private val TextMuted = Color(0xFF918A85)

data class LearnSection(
    val title: String,
    val questions: List<String>
)

@Composable
fun LearnScreen(
    modifier: Modifier = Modifier,
    onQuestionClick: (String) -> Unit = {}
) {
    val sections = listOf(
        LearnSection(
            title = "Diagnosis & basics",
            questions = listOf(
                "What exactly is PCOS?",
                "What does PCOS stand for — and do I actually have cysts on my ovaries?",
                "What causes PCOS?",
                "How is PCOS actually diagnosed?",
                "Is PCOS the same for everyone?"
            )
        ),
        LearnSection(
            title = "Symptoms & body",
            questions = listOf(
                "Why are my periods irregular?",
                "Why does weight feel harder to manage?",
                "What is the deal with insulin resistance?",
                "Is the acne and hair growth from PCOS treatable?"
            )
        ),
        LearnSection(
            title = "Managing it day to day",
            questions = listOf(
                "Can food really make a difference?",
                "What foods should I eat — and which should I limit?",
                "How can I manage insulin resistance day to day?",
                "Will I need medication forever?",
                "How does stress fit into all this?"
            )
        ),
        LearnSection(
            title = "Looking ahead",
            questions = listOf(
                "Does PCOS affect fertility?",
                "What happens if PCOS is left unmanaged?"
            )
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // --- 1. Header ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "Learn",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = TextDark
                )
                Text(
                    text = "Understand PCOS, at your own pace",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // --- 2. Sections & Questions ---
        sections.forEach { section ->
            item {
                Text(
                    text = section.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    color = TextDark,
                    modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                )
            }

            items(section.questions) { question ->
                LearnQuestionCard(
                    question = question,
                    onClick = { onQuestionClick(question) }
                )
            }
        }
    }
}

@Composable
private fun LearnQuestionCard(
    question: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = question,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark,
                lineHeight = 17.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_right),
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}