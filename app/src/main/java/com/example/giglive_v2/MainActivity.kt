package com.example.giglive_v2


import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giglive_v2.ui.theme.Giglive_v2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Giglive_v2Theme {
                //CartelScreen3()
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController)
                    }
                    composable("cartel") {
                        CartelScreen()
                    }
                    composable("cartel2") {
                        CartelScreen2()
                    }
                    composable("cartel3") {
                        CartelScreen3()
                    }
                }
            }
        }
    }
}



@Composable
fun MainScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        // Contenido de la pantalla principal
        EventCard(
            name = "Boombastic",
            price = "80€",
            location = "Rivas Vaciamadrid",
            imageResource = R.drawable.cartel,
            onClick = { navController.navigate("cartel") }
        )
        EventCard(
            name = "Instafest",
            price = "0€",
            location = "Instagram",
            imageResource = R.drawable.cartel2,
            onClick = { navController.navigate("cartel2") }
        )
        EventCard(
            name = "La movida madrileña",
            price = "40€",
            location = "Puerta de montilla",
            imageResource = R.drawable.cartel3,
            onClick = { navController.navigate("cartel3") }
        )
    }
}

@Composable
fun CartelScreen() {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentAudioResId by remember { mutableStateOf<Int?>(null) }

    val artistAreas = listOf(
        ClickableArea(x = 350, y = 1045, width = 380, height = 45, audioResId = R.raw.audio1),
        ClickableArea(x = 275, y = 1110, width = 550, height = 45, audioResId = R.raw.audio2),
        ClickableArea(x = 210, y = 1170, width = 330, height = 45, audioResId = R.raw.audio3),
        ClickableArea(x = 600, y = 1170, width = 290, height = 45, audioResId = R.raw.audio4),
        ClickableArea(x = 205, y = 1225, width = 230, height = 45, audioResId = R.raw.audio5),
        ClickableArea(x = 500, y = 1225, width = 150, height = 45, audioResId = R.raw.audio6),
        ClickableArea(x = 720, y = 1225, width = 180, height = 45, audioResId = R.raw.audio7),
        ClickableArea(x = 160, y = 1280, width = 200, height = 40, audioResId = R.raw.audio8),
        ClickableArea(x = 400, y = 1280, width = 530, height = 40, audioResId = R.raw.audio9),
        ClickableArea(x = 155, y = 1329, width = 340, height = 38, audioResId = R.raw.audio10),
        ClickableArea(x = 535, y = 1329, width = 140, height = 38, audioResId = R.raw.audio11),
        ClickableArea(x = 720, y = 1329, width = 220, height = 38, audioResId = R.raw.audio12),
        ClickableArea(x = 155, y = 1378, width = 135, height = 38, audioResId = R.raw.audio13),
        ClickableArea(x = 350, y = 1378, width = 120, height = 38, audioResId = R.raw.audio14),
        ClickableArea(x = 525, y = 1378, width = 120, height = 38, audioResId = R.raw.audio15),
        ClickableArea(x = 700, y = 1378, width = 225, height = 38, audioResId = R.raw.audio16),
        ClickableArea(x = 155, y = 1428, width = 170, height = 38, audioResId = R.raw.audio17),
        ClickableArea(x = 370, y = 1428, width = 260, height = 38, audioResId = R.raw.audio18),
        ClickableArea(x = 675, y = 1428, width = 260, height = 38, audioResId = R.raw.audio19),
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cartel),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown().run {
                                val x = this.position.x
                                val y = this.position.y
                                artistAreas.forEach { area ->
                                    if (x >= area.x && x <= area.x + area.width &&
                                        y >= area.y && y <= area.y + area.height
                                    ) {
                                        if (area.audioResId == currentAudioResId) {
                                            mediaPlayer?.release() // Detener el audio actual
                                            currentAudioResId = null
                                            mediaPlayer = null
                                            return@run
                                        }
                                        mediaPlayer?.release() // Detener el audio actual
                                        mediaPlayer = MediaPlayer.create(context, area.audioResId)
                                        mediaPlayer?.start()
                                        mediaPlayer?.setOnCompletionListener {
                                            mediaPlayer?.release()
                                            mediaPlayer = null
                                            currentAudioResId = null
                                        }
                                        currentAudioResId = area.audioResId
                                        return@forEach
                                    }
                                }
                            }
                        }
                    }
                }
        )
    }
}

@Composable
fun CartelScreen2() {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentAudioResId by remember { mutableStateOf<Int?>(null) }

    val artistAreas = listOf(
        ClickableArea(x = 420, y = 725, width = 240, height = 50, audioResId = R.raw.c2audio1),
        ClickableArea(x = 200, y = 800, width = 290, height = 30, audioResId = R.raw.c2audio2),
        ClickableArea(x = 520, y = 800, width = 210, height = 30, audioResId = R.raw.c2audio3),
        ClickableArea(x = 760, y = 800, width = 120, height = 30, audioResId = R.raw.c2audio4),
        ClickableArea(x = 210, y = 848, width = 96, height = 28, audioResId = R.raw.c2audio5),
        ClickableArea(x = 330, y = 848, width = 190, height = 28, audioResId = R.raw.c2audio6),
        ClickableArea(x = 550, y = 848, width = 210, height = 28, audioResId = R.raw.c2audio7),
        ClickableArea(x = 795, y = 848, width = 70, height = 28, audioResId = R.raw.c2audio8),
        ClickableArea(x = 325, y = 885, width = 155, height = 28, audioResId = R.raw.c2audio9),
        ClickableArea(x = 510, y = 885, width = 50, height = 28, audioResId = R.raw.c2audio10),
        ClickableArea(x = 585, y = 885, width = 170, height = 28, audioResId = R.raw.c2audio11),

        ClickableArea(x = 310, y = 950, width = 460, height = 60, audioResId = R.raw.c2audio12),
        ClickableArea(x = 185, y = 1030, width = 435, height = 30, audioResId = R.raw.c2audio13),
        ClickableArea(x = 665, y = 1030, width = 230, height = 30, audioResId = R.raw.c2audio14),
        ClickableArea(x = 180, y = 1075, width = 200, height = 28, audioResId = R.raw.c2audio15),
        ClickableArea(x = 400, y = 1075, width = 165, height = 28, audioResId = R.raw.c2audio16),
        ClickableArea(x = 595, y = 1075, width = 110, height = 28, audioResId = R.raw.c2audio17),
        ClickableArea(x = 735, y = 1075, width = 75, height = 28, audioResId = R.raw.c2audio18),
        ClickableArea(x = 840, y = 1075, width = 60, height = 28, audioResId = R.raw.c2audio19),
        ClickableArea(x = 255, y = 1113, width = 200, height = 28, audioResId = R.raw.c2audio20),
        ClickableArea(x = 485, y = 1113, width = 120, height = 28, audioResId = R.raw.c2audio21),
        ClickableArea(x = 635, y = 1113, width = 190, height = 28, audioResId = R.raw.c2audio22),

        ClickableArea(x = 400, y = 1180, width = 280, height = 60, audioResId = R.raw.c2audio23),
        ClickableArea(x = 220, y = 1255, width = 170, height = 30, audioResId = R.raw.c2audio24),
        ClickableArea(x = 430, y = 1255, width = 230, height = 30, audioResId = R.raw.c2audio25),
        ClickableArea(x = 700, y = 1255, width = 160, height = 30, audioResId = R.raw.c2audio26),
        ClickableArea(x = 140, y = 1300, width = 185, height = 28, audioResId = R.raw.c2audio27),
        ClickableArea(x = 355, y = 1300, width = 65, height = 28, audioResId = R.raw.c2audio28),
        ClickableArea(x = 450, y = 1300, width = 185, height = 28, audioResId = R.raw.c2audio29),
        ClickableArea(x = 660, y = 1300, width = 110, height = 28, audioResId = R.raw.c2audio30),
        ClickableArea(x = 800, y = 1300, width = 140, height = 28, audioResId = R.raw.c2audio31),
        ClickableArea(x = 420, y = 1337, width = 110, height = 28, audioResId = R.raw.c2audio32),
        ClickableArea(x = 555, y = 1337, width = 110, height = 28, audioResId = R.raw.c2audio33),


        )


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cartel2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown().run {
                                val x = this.position.x
                                val y = this.position.y
                                artistAreas.forEach { area ->
                                    if (x >= area.x && x <= area.x + area.width &&
                                        y >= area.y && y <= area.y + area.height
                                    ) {
                                        if (area.audioResId == currentAudioResId) {
                                            mediaPlayer?.release() // Detener el audio actual
                                            currentAudioResId = null
                                            mediaPlayer = null
                                            return@run
                                        }
                                        mediaPlayer?.release() // Detener el audio actual
                                        mediaPlayer = MediaPlayer.create(context, area.audioResId)
                                        mediaPlayer?.start()
                                        mediaPlayer?.setOnCompletionListener {
                                            mediaPlayer?.release()
                                            mediaPlayer = null
                                            currentAudioResId = null
                                        }
                                        currentAudioResId = area.audioResId
                                        return@forEach
                                    }
                                }
                            }
                        }
                    }
                }
        )

    }
}
@Composable
fun CartelScreen3() {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentAudioResId by remember { mutableStateOf<Int?>(null) }

    val artistAreas = listOf(
        ClickableArea(x = 40, y = 1425, width = 160, height = 25, audioResId = R.raw.c3audio1),
        ClickableArea(x = 40, y = 1470, width = 160, height = 25, audioResId = R.raw.c3audio2),
        ClickableArea(x = 40, y = 1512, width = 140, height = 25, audioResId = R.raw.c3audio3),
        ClickableArea(x = 225, y = 1512, width = 342, height = 25, audioResId = R.raw.c3audio4),
        ClickableArea(x = 40, y = 1555, width = 220, height = 25, audioResId = R.raw.c3audio5),
        ClickableArea(x = 315, y = 1555, width = 180, height = 25, audioResId = R.raw.c3audio6),
        ClickableArea(x = 40, y = 1597, width = 220, height = 25, audioResId = R.raw.c3audio7),
        ClickableArea(x = 305, y = 1597, width = 160, height = 25, audioResId = R.raw.c3audio8),
        ClickableArea(x = 40, y = 1638, width = 140, height = 25, audioResId = R.raw.c3audio9),
        ClickableArea(x = 235, y = 1638, width = 390, height = 25, audioResId = R.raw.c3audio10),
        ClickableArea(x = 40, y = 1683, width = 340, height = 25, audioResId = R.raw.c3audio11),
        ClickableArea(x = 425, y = 1683, width = 145, height = 25, audioResId = R.raw.c3audio12),
        ClickableArea(x = 40, y = 1723, width = 170, height = 25, audioResId = R.raw.c3audio13),
        ClickableArea(x = 265, y = 1723, width = 295, height = 25, audioResId = R.raw.c3audio14),
        ClickableArea(x = 40, y = 1764, width = 190, height = 25, audioResId = R.raw.c3audio15),
        ClickableArea(x = 273, y = 1764, width = 140, height = 25, audioResId = R.raw.c3audio16),
        ClickableArea(x = 40, y = 1808, width = 240, height = 25, audioResId = R.raw.c3audio17),
        ClickableArea(x = 330, y = 1808, width = 140, height = 25, audioResId = R.raw.c3audio18),
        ClickableArea(x = 40, y = 1851, width = 200, height = 25, audioResId = R.raw.c3audio19),
        ClickableArea(x = 280, y = 1851, width = 195, height = 25, audioResId = R.raw.c3audio20),
        ClickableArea(x = 40, y = 1895, width = 510, height = 25, audioResId = R.raw.c3audio21),

        )



    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cartel3),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown().run {
                                val x = this.position.x
                                val y = this.position.y
                                artistAreas.forEach { area ->
                                    if (x >= area.x && x <= area.x + area.width &&
                                        y >= area.y && y <= area.y + area.height
                                    ) {
                                        if (area.audioResId == currentAudioResId) {
                                            mediaPlayer?.release() // Detener el audio actual
                                            currentAudioResId = null
                                            mediaPlayer = null
                                            return@run
                                        }
                                        mediaPlayer?.release() // Detener el audio actual
                                        mediaPlayer = MediaPlayer.create(context, area.audioResId)
                                        mediaPlayer?.start()
                                        mediaPlayer?.setOnCompletionListener {
                                            mediaPlayer?.release()
                                            mediaPlayer = null
                                            currentAudioResId = null
                                        }
                                        currentAudioResId = area.audioResId
                                        return@forEach
                                    }
                                }
                            }
                        }
                    }
                }
        )
/*
                Canvas(modifier = Modifier.fillMaxSize()) {
                    artistAreas.forEach { area ->
                        drawCircle(
                            color = Color.Blue,
                            center = Offset(area.x.toFloat(), area.y.toFloat()),
                            radius = 8f
                        )
                        drawCircle(
                            color = Color.Blue,
                            center = Offset((area.x + area.width).toFloat(), area.y.toFloat()),
                            radius = 8f
                        )
                        drawCircle(
                            color = Color.Blue,
                            center = Offset((area.x + area.width).toFloat(), (area.y + area.height).toFloat()),
                            radius = 8f
                        )
                        drawCircle(
                            color = Color.Blue,
                            center = Offset(area.x.toFloat(), (area.y + area.height).toFloat()),
                            radius = 8f
                        )
                    }
                }

*/
    }
}
@Composable
fun EventCard(
    name: String,
    price: String,
    location: String,
    imageResource: Int, // Recurso de imagen, por ejemplo: R.drawable.mi_imagen
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(

        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null, // Opcional: en este caso no se necesita descripción
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Price: $price", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(start = 8.dp))
                Text(text = "Location: $location", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onClick) {
                    Text(text = "Ver cartel")
                }
            }
        }
    }
}

@Composable
fun EventCard1(name: String, price: String, location: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        //elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Price: $price", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: $location", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onClick) {
                Text(text = "Ver cartel")
            }
        }
    }
}


data class ClickableArea(val x: Int, val y: Int, val width: Int, val height: Int, val audioResId: Int)

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Giglive_v2Theme {
        MainScreen(navController = rememberNavController())
    }
}
