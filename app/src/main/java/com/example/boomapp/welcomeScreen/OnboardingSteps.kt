package com.example.boomapp.welcomeScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boomapp.OnboardingPreferences
import com.example.boomapp.R
import com.example.boomapp.data.HabitItemData
import com.example.boomapp.dialog.DialExample
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val pages = listOf(
    HabitItemData(
        id = 1,
        iconRes = R.drawable.ic_meal,
        title = "Meals · 3 balanced meals",
        subtitle = "Steadier blood sugar helps keep PCOS symptoms in check.",
        isEnabled = true
    ),
    HabitItemData(
        id = 2,
        iconRes = R.drawable.ic_movement,
        title = "Movement · 30 minutes",
        subtitle = "Supports insulin sensitivity, a key lever in PCOS.",
        isEnabled = true
    ),
    HabitItemData(
        id = 3,
        iconRes = R.drawable.ic_meal,
        title = "Meals · 3 balanced meals",
        subtitle = "Steadier blood sugar helps keep PCOS symptoms in check.",
        isEnabled = true
    ),
    HabitItemData(
        id = 4,
        iconRes = R.drawable.ic_water,
        title = "Water · 8 glasses",
        subtitle = "Helps with energy and bloating.",
        isEnabled = true
    ),
    HabitItemData(
        id = 5,
        iconRes = R.drawable.ic_relex,
        title = "Relaxation · 15 minutes",
        subtitle = "Lower stress means lower cortisol, which can worsen symptoms.",
        isEnabled = true
    ),
    HabitItemData(
        id = 6,
        iconRes = R.drawable.ic_sleep,
        title = "Sleep · 8 hours",
        subtitle = "Consistent sleep helps balance hormones over time.",
        isEnabled = true
    ),
    HabitItemData(
        id = 7,
        iconRes = R.drawable.ic_period,
        title = "Cycle · period dates",
        subtitle = "Spot your personal pattern and predict your next period.",
        isEnabled = true
    ),
)

@Composable
fun OnboardingStepOne() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FlowerLogoWithGlow()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Welcome to Bloom",
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C1810),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "A simple daily companion for managing PCOS — track your cycle, meals, movement, water, relaxation and sleep.",
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xFF7A6E68),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingStepTwo(
    name: String,
    onNameChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "A little about you",
            fontStyle = FontStyle.Italic,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        BasicTextField(
            value = name,
            onValueChange = {
                onNameChange(it)
            },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.lightRed),
                    shape = RoundedCornerShape(14.dp)
                )
                .background(Color.White, shape = RoundedCornerShape(14.dp))
                .padding(horizontal = 24.dp, vertical = 18.dp),
            decorationBox = { innerTextField ->
                if (name.isEmpty()) {
                    Text("Your first name", color = Color.Gray)
                }
                innerTextField()
            })
    }
}

@Composable
fun OnboardingStepThree() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_star), contentDescription = "Star Logo"
            )
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = "What would you like to track daily?",
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp,
                color = colorResource(R.color.newBlack),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Pick the goals that matter to you — only these will show up on your dashboard. Partial progress still counts, and your flower fills in as you go.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.lightBrown),
                textAlign = TextAlign.Center
            )
        }
        item {
            HabitTrackerList(
                items = pages, modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                text = "Targets and what you track can be adjusted any time in Settings.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.lightBrown),
                textAlign = TextAlign.Center
            )
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingStepFour() {
    val primaryBrown = Color(0xFFA65851)
    var morning by remember { mutableStateOf(false) }
    var afterNoon by remember { mutableStateOf(false) }
    var evening by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTimeText by remember {
        val now = Calendar.getInstance()
        mutableStateOf(formatTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)))
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_time), contentDescription = "Star Logo"
            )
        }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = "When should we remind you?",
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp,
                color = colorResource(R.color.newBlack),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "We'll send one daily nudge to log how you're doing.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.lightBrown),
                textAlign = TextAlign.Center
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Pick a time of day",
                    fontSize = 15.sp,
                    color = colorResource(R.color.lightBrown),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = morning,
                            onClick = {
                                morning = !morning
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = primaryBrown,
                                unselectedColor = Color(0xFFC4B5B0)
                            )
                        )
                        Column {
                            Text(
                                text = "Morning",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W700,
                                color = colorResource(R.color.newBlack)
                            )
                            Text(
                                text = "7:30 AM — start the day on track",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W400,
                                color = colorResource(R.color.lightBrown)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
//                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = afterNoon,
                            onClick = {
                                afterNoon = !afterNoon
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = primaryBrown,
                                unselectedColor = Color(0xFFC4B5B0)
                            )
                        )
                        Column {
                            Text(
                                text = "Afternoon",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W700,
                                color = colorResource(R.color.newBlack)
                            )
                            Text(
                                text = "1:00 PM — a midday check-in",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W400,
                                color = colorResource(R.color.lightBrown)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
//                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = evening,
                            onClick = {
                                evening = !evening
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = primaryBrown,
                                unselectedColor = Color(0xFFC4B5B0)
                            )
                        )
                        Column {
                            Text(
                                text = "Evening",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W700,
                                color = colorResource(R.color.newBlack)
                            )
                            Text(
                                text = "8:00 PM — wind down and reflect",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W400,
                                color = colorResource(R.color.lightBrown)
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 18.dp),
                    text = "Or set a custom time",
                    fontSize = 15.sp,
                    color = colorResource(R.color.lightBrown),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .clickable { showBottomSheet = true }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(modifier = Modifier, text = selectedTimeText)
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.ic_time_black), // Replace with your vector/drawable
                            contentDescription = "Bloom Logo",
                            modifier = Modifier.clickable { showBottomSheet = true }
                        )
                        if (showBottomSheet) {
                            DialExample(
                                onConfirm = { hour, minute ->
                                    selectedTimeText = formatTime(hour, minute)
                                    showBottomSheet = false
                                },
                                onDismiss = {
                                    showBottomSheet = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatTime(hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
}