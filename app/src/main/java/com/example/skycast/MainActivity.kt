package com.example.skycast

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherScreen()
        }
        window.setFlags(
           WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}


@Composable
fun WeatherScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#59469d")),
                        Color(android.graphics.Color.parseColor("#643d67"))
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Text(
                        text = "Mostly Cloudy",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(id = R.drawable.cloudy_sunny),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                    )

                    //Display Date And Time
                    Text(text= "Wednesday, 18 Sep | 9:00 AM",
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    //Displaying Temperature Details
                    Text(text ="25°C",
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(text = "H:27 L:18",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )

                    //Box Containing Weather Details Like rain, Humidity, Wind Speed
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .background(
                            color = colorResource(id = R.color.purple),
                            shape = RoundedCornerShape(25.dp)
                        )
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            WeatherDetailsItem(icon = R.drawable.rain,
                                value = "20%",
                                label = "Rain"
                            )
                            WeatherDetailsItem(icon = R.drawable.wind,
                                value = "12 Km/h",
                                label = "Wind Speed"
                            )
                            WeatherDetailsItem(icon = R.drawable.humidity,
                                value = "18%",
                                label = "Humidity"
                            )
                        }
                    }

                    // Display "Today" Label
                    Text(text = "Today",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
                //Displaying Hourly Forecast Using LazyRow
                item{
                    LazyRow(modifier = Modifier
                        .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        items(items){item->
                            FutureModelViewHolder(item)

                        }

                    }
                }
                item{
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Future",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Text(text = "Next 7 Days>",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(dailyItems){
                    FutureItem(it)
                }

            }
        }
    }
}
@Composable
fun FutureItem(item: FutureModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = item.day,
            fontSize = 14.sp,
            color = Color.White,
        )
        Image(painter = painterResource(id = GetDrawableResourceId(picPath = item.picPath)),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )
        Text(
            text = item.status,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "${item.highTemp}",
            modifier = Modifier
                .padding(end = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "${item.lowTemp}",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
@Composable
fun GetDrawableResourceId(picPath: String):Int{
    return when(picPath){
        "storm"->R.drawable.storm
        "cloudy"->R.drawable.cloudy
        "sunny"->R.drawable.sunny
        "windy"->R.drawable.windy
        "rainy"->R.drawable.rainy
        "cloudy_sunny"->R.drawable.cloudy_sunny
        else -> R.drawable.sunny
        }

}
// Sample Daily Data
val dailyItems = listOf(
    FutureModel("Sat","storm","Stormy",20,15),
    FutureModel("Sun","cloudy","Cloudy",24,12),
    FutureModel("Mon","windy","Windy",23,13),
    FutureModel("Tue","cloudy_sunny","Cloudy Sunny",22,11),
    FutureModel("Wed","sunny","Sunny",24,10),
    FutureModel("Thu","rainy","Rainy",23,14),
)
//ViewHolder for Each Forecast Item
@Composable
fun FutureModelViewHolder(model: HourlyModel){
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(
                color = colorResource(id = R.color.purple),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = model.hour,
            color = Color.White,
            fontSize= 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id =
            when(model.picPath){
                "cloudy" -> R.drawable.cloudy
                "windy" -> R.drawable.wind
                "sunny" -> R.drawable.sunny
                "rainy" -> R.drawable.rainy
                "stormy" -> R.drawable.storm
                else -> R.drawable.sunny
            }
            ),contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "${model.temp}",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,

        )
    }
}
//Sample Hourly Data
val items = listOf(
    HourlyModel("10 AM", 25, "cloudy"),
    HourlyModel("11 AM", 26, "sunny"),
    HourlyModel("12 PM", 27, "windy"),
    HourlyModel("1 PM", 28, "rainy"),
    HourlyModel("2 PM", 29, "cloudy"),
    HourlyModel("3 PM", 23, "stormy"),
    HourlyModel("4 PM", 25, "sunny"),
    )

@Composable
fun WeatherDetailsItem(icon: Int, value: String, label: String){
    Column(modifier = Modifier
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Text(text = label,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )

    }
}
@Preview
@Composable
fun WeatherScreenPreview(){
    WeatherScreen()
}