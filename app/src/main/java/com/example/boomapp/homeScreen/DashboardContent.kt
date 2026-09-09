//package com.example.boomapp.homeScreen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.boomapp.R
//
//
//@Composable
//fun BloomDashboardContent(
//    modifier: Modifier = Modifier,
//    userName: String = "Sofia"
//) {
//    val goalItems = listOf(
//        GoalItemData("Meals", "0/3 meals", R.drawable.ic_home, Color(0xFFA64D43)),
//        GoalItemData("Movement", "0/30 min", R.drawable.ic_home, Color(0xFF5E8C76)),
//        GoalItemData("Water", "0/8 glasses", R.drawable.ic_home, Color(0xFF4C82A6)),
//        GoalItemData("Relaxation", "0/15 min", R.drawable.ic_home, Color(0xFF7A6B9B)),
//        GoalItemData("Sleep", "0/8 hrs", R.drawable.ic_home, Color(0xFF53597D))
//    )
//
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .background(ScreenBackground),
//        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
//        verticalArrangement = Arrangement.spacedBy(14.dp)
//    ) {
//        item { Row(
//            modifier = Modifier // 👈 Fresh Modifier (capital M)
//                .fillMaxWidth()
//                .statusBarsPadding()
//                .padding(horizontal = 24.dp, vertical = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Good morning, ",
//                    fontWeight = FontWeight.W700,
//                    fontSize = 22.sp,
//                    color = colorResource(R.color.lightBrown)
//                )
//                Text(
//                    text = "Pankaj",
//                    fontWeight = FontWeight.W600,
//                    fontSize = 20.sp,
//                    fontStyle = FontStyle.Italic,
//                    color = colorResource(R.color.newBlack)
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f)) // 👈 Fixed: Modifier.weight(1f)
//
//            Box(
//                modifier = Modifier
//                    .size(44.dp)
//                    .background(color = colorResource(R.color.lightRed), shape = CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_person),
//                    contentDescription = "Profile",
//                    tint = Color.Unspecified,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        } }
//
//        // --- 2. Today's Goals Banner Card ---
//        item {
//            Card(
//                shape = RoundedCornerShape(20.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .background(
//                            Brush.horizontalGradient(
//                                colors = listOf(Color.White, Color(0xFFFFF7F4))
//                            )
//                        )
//                        .padding(20.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column {
//                        Text(
//                            text = "Today's Goals",
//                            fontSize = 20.sp,
//                            fontFamily = FontFamily.Serif,
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic,
//                            color = BrownPrimary
//                        )
//                        Text(
//                            text = "Saturday, June 27",
//                            fontSize = 12.sp,
//                            color = TextMuted,
//                            modifier = Modifier.padding(top = 2.dp)
//                        )
//                        Text(
//                            text = "Let's get blooming",
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.Bold,
//                            fontStyle = FontStyle.Italic,
//                            color = TextDark,
//                            modifier = Modifier.padding(top = 10.dp)
//                        )
//                        Text(
//                            text = "0 of 5 complete",
//                            fontSize = 12.sp,
//                            color = TextMuted
//                        )
//                        Box(
//                            modifier = Modifier
//                                .padding(top = 6.dp)
//                                .width(120.dp)
//                                .height(3.dp)
//                                .clip(RoundedCornerShape(2.dp))
//                                .background(Color(0xFFF1D9D3))
//                        )
//                    }
//
//                    // Flower "Start here" Graphic placeholder
//                    Box(
//                        modifier = Modifier
//                            .size(76.dp)
//                            .clip(CircleShape)
//                            .background(Color(0xFFFBF1EE)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Start here",
//                            fontSize = 13.sp,
//                            fontStyle = FontStyle.Italic,
//                            fontFamily = FontFamily.Serif,
//                            color = BrownPrimary
//                        )
//                    }
//                }
//            }
//        }
//
//        // --- 3. PCOS FAQ Section ---
//        item {
//            SectionTitle("PCOS FAQ")
//            Card(
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 14.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column(modifier = Modifier.weight(1f)) {
//                        Text(
//                            text = "What exactly is PCOS?",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = TextDark
//                        )
//                        Text(
//                            text = "PCOS stands for polycystic ovary syndrome...",
//                            fontSize = 12.sp,
//                            color = TextMuted,
//                            maxLines = 1
//                        )
//                    }
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                        contentDescription = null,
//                        tint = TextMuted
//                    )
//                }
//            }
//
//            // Pager Dots Indicator
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(width = 16.dp, height = 4.dp)
//                        .clip(RoundedCornerShape(2.dp))
//                        .background(BrownPrimary)
//                )
//                repeat(8) {
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Box(
//                        modifier = Modifier
//                            .size(4.dp)
//                            .clip(CircleShape)
//                            .background(Color(0xFFE2D6CF))
//                    )
//                }
//            }
//        }
//
//        // --- 4. Cycle & Check-in Cards ---
//        item {
//            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                ActionCard(
//                    title = "Cycle",
//                    subtitle = "Log your last period date",
//                    badgeColor = Color(0xFFF9EAE7),
//                    iconRes = R.drawable.ic_home,
//                    iconTint = BrownPrimary
//                )
//                ActionCard(
//                    title = "Symptom check-in",
//                    subtitle = "Bloating, skin, mood, sleep quality",
//                    badgeColor = Color(0xFFF9EAE7),
//                    iconRes = R.drawable.ic_home,
//                    iconTint = BrownPrimary
//                )
//            }
//        }
//
//        // --- 5. Goal Progress Section ---
//        item {
//            SectionTitle("Goal Progress")
//        }
//
//        items(goalItems.size) { index ->
//            val item = goalItems[index]
//            GoalProgressCard(item = item)
//        }
//
//        // --- 6. Today's Tip Card ---
//        item {
//            SectionTitle("Today's tip")
//            Card(
//                shape = RoundedCornerShape(20.dp),
//                colors = CardDefaults.cardColors(containerColor = TipCardBg),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(modifier = Modifier.padding(18.dp)) {
//                    Row(verticalAlignment = Alignment.Top) {
//                        Box(
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clip(RoundedCornerShape(10.dp))
//                                .background(Color(0xFFDFD7E6)),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_home),
//                                contentDescription = null,
//                                tint = Color(0xFF53597D),
//                                modifier = Modifier.size(20.dp)
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Column {
//                            Text(
//                                text = "LOG LAST NIGHT'S SLEEP",
//                                fontSize = 11.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = BrownPrimary
//                            )
//                            Text(
//                                text = "You haven't logged sleep yet. Consistent, close-to-8-hr nights can help keep your hormones more balanced — avoiding phones and screens before bed makes it easier to fall asleep.",
//                                fontSize = 12.sp,
//                                color = TextDark,
//                                lineHeight = 16.sp,
//                                modifier = Modifier.padding(top = 4.dp)
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(14.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Button(
//                            onClick = { /* Handle */ },
//                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5D5C1)),
//                            shape = RoundedCornerShape(20.dp),
//                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
//                        ) {
//                            Text("Remind me later", fontSize = 11.sp, color = TextDark)
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Button(
//                            onClick = { /* Handle */ },
//                            colors = ButtonDefaults.buttonColors(containerColor = GreenAccent),
//                            shape = RoundedCornerShape(20.dp),
//                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
//                        ) {
//                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text("Mark as done", fontSize = 11.sp, color = Color.White)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// Section Header Sub-composable
//@Composable
//fun SectionTitle(title: String) {
//    Text(
//        text = title,
//        fontSize = 17.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Serif,
//        fontStyle = FontStyle.Italic,
//        color = TextDark,
//        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
//    )
//}
//
//// Light Pink Card Composable
//@Composable
//fun ActionCard(
//    title: String,
//    subtitle: String,
//    badgeColor: Color,
//    iconRes: Int,
//    iconTint: Color
//) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = CardPinkBg),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 14.dp, vertical = 12.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Box(
//                    modifier = Modifier
//                        .size(42.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(Color.White),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        painter = painterResource(id = iconRes),
//                        contentDescription = null,
//                        tint = iconTint,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.width(12.dp))
//                Column {
//                    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
//                    Text(text = subtitle, fontSize = 12.sp, color = TextMuted)
//                }
//            }
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                contentDescription = null,
//                tint = TextMuted
//            )
//        }
//    }
//}
//
//// White Goal Progress Card with Circular Indicator Ring
//@Composable
//fun GoalProgressCard(item: GoalItemData) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 14.dp, vertical = 10.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                // Circle with border accent
//                Box(
//                    modifier = Modifier
//                        .size(44.dp)
//                        .clip(CircleShape)
//                        .background(item.accentColor.copy(alpha = 0.12f)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        painter = painterResource(id = item.iconRes),
//                        contentDescription = null,
//                        tint = item.accentColor,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.width(14.dp))
//                Column {
//                    Text(text = item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
//                    Text(text = item.subtitle, fontSize = 12.sp, color = TextMuted)
//                }
//            }
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                contentDescription = null,
//                tint = TextMuted
//            )
//        }
//    }
//}