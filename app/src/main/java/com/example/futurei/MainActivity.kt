package com.example.futurei

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DarkTheme {
                AppScreen()
            }
        }
    }
}

sealed class Screen {
    object Story : Screen()
    object Profile : Screen()
    object Settings : Screen()
    object Menu : Screen()
    object Challenge : Screen()
    object Rating : Screen()
    object Compeptitives: Screen()
    object Deposit: Screen()
    object Home: Screen()
    object Food: Screen()
    object Training: Screen()
}

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = colorResource(id = R.color.background),
            onBackground = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background)) // общий фон
        ) {
            content()
        }
    }
}
@Composable
fun AppScreen() {

    val storySteps = listOf(
        StoryStep(
            image = R.drawable.s1c1,
            main_text = "The warrior stepped into the cold stone hall. The slabs screeched beneath his feet, " +
                    "and iron grates fell before him.\nThe only way out was forward.\n",
            exercice_text = "10 squats\n15 seconds plank"
        ),
        StoryStep(
            image = R.drawable.s1c2,
            main_text =  "Torches flared along the walls. Arrows shot out from openings, whistling over his head. " +
                    "The warrior pressed himself to the floor, rolled, and managed to slip through.",
            exercice_text = "5 push-ups\n10 jumping jacks"
        ),
        StoryStep(
            image = R.drawable.s1c3,
            main_text =  "The dark door slammed shut behind him. In the room, heavy bladed pendulums swung, " +
                    "leaving deep grooves in the stone. He had to pass between them.\n",
            exercice_text = "20 seconds running in place\n8 rolls"
        ),
        StoryStep(
            image = R.drawable.s1c4,
            main_text = "He reached a round hall. In the center stood a stone pedestal with a key, but the floor " +
                    "began to crumble right under his feet.\n",
            exercice_text = "12 pistol squats\n20 crunches"
        )
    )


    var stepIndex by remember { mutableStateOf(0) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomNavBar(
                currentScreen = currentScreen,
                onScreenChange = { currentScreen = it },
                stepIndex = stepIndex,
                onStepChange = { stepIndex = it }
            )
        },
        containerColor = colorResource(id = R.color.background)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
        ) {
            when (currentScreen) {
                Screen.Story -> Content(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    storyStep = storySteps[stepIndex]
                )
                Screen.Settings -> SettingsScreen()
                Screen.Profile -> ProfileScreen()
                Screen.Menu -> MenuScreen(currentScreen = currentScreen,
                    onScreenChange = { currentScreen = it })
                Screen.Challenge -> ChallengeScreen()
                Screen.Deposit -> DepositScreen()
                Screen.Rating -> RatingScreen()
                Screen.Compeptitives -> CompeptitivesScreen()
                Screen.Home -> HomeScreen()
                Screen.Food -> FoodScreen()
                Screen.Training -> TrainingScreen()
            }
        }
    }
}

@Composable
fun TrainingScreen() {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1))
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Workout Progress", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("14 exercises left", fontSize = 15.sp)
                }
                Box(modifier = Modifier.padding(start = 15.dp)){
                    CircularProgressBar(
                        max = 100f,
                        progress = 75f,
                        modifier = Modifier.size(75.dp),
                        mainText = "75",
                        secondText = "%"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(220.dp)
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.main_color_1))
            ) {
                Column {
                    Row(modifier = Modifier.padding(16.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Calories", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                        Image(
                            painter = painterResource(id = R.drawable.fire),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(25.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                        CircularProgressBar(max = 1000f, progress = 730f, modifier = Modifier.size(100.dp), mainText = "730", secondText = "kCal")
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(220.dp)
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(16.dp)) // сначала обрезаем
                    .background(colorResource(id = R.color.main_color_1)) // фон теперь закруглённый
            ) {
                Column {
                    Row(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pulse", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                        Image(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(25.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth()){
                        Image(
                            painter = painterResource(id = R.drawable.pulse),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .width(170.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Box(modifier = Modifier.padding(start = 16.dp)) {
                        Text("95 bpm", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Programs", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(modifier = Modifier
                .width(80.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.run),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(75.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Box(modifier = Modifier
                .width(80.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.yoga),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(75.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Box(modifier = Modifier
                .width(80.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.bicucle),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(75.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Box(modifier = Modifier
                .width(80.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.weight),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(75.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.Center)
        {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Описание",
                modifier = Modifier
                    .height(20.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
fun FoodScreen() {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(modifier = Modifier
            .background(colorResource(id = R.color.main_color_1))
            .height(1.dp)
            .fillMaxWidth()
        )
        Box(modifier = Modifier.padding(vertical = 20.dp))
        {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(25.dp),
                    contentScale = ContentScale.Crop,
                )
                Box(modifier = Modifier.padding(horizontal = 115.dp)) {
                    Text("Сегодня", fontWeight = FontWeight.Bold)
                }
                Image(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(25.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.CenterEnd
                )
            }
        }
        Box(modifier = Modifier
            .background(colorResource(id = R.color.main_color_1))
            .height(1.dp)
            .fillMaxWidth()
        )

        Box(){
            Column {
                Row {
                    Text("Remaining items:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Row {
                    Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("41", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                        Text("Protein (P)")
                    }
                    Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("41", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                        Text("Fats (F)")
                    }
                    Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("41", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                        Text("Carbs (C)")
                    }
                    Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("41", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                        Text("Cal. (Kcal)")
                    }
                }

            }
        }

        Box(modifier = Modifier
            .background(colorResource(id = R.color.main_color_1))
            .height(1.dp)
            .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(colorResource(id = R.color.main_color_1))
            .padding(15.dp)
        ){
            Column {
                // Breakfast
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.main_color_1))
                    .padding(15.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Breakfast", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.main_color_2))
                            .height(1.dp)
                            .fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Add food", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Row(modifier = Modifier.offset(y = 15.dp)) {
                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Lunch
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.main_color_1))
                    .padding(15.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Lunch", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.main_color_2))
                            .height(1.dp)
                            .fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Add food", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Row(modifier = Modifier.offset(y = 15.dp)) {
                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Dinner
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.main_color_1))
                    .padding(15.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Dinner", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.main_color_2))
                            .height(1.dp)
                            .fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Add food", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Row(modifier = Modifier.offset(y = 15.dp)) {
                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Snack 1
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.main_color_1))
                    .padding(15.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Snack", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.main_color_2))
                            .height(1.dp)
                            .fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Add food", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Row(modifier = Modifier.offset(y = 15.dp)) {
                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Snack 2
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.main_color_1))
                    .padding(15.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Snack", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(modifier = Modifier
                            .background(colorResource(id = R.color.main_color_2))
                            .height(1.dp)
                            .fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Add food", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Row(modifier = Modifier.offset(y = 15.dp)) {
                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))

                                Box(modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .background(color = Color.Gray))
                            }
                        }
                    }
                }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.main_color_1))
                        .padding(15.dp)
                    ){
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Water", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }

                            Box(modifier = Modifier
                                .background(colorResource(id = R.color.main_color_2))
                                .height(1.dp)
                                .fillMaxWidth()
                            )

                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Add water", color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                                Row(modifier = Modifier.offset(y = 15.dp)) {
                                    Box(modifier = Modifier
                                        .width(5.dp)
                                        .height(5.dp)
                                        .background(color = Color.Gray))

                                    Box(modifier = Modifier
                                        .padding(horizontal = 2.dp)
                                        .width(5.dp)
                                        .height(5.dp)
                                        .background(color = Color.Gray))

                                    Box(modifier = Modifier
                                        .width(5.dp)
                                        .height(5.dp)
                                        .background(color = Color.Gray))
                                }
                            }



                        }
                    }
                }
            }
    }
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.height(20.dp))

        Box(contentAlignment = Alignment.CenterStart){
            Text("Today", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_2))
            .padding(16.dp)
            .fillMaxWidth()){
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("1835 kcal", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Consumed", fontSize = 14.sp)
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressBar(
                            progress = 1835f,
                            max = 2500f,
                            modifier = Modifier.size(100.dp),
                            mainText = "1835",
                            secondText = "/kCal"
                        )
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("2500 kcal", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Goal", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("P - 79/120g", fontSize = 15.sp)
                        LinearProgressBar(
                            progress = 79f/120f,
                            background = Color.Black,
                            width = 75.dp,
                            height = 10.dp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("C - 210/320g", fontSize = 15.sp)
                        LinearProgressBar(
                            progress = 210f/320f,
                            background = Color.Black,
                            width = 75.dp,
                            height = 10.dp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("G - 54/70g", fontSize = 15.sp)
                        LinearProgressBar(
                            progress = 54f/70f,
                            background = Color.Black,
                            width = 75.dp,
                            height = 10.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(){
            Column() {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Box(modifier = Modifier.padding(end = 10.dp)){
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.main_color_2))
                            .padding(16.dp)
                            .width(130.dp)
                            .height(75.dp)){
                            Row() {
                                Column {
                                    Text("Sleep", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text("7/8h")
                                }
                                Box(modifier = Modifier.offset(x = 7.dp))
                                {
                                    CircularProgressBar(
                                        progress = 7f,
                                        max = 8f,
                                        modifier = Modifier.size(75.dp))
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(start = 10.dp)){
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.main_color_2))
                            .padding(16.dp)
                            .width(130.dp)
                            .height(75.dp)){
                            Row() {
                                Column {
                                    Text("Water", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text("2/3l", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                }
                                Box(modifier = Modifier.offset(x = 5.dp))
                                {
                                    CircularProgressBar(
                                        progress = 2f,
                                        max = 3f,
                                        modifier = Modifier.size(75.dp))
                                }
                            }
                        }
                    }
                }
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Box(modifier = Modifier.padding(end = 10.dp)){
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.main_color_2))
                            .padding(16.dp)
                            .width(130.dp)
                            .height(75.dp)){
                            Column(modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.footstep),
                                    contentDescription = "Описание",
                                    modifier = Modifier
                                        .height(50.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Text("9320", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(start = 10.dp)){
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.main_color_2))
                            .padding(16.dp)
                            .width(130.dp)
                            .height(75.dp),
                            contentAlignment = Alignment.Center){
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Описание",
                                modifier = Modifier
                                    .height(75.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(Modifier.height(12.dp))

    }
}

@Composable
fun CompeptitivesScreen() {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            painter = painterResource(id = R.drawable.vs),
            contentDescription = "Описание",
            modifier = Modifier
                .height(175.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Row(modifier = Modifier.fillMaxWidth().offset(y = -80.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column()
            {
                Box(modifier = Modifier.offset(x = 10.dp)){
                    LevelProgressBar(
                        progress = 0.8f, // 80%
                        level = 52,
                        trophies = 2095,
                        background = Color.White,
                        barHeight = 30.dp,
                        barLength = 80.dp)
                }
                Image(
                    painter = painterResource(id = R.drawable.black_sam),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(300.dp)
                        .offset(y=-35.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            Column()
            {
                Box(modifier = Modifier.offset(x = 25.dp)){
                    LevelProgressBar(
                        progress = 0.8f, // 80%
                        level = 52,
                        trophies = 2095,
                        background = Color.White,
                        barHeight = 30.dp,
                        barLength = 80.dp)
                }
                Image(
                    painter = painterResource(id = R.drawable.white_sam),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(300.dp)
                        .offset(y=-35.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Column(modifier = Modifier
            .offset(y = -130.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.main_color_1))) {

            Column(modifier = Modifier.fillMaxHeight())
            {
                Box(modifier = Modifier.padding(10.dp)) {
                    Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(35.dp)
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.run),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(35.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(35.dp)
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.yoga),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(35.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(35.dp)
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bicucle),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(35.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(35.dp)
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Описание",
                            modifier = Modifier
                                .height(35.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                }
            }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .offset(y = -130.dp)
            .fillMaxWidth()
            .height(65.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.Center){
            Text("Play Match!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun RatingScreen() {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        //пользователь 1
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                ){
                    Image(
                        painter = painterResource(id = R.drawable.cup1),
                        contentDescription = "Описание",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(75.dp)
                    )
                }
            }
        }

        //пользователь 2
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                ){
                    Image(
                        painter = painterResource(id = R.drawable.cup2),
                        contentDescription = "Описание",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(75.dp)
                            .padding(end = 6.dp)
                    )
                }
            }
        }

        //пользователь 3
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                ){
                    Image(
                        painter = painterResource(id = R.drawable.cup3),
                        contentDescription = "Описание",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(75.dp)
                            .padding(end = 6.dp)
                    )
                }
            }
        }

        //пользователь 4
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ){
                        Text("4", fontSize = 25.sp)
                    }
                }
            }
        }

        //пользователь 5
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ){
                        Text("5", fontSize = 25.sp)
                    }
                }
            }
        }

        //пользователь 6
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ){
                        Text("6", fontSize = 25.sp)
                    }
                }
            }
        }

        //пользователь 7
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1)),
        ){
            Row(modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .background(color = colorResource(id = R.color.main_color_1))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                    Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
                }
                Box(modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.Center){
                    Text( "1995", fontSize = 25.sp  )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(id = R.color.main_color_2)),
                        contentAlignment = Alignment.Center
                    ){
                        Text("7", fontSize = 25.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DepositScreen() {
    Column(modifier = Modifier
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_1))) {
            Row(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("Balance: 1118", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Image(
                    painter = painterResource(id = R.drawable.menu5),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(50.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            Text(
                "Deposit $50 -> get +10% free!",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Box(modifier = Modifier.padding(top = 20.dp)) {
            Text(
                "Quick Deposit:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(modifier = Modifier
                .width(110.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                    Text("[+10]", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier
                .width(110.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                    Text("[+50]", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier
                .width(110.dp)
                .height(75.dp)
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center) {
                    Text("[+100]", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_1))
            .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.TopStart) {
            Box(modifier = Modifier.padding(6.dp)){
                Text("Recent Deposits:\n$50 via Card\n$10 via Card", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.CenterStart) {
            Box(modifier = Modifier.padding(6.dp)) {
                Text("You Won: 128", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.CenterStart) {
            Box(modifier = Modifier.padding(6.dp)){
                Text("You Lost: 10", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.CenterStart) {
            Box(modifier = Modifier.padding(6.dp)) {
                Text("Wins: 50%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ChallengeScreen() {
    Column(modifier = Modifier
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1))
        ) {
            // Текст
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Text(
                    "Saitama Classic\nChallenge",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "30 day",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Картинка поверх текста, сдвинута вправо
            Image(
                painter = painterResource(id = R.drawable.challenge1),
                contentDescription = "Описание",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.CenterEnd) // выравниваем по правому краю Box
            )
        }
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1))
        ) {
            // Текст
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Text(
                    "Kempachi Zaraki\nChallenge",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "21 day",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Картинка поверх текста, сдвинута вправо
            Image(
                painter = painterResource(id = R.drawable.challenge2),
                contentDescription = "Описание",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.CenterEnd) // выравниваем по правому краю Box
            )
        }

        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1))
        ) {
            // Текст
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Text(
                    "Devil Core\nChallenge",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "31 day",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Картинка поверх текста, сдвинута вправо
            Image(
                painter = painterResource(id = R.drawable.challenge3),
                contentDescription = "Описание",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.CenterEnd) // выравниваем по правому краю Box
            )
        }
    }
}

@Composable
fun MenuScreen(    onScreenChange: (Screen) -> Unit,
                   currentScreen: Screen) {
    Column(modifier = Modifier
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1))
            .clickable { onScreenChange(Screen.Rating) },)
        {
            Image(
                painter = painterResource(id = R.drawable.main_menu1),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1))
            .clickable { onScreenChange(Screen.Compeptitives) },)
        {
            Image(
                painter = painterResource(id = R.drawable.main_menu2),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1))
            .clickable { onScreenChange(Screen.Story) },)
        {
            Image(
                painter = painterResource(id = R.drawable.main_menu3),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1))
            .clickable { onScreenChange(Screen.Challenge) },)
        {
            Image(
                painter = painterResource(id = R.drawable.main_menu4),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.main_color_1))
            .clickable { onScreenChange(Screen.Deposit) },)
        {
            Image(
                painter = painterResource(id = R.drawable.main_menu5),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Box(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.s1c1),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

data class StoryStep( val image: Int, val main_text: String, val exercice_text: String,)
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.main_color_1))
            .padding(WindowInsets.statusBars.asPaddingValues()) // отступ от системных иконок
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row{
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(90.dp))
                    .background(color = colorResource(id = R.color.main_color_1)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .height(50.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column( modifier = Modifier.padding(15.dp, 0.dp)) {
                Text("KLLADLEN", color = Color.White, fontWeight = FontWeight.Bold)
                Text("ID: 525252", color = Color.Gray, fontSize = 12.sp)
            }
        }


        LevelProgressBar(
            progress = 0.3f, // 30%
            level = 52,
            trophies = 1995,
            background = Color.White

        )

        Icon(
            painter = painterResource(id = R.drawable.menu8),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, storyStep: StoryStep) {


    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Story 1: Palace of Trials", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = storyStep.image),
                contentDescription = "Описание",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = storyStep.main_text,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.main_color_1)),
            contentAlignment = Alignment.TopStart
        ){
            Text(
                text = storyStep.exercice_text,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
fun BottomNavBar(
    stepIndex: Int,
    onStepChange: (Int) -> Unit,
    onScreenChange: (Screen) -> Unit,
    currentScreen: Screen
) {
    Box(
        modifier = Modifier
            .padding(10.dp, 20.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.main_color_1),
                    shape = RoundedCornerShape(16.dp) // clip только для фона
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        if (stepIndex < 3) {
                            onStepChange(stepIndex + 1)
                        } else {
                            onStepChange(0)
                        }
                    }
                    .background(
                        if (currentScreen is Screen.Home) Color.Red else colorResource(id = R.color.main_color_1),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu1),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)

                        .clickable { onScreenChange(Screen.Home) },
                    contentScale = ContentScale.Fit
                )
            }

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        if (stepIndex < 3) {
                            onStepChange(stepIndex + 1)
                        } else {
                            onStepChange(0)
                        }
                    }
                    .background(
                        if (currentScreen is Screen.Food) Color.Red else colorResource(
                            id = R.color.main_color_1
                        ), shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu2),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)

                        .clickable { onScreenChange(Screen.Food) },
                    contentScale = ContentScale.Fit
                )
            }

            Box(modifier = Modifier.offset(y = -20.dp)){
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Red, CircleShape)
                        .clickable {
                            if (stepIndex < 3) {
                                onStepChange(stepIndex + 1)
                            } else {
                                onStepChange(0)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter =(if(currentScreen == Screen.Story) painterResource(id = R.drawable.menu4) else painterResource(id = R.drawable.menu5)),
                        contentDescription = "Описание",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }


            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        if (stepIndex < 3) {
                            onStepChange(stepIndex + 1)
                        } else {
                            onStepChange(0)
                        }
                    }
                    .background(
                        if (currentScreen is Screen.Training) Color.Red else colorResource(
                            id = R.color.main_color_1
                        ), shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu6),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)

                        .clickable { onScreenChange(Screen.Training) },
                    contentScale = ContentScale.Fit
                )
            }
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        if (stepIndex < 3) {
                            onStepChange(stepIndex + 1)
                        } else {
                            onStepChange(0)
                        }
                    }
                    .background(
                        if (currentScreen is Screen.Menu) Color.Red else colorResource(
                            id = R.color.main_color_1
                        ), shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu7),
                    contentDescription = "Описание",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)

                        .clickable { onScreenChange(Screen.Menu) },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun LevelProgressBar(
    progress: Float, // 0f..1f
    level: Int,
    trophies: Int,
    background: Color,
    barHeight: Dp = 35.dp,
    barLength: Dp = 100.dp

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    )
    {

        Column(
            modifier = Modifier
                .height(barHeight)
                .width(barLength)
                .offset(y = 11.dp)
        ) {
            // Прогресс-бар с уровнем
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
            ) {
                // Белый фон с закруглением
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(background)
                )
                // Красная заливка
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(Color.Red)
                )
            }
            Row {
                // Счетчик трофеев
                Text(
                    text = trophies.toString(),
                    color = Color.White,
                    fontSize = 16.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.menu7), // твоя иконка
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                )
            }
        }

        Box(modifier = Modifier.offset(x = -3.dp)){
            Box( modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.White),
                contentAlignment = Alignment.Center){
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = level.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LinearProgressBar(
    progress: Float, // 0f..1f
    background: Color,
    width: Dp,
    height: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    )
    {

        Column(
            modifier = Modifier
                .height(height)
                .width(width)
                .offset()
        ) {
            // Прогресс-бар
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(height)
            ) {
                // Белый фон с закруглением
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(background)
                )
                // Красная заливка
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(Color.Red)
                )
            }
        }
    }
}



@Composable
fun CircularProgressBar(
    progress: Float,       // текущее значение
    max: Float,            // максимум (например 1000 ккал)
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 10.dp,
    progressColor: Color = Color.Red,
    backgroundColor: Color = Color.Black,
    textColor: Color = Color.White,
    mainText: String = "",
    secondText: String = ""

) {
    val sweepAngle = (progress / max) * 360f

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = size.minDimension
            val radius = size / 2f
            val topLeft = Offset((this.size.width - size) / 2f, (this.size.height - size) / 2f)
            val arcSize = Size(size, size)

            // Фон
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                topLeft = topLeft,
                size = arcSize
            )

            // Прогресс
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                topLeft = topLeft,
                size = arcSize
            )
        }

        // Текст внутри
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mainText,
                style = TextStyle(
                    color = textColor,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = secondText,
                style = TextStyle(
                    color = textColor,
                    fontSize = 16.sp
                )
            )
        }
    }
}