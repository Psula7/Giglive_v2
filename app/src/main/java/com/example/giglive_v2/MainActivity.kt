package com.example.giglive_v2


import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giglive_v2.ui.theme.Giglive_v2Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

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
                        CartelScreen(navController)
                    }
                    /*
                    composable("cartel") {
                        CartelScreen1(navController)
                    }
                    composable("cartel2") {
                        CartelScreen(navController)
                    }
                    composable("cartel3") {
                        CartelScreen(navController)
                    }
                    composable("cartel4") {
                        CartelScreen(navController)
                    }
                    composable("cartel5") {
                        CartelScreen(navController)
                    }
                    composable("cartel6") {
                        CartelScreen(navController)
                    }*/
                }
            }
        }
    }
}



@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenido de la pantalla principal
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                EventCard(
                    name = "Boombastic Madrid",
                    price = "80€",
                    location = "Rivas Vaciamadrid",
                    imageResource = R.drawable.cartel,
                    onClick = { navController.navigate("cartel?index=0") }
                )
            }
            item {
                EventCard(
                    name = "Instafest",
                    price = "0€",
                    location = "Instagram",
                    imageResource = R.drawable.cartel2,
                    onClick = { navController.navigate("cartel?index=1") }
                )
            }
            item {
                EventCard(
                    name = "La movida madrileña",
                    price = "40€",
                    location = "Puerta de montilla",
                    imageResource = R.drawable.cartel3,
                    onClick = { navController.navigate("cartel?index=2") }
                )
            }
            item {
                EventCard(
                    name = "Boombastic Asturias",
                    price = "85€",
                    location = "Llanera",
                    imageResource = R.drawable.cartel4,
                    onClick = { navController.navigate("cartel?index=3") }
                )
            }
            item {
                EventCard(
                    name = "Boombastic Gran Canaria",
                    price = "105€",
                    location = "Anexo Estadio Gran Canaria",
                    imageResource = R.drawable.cartel5,
                    onClick = { navController.navigate("cartel?index=4") }
                )
            }
            item {
                EventCard(
                    name = "Boombastic Costa del Sol",
                    price = "78€",
                    location = "Málaga Forum.",
                    imageResource = R.drawable.cartel6,
                    onClick = { navController.navigate("cartel?index=5") }
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CartelScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        count = 6, // Número de carteles
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> CartelScreen1(navController)
            1 -> CartelScreen2(navController)
            2 -> CartelScreen3(navController)
            3 -> CartelScreen4(navController)
            4 -> CartelScreen5(navController)
            5 -> CartelScreen6(navController)
        }
    }
}

@Composable
fun CartelContent(navController: NavController, imageResId: Int, audioRange: IntRange) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = audioRange.map { audioResId ->
        Artista(nombre = "Artista $audioResId", audioResId = audioResId)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                        color = Color.Magenta
                    )
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}
@Composable
fun CartelScreen1(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "BIZARRAP", audioResId = R.raw.audio1),
        Artista(nombre = "FUNZO & BABY LOUD", audioResId = R.raw.audio2),
        Artista(nombre = "RECYCLED J", audioResId = R.raw.audio3),
        Artista(nombre = "DELAOSSA", audioResId = R.raw.audio4),
        Artista(nombre = "PTAZETA", audioResId = R.raw.audio5),
        Artista(nombre = "POLE", audioResId = R.raw.audio6),
        Artista(nombre = "YSY A", audioResId = R.raw.audio7),
        Artista(nombre = "ZETAZEN", audioResId = R.raw.audio8),
        Artista(nombre = "HOKE & LOUIS AMOEBA", audioResId = R.raw.audio9),
        Artista(nombre = "JAIME LORENTE", audioResId = R.raw.audio10),
        Artista(nombre = "SAIKO", audioResId = R.raw.audio11),
        Artista(nombre = "LEO RIZZI", audioResId = R.raw.audio12),
        Artista(nombre = "NANO", audioResId = R.raw.audio13),
        Artista(nombre = "DUDI", audioResId = R.raw.audio14),
        Artista(nombre = "ENOL", audioResId = R.raw.audio15),
        Artista(nombre = "MUNIC HB", audioResId = R.raw.audio16),
        Artista(nombre = "DELGAO", audioResId = R.raw.audio17),
        Artista(nombre = "BON CALSO", audioResId = R.raw.audio18),
        Artista(nombre = "DANI RIBBA", audioResId = R.raw.audio19),
    )

    var offsetX by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxSize()

    ) {Image(
            painter = painterResource(id = R.drawable.cartel),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline,  fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun CartelScreen2(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "AITANA", audioResId = R.raw.c2audio1),
        Artista(nombre = "MARTIN URRUTIA", audioResId = R.raw.c2audio2),
        Artista(nombre = "BAD BUNNY", audioResId = R.raw.c2audio3),
        Artista(nombre = "DENNA", audioResId = R.raw.c2audio4),
        Artista(nombre = "NAIARA", audioResId = R.raw.c2audio5),
        Artista(nombre = "LUCAS CUROTTO", audioResId = R.raw.c2audio6),
        Artista(nombre = "RAUW ALEJANDRO", audioResId = R.raw.c2audio7),
        Artista(nombre = "ADELE", audioResId = R.raw.c2audio8),
        Artista(nombre = "DELLAFUENTE", audioResId = R.raw.c2audio9),
        Artista(nombre = "TINI", audioResId = R.raw.c2audio10),
        Artista(nombre = "MYKE TOWERS", audioResId = R.raw.c2audio11),

        Artista(nombre = "CHIARA OLIVER", audioResId = R.raw.c2audio12),
        Artista(nombre = "OPERACIÓN TRIUNFO 2023", audioResId = R.raw.c2audio13),
        Artista(nombre = "JUANJO BONA", audioResId = R.raw.c2audio14),
        Artista(nombre = "BEA FERNÁNDEZ", audioResId = R.raw.c2audio15),
        Artista(nombre = "TAYLOR SWIFT", audioResId = R.raw.c2audio16),
        Artista(nombre = "QUEVEDO", audioResId = R.raw.c2audio17),
        Artista(nombre = "OZUNA", audioResId = R.raw.c2audio18),
        Artista(nombre = "DUKI", audioResId = R.raw.c2audio19),
        Artista(nombre="ELADIO CARRION", audioResId = R.raw.c2audio20),
        Artista(nombre="COLDPLAY", audioResId = R.raw.c2audio21),
        Artista(nombre="SHAWN MENDES", audioResId = R.raw.c2audio22),

        Artista(nombre="RUSLANA", audioResId = R.raw.c2audio23),
        Artista(nombre="PAUL THIN", audioResId = R.raw.c2audio24),
        Artista(nombre="ÁLVARO MAYO", audioResId = R.raw.c2audio25),
        Artista(nombre="ANUEL AA", audioResId = R.raw.c2audio26),
        Artista(nombre="VIOLETA HÓDAR", audioResId = R.raw.c2audio27),
        Artista(nombre="MORA", audioResId = R.raw.c2audio28),
        Artista(nombre="BRYENT MYERS", audioResId = R.raw.c2audio29),
        Artista(nombre="BAD GYAL", audioResId = R.raw.c2audio30),
        Artista(nombre="NIO GARCÍA", audioResId = R.raw.c2audio31),
        Artista(nombre="RIHANNA", audioResId = R.raw.c2audio32),
        Artista(nombre="MELENDI", audioResId = R.raw.c2audio33),
    )



    Column(
        modifier = Modifier.fillMaxSize()

    ) {Image(
            painter = painterResource(id = R.drawable.cartel2),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun CartelScreen3(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "PISTONES", audioResId = R.raw.c3audio1),
        Artista(nombre = "LA UNIÓN", audioResId = R.raw.c3audio2),
        Artista(nombre = "MECANO", audioResId = R.raw.c3audio3),
        Artista(nombre = "ALASKA Y DINARAMA", audioResId = R.raw.c3audio4),
        Artista(nombre = "LA FRONTERA", audioResId = R.raw.c3audio5),
        Artista(nombre = "COMPLICES", audioResId = R.raw.c3audio6),
        Artista(nombre = "DUNCAN CHU", audioResId = R.raw.c3audio7),
        Artista(nombre = "CADILLAC", audioResId = R.raw.c3audio8),
        Artista(nombre = "ZOMBIES", audioResId = R.raw.c3audio9),
        Artista(nombre = "ORQUESTA MONDRAGON", audioResId = R.raw.c3audio10),
        Artista(nombre = "RUBI Y LOS CASINOS", audioResId = R.raw.c3audio11),
        Artista(nombre = "LA MODE", audioResId = R.raw.c3audio12),
        Artista(nombre = "LUZ CASAL", audioResId = R.raw.c3audio13),
        Artista(nombre = "PEOR IMPOSSIBLE", audioResId = R.raw.c3audio14),
        Artista(nombre = "NACHA POP", audioResId = R.raw.c3audio15),
        Artista(nombre = "TEQUILA", audioResId = R.raw.c3audio16),
        Artista(nombre = "LOS REBELDES", audioResId = R.raw.c3audio17),
        Artista(nombre = "OLE OLE", audioResId = R.raw.c3audio18),
        Artista(nombre = "HOMBRES G", audioResId = R.raw.c3audio19),
        Artista(nombre = "TINO CASAL", audioResId = R.raw.c3audio20),
        Artista(nombre = "LOQUILLO Y LOS TROGLODITAS", audioResId = R.raw.c3audio21),
    )

    Column(
        modifier = Modifier.fillMaxSize()

    ) {Image(
            painter = painterResource(id = R.drawable.cartel3),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline,  fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}


@Composable
fun CartelScreen4(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "LOLA ÍNDIGO", audioResId = R.raw.c4audio1),
        Artista(nombre = "NATOS Y WAOR", audioResId = R.raw.c4audio2),
        Artista(nombre = "RELS B", audioResId = R.raw.c4audio3),
        Artista(nombre = "SAIKO", audioResId = R.raw.audio11),
        Artista(nombre = "YANDEL", audioResId = R.raw.c4audio5),
    )


    Column(
        modifier = Modifier.fillMaxSize()

    ) {Image(
            painter = painterResource(id = R.drawable.cartel4),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline,  fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun CartelScreen5(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "LOLA ÍNDIGO", audioResId = R.raw.c4audio1),
        Artista(nombre = "NICKI NICOLE", audioResId = R.raw.c5audio2),
        Artista(nombre = "RELS B", audioResId = R.raw.c4audio3),
        Artista(nombre = "YANDEL", audioResId = R.raw.c4audio5),
    )


    Column(
        modifier = Modifier.fillMaxSize()
            // Permitir el desplazamiento vertical

    ) {Image(
            painter = painterResource(id = R.drawable.cartel5),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline,  fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
    }
}



@Composable
fun CartelScreen6(navController: NavController) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var artistaActivo by remember { mutableStateOf<Artista?>(null) }

    val artistas = listOf(
        Artista(nombre = "DUKI", audioResId = R.raw.c2audio19),
        Artista(nombre = "BIZARRAP", audioResId = R.raw.audio1),
        Artista(nombre = "FUNZO & BABY LOUD", audioResId = R.raw.audio2),
        Artista(nombre = "QUEVEDO", audioResId = R.raw.c2audio17),
        Artista(nombre = "POLE HENS", audioResId = R.raw.c6audio5),
        Artista(nombre = "DUDI", audioResId = R.raw.audio14),
    )


    Column(
        modifier = Modifier.fillMaxSize()

    ) {Image(
            painter = painterResource(id = R.drawable.cartel6),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artistas) { artista ->
                val isPlaying = artista == artistaActivo
                val textStyle = if (isPlaying) {
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline,  fontWeight = FontWeight.Bold, color = Color.Magenta)
                } else {
                    MaterialTheme.typography.bodyMedium
                }
                Text(
                    text = artista.nombre,
                    style = textStyle.copy(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayer?.release() // Detener la música si está sonando
                                mediaPlayer = null
                                artistaActivo = null
                            } else {
                                mediaPlayer?.release() // Detener el audio actual si hay uno reproduciendo
                                mediaPlayer = MediaPlayer.create(context, artista.audioResId)
                                mediaPlayer?.start()
                                mediaPlayer?.setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    artistaActivo = null
                                }
                                artistaActivo = artista
                            }
                        }
                )
            }
        }
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
            .fillMaxWidth()
            .clickable(onClick = onClick),
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


data class Artista(val nombre: String, val audioResId: Int)

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Giglive_v2Theme {
        MainScreen(navController = rememberNavController())
    }
}


