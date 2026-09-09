package com.example.boomapp.dashboard

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.R
import com.example.boomapp.dialog.LogCycleBottomSheet
import com.example.boomapp.dialog.LogMealsBottomSheet
import com.example.boomapp.dialog.LogMovementBottomSheet
import com.example.boomapp.dialog.LogSymptomsBottomSheet

private val ScreenBg = Color(0xFFFAF7F2)
private val DarkText = Color(0xFF2E2623)
private val MutedText = Color(0xFF918A85)
private val MaroonBrown = Color(0xFF8B4D3E)
private val GreenBtn = Color(0xFF638B75)

enum class GoalDialogType {
    MEALS,
    MOVEMENT,
    WATER,
    RELAXATION,
    SLEEP
}

data class GoalProgressItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val ringColor: Color,
    val iconColor: Color = ringColor,
    val dialogType: GoalDialogType
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BloomDashboard(
    modifier: Modifier = Modifier,
    userName: String = "Verma"
) {

    var activeDialog by remember { mutableStateOf<GoalDialogType?>(null) }
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
    val goalItems = listOf(
        GoalProgressItem(
            title = "Meals",
            subtitle = "0/3 meals",
            icon = ImageVector.vectorResource(id = R.drawable.ic_meal),
            ringColor = Color(0xFFA64D43),
            dialogType = GoalDialogType.MEALS
        ),
        GoalProgressItem(
            title = "Movement",
            subtitle = "0/30 min",
            icon = ImageVector.vectorResource(id = R.drawable.ic_movement),
            ringColor = Color(0xFF5E8C76),
            iconColor = colorResource(R.color.green),
            dialogType = GoalDialogType.MOVEMENT
        ),
        GoalProgressItem(
            title = "Water",
            subtitle = "0/8 glasses",
            icon = ImageVector.vectorResource(id = R.drawable.ic_water),
            ringColor = Color(0xFF4C82A6),
            iconColor = colorResource(R.color.blue),
            dialogType = GoalDialogType.WATER
        ),
        GoalProgressItem(
            title = "Relaxation",
            subtitle = "0/15 min",
            icon = ImageVector.vectorResource(id = R.drawable.ic_relex),
            ringColor = Color(0xFF7A6B9B),
            iconColor = colorResource(R.color.purple),
            dialogType = GoalDialogType.RELAXATION
        ),
        GoalProgressItem(
            title = "Sleep",
            subtitle = "0/8 hrs",
            icon = ImageVector.vectorResource(id = R.drawable.ic_sleep),
            ringColor = Color(0xFF53597D),
            iconColor = colorResource(R.color.darkPurple),
            dialogType = GoalDialogType.SLEEP
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBg),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "GOOD MORNING, ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W700,
                        letterSpacing = 1.sp,
                        color = MutedText
                    )
                    Text(
                        text = userName,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.W600,
                        color = DarkText
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF8DDD9)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "Profile",
                    )
                }
            }
        }

        // Goals
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.White, Color(0xFFFFF7F4))
                            )
                        )
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Today's Goals",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            color = colorResource(R.color.darkBrown)
                        )
                        Text(
                            text = "Saturday, June 27",
                            fontSize = 12.sp,
                            color = MutedText,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "Let's get blooming",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            color = DarkText,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        GoalsProgressSection(0,5)
                    }

                    // Start here
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0x59E8B4AA),
                                        Color(0x26E8B4AA),
//                                        Color.Transparent
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Start here",
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            color = MaroonBrown
                        )
                    }
                }
            }
        }
        item {
            PcosFaqPagerSection(sections = sections )
        }

        // Action Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ActionCard(
                    title = "Cycle",
                    subtitle = "Log your last period date",
                    icon = painterResource(R.drawable.ic_period)
                )
                ActionCard(
                    title = "Symptom check-in",
                    subtitle = "Bloating, skin, mood, sleep quality",
                    icon = painterResource(R.drawable.ic_smile)
                )
            }
        }

        // Progress
        item {
            SectionHeader("Goal Progress")
        }

        // Goal items
        items(goalItems.size) { index ->
            val item = goalItems[index]
            GoalCard(
                item = item,
                onClick = { activeDialog = item.dialogType } // 👈 Triggers specific dialog
            )
        }

        item {
            SectionHeader("Today's tip")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.creamBrown)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(colorResource(R.color.lightBlue)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_sleep),
                                contentDescription = null,
                                tint = colorResource(R.color.darkPurple),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "LOG LAST NIGHT'S SLEEP",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W800,
                                color = colorResource(R.color.brown)
                            )
                            Text(
                                text = "You haven't logged sleep yet. Consistent, close-to-8-hr nights can help keep your hormones more balanced — avoiding phones and screens before bed makes it easier to fall asleep.",
                                fontSize = 12.sp,
                                color = colorResource(R.color.newBlack),
                                lineHeight = 17.sp,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5D5C1)),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text("Remind me later", fontSize = 13.sp, color = DarkText,
                                fontWeight = FontWeight.W700)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenBtn),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_tick),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Mark as done", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
    when (activeDialog) {
        GoalDialogType.MEALS -> {
            LogMealsBottomSheet(onDismissRequest = { activeDialog = null })
        }
        GoalDialogType.MOVEMENT -> {
            LogMovementBottomSheet(
                "Movement",
                onDismissRequest = { activeDialog = null },
                onNavigateToSettings = {
                    activeDialog = null
                }
            )
        }
        GoalDialogType.WATER -> {
            LogMovementBottomSheet(
                "Water",
                onDismissRequest = { activeDialog = null },
                onNavigateToSettings = {
                    activeDialog = null
                }
            )
        }
        GoalDialogType.RELAXATION -> {
            LogMovementBottomSheet(
                "Relaxation",
                onDismissRequest = { activeDialog = null },
                onNavigateToSettings = {
                    activeDialog = null
                }
            )
        }
        GoalDialogType.SLEEP -> {
            LogMovementBottomSheet(
                "Sleep",
                onDismissRequest = { activeDialog = null },
                onNavigateToSettings = {
                    activeDialog = null
                }
            )
        }
        null -> { /* No dialog shown */ }
        else -> {}
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        color = DarkText,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    icon: Painter
) {
    var showCycleSheet by remember { mutableStateOf(false) }
    var showSymptomSheet by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightCream)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().clickable {
            if(title=="Cycle") {
                showCycleSheet = !showCycleSheet
            } else {
                showSymptomSheet = !showSymptomSheet
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = MaroonBrown,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(text = subtitle, fontSize = 12.sp, color = MutedText)
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right),
                contentDescription = null,
                tint = MutedText
            )
        }
    }
    if (showCycleSheet) {
        LogCycleBottomSheet(
            onDismissRequest = {
                showCycleSheet = false
            },
            onDateConfirmed = { date ->
                showCycleSheet = false
            }
        )
    }
    if (showSymptomSheet) {
        LogSymptomsBottomSheet(
            onDismissRequest = { showSymptomSheet = false },
            onDoneClick = { selectedSymptoms ->
            }
        )
    }
}

@Composable
private fun GoalCard(
    item: GoalProgressItem,
    onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(width = 3.dp, color = item.ringColor, shape = CircleShape)
                        .background(item.ringColor.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = item.ringColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(text = item.subtitle, fontSize = 12.sp, color = MutedText)
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right),
                contentDescription = null,
                tint = MutedText
            )
        }
    }
}

@Composable
fun GoalsProgressSection(
    completedCount: Int = 1,
    totalCount: Int = 5
) {
    val progressFraction = if (totalCount > 0) (completedCount.toFloat() / totalCount).coerceIn(0f, 1f) else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        label = "GoalProgressAnimation"
    )

    Column {
        Text(
            text = "$completedCount of $totalCount complete",
            fontSize = 12.sp,
            color = MutedText
        )

        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .width(130.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFF4DDD7))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(2.dp))
                    .background(colorResource(R.color.darkBrown))
            )
        }
    }
}


@Composable
fun PcosFaqPagerSection(
    sections: List<LearnSection>,
    onQuestionClick: (String) -> Unit = {}
) {
    val allQuestions = remember(sections) {
        sections.flatMap { it.questions }
    }

    if (allQuestions.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { allQuestions.size })

    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader("PCOS FAQ")

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 12.dp
        ) { pageIndex ->
            val question = allQuestions[pageIndex]

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                onClick = { onQuestionClick(question) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = question,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            maxLines = 1
                        )
                        Text(
                            text = "PCOS stands for polycystic ovary syndrome...",
                            fontSize = 12.sp,
                            color = MutedText,
                            maxLines = 1,
                            modifier = Modifier
                        )
                    }
                    Icon(
                        painter = painterResource(id=R.drawable.ic_right),
                        contentDescription = null,
                        tint = MutedText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(allQuestions.size) { index ->
                val isSelected = pagerState.currentPage == index

                val width by animateDpAsState(
                    targetValue = if (isSelected) 18.dp else 5.dp,
                    label = "indicatorWidth"
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.5.dp)
                        .height(4.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaroonBrown else Color(0xFFE2D6CF)
                        )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewDashboardContent() {
    BloomDashboard()

}