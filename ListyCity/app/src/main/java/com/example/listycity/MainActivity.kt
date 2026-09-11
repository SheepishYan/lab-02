package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onRemoveCity = {cityRepository.removeCity(it)},
                        onConfirm = {cityRepository.addCity(it)},
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(
    cities: List<String>,
    //onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("")
    }
    var displayAddCity by remember {mutableStateOf(false)
    }
    var selectedItem by remember {mutableStateOf("")
    }


    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    displayAddCity = !displayAddCity
                }
            ) {
                Text("Add City")
            }
            if (displayAddCity) {
                Column(modifier = modifier.padding(4.dp)) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        OutlinedTextField(
                            value = newCityName,
                            onValueChange = { newCityName = it },
                            label = { Text("City Name") },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                if (newCityName.isNotBlank()) {
                                    onConfirm(newCityName)
                                    newCityName = ""
                                    displayAddCity = false
                                }
                            }
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
            Button(
                onClick = {
                    if (selectedItem != "") {
                        onRemoveCity(selectedItem)
                    }
                }
            ) {
                Text("Remove City")
            }




        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(cities) { city ->
                CityRow(city = city, onClick = {selectedItem = city})

            }



        }
    }
}
@Composable
fun CityRow(city: String, onClick: () -> Unit) {
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
            .padding(horizontal = 18.dp, vertical = 14.dp)
    )
}
class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow", "Sydney",
        "Berlin", "Osaka", "Vienna", "Tokyo", "Beijing",
        "New Delhi"
    )
    val cities: List<String>
        get() = _cities
    fun addCity(city: String) {
        _cities.add(city)
    }
    fun removeCity(city: String) {
        _cities.remove(city)
    }
}
