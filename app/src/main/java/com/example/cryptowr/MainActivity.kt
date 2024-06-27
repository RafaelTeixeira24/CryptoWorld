@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cryptowr

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptowr.ui.theme.CryptoWrTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.w3c.dom.DocumentType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoWrTheme {
                val navControll = rememberNavController()
                NavHost(navControll, startDestination = "conversor"){
                    composable("conversor"){ CryptoWrConversor(navControll)}
                    composable("tracker"){ CryptoWrTracker(navControll)}
                }
            }
        }
    }
}

val coins : List<String> = listOf("BTC", "ETH", "LTC", "BNB", "EUR", "ADA", "SOL", "XRP", "DOT", "DOGE", "AVAX", "LINK", "VET")
val values : List<Double> = listOf(57174.99, 3175.88, 66.47, 534.39, 1.0, 0.36, 128.08, 0.44, 5.37, 0.12, 4.55, 12.94, 0.02442127)

@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = { navController.navigate("conversor") }) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = "Conversor")
            }
            IconButton(onClick = { navController.navigate("tracker") }) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "Tracker")
            }
        }
    )
}

@Composable
fun CryptoWrTracker(navControll: NavController){
    val coinsOrd = coins
    val valuesOrd = values
    Scaffold(
        topBar = { TopBar(navControll) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(coinsOrd.size) { index ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(vertical = 5.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = coinsOrd[index],
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = stringResource(id = R.string.value_display, valuesOrd[index]),
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
@Composable
fun CryptoWrConversor(navControll: NavController) {
    var inputAmount by rememberSaveable { mutableStateOf("") }
    var convertedAmount : Double by rememberSaveable { mutableDoubleStateOf(0.0) }
    var fromCurrency by rememberSaveable { mutableStateOf("BTC") }
    var toCurrency by rememberSaveable { mutableStateOf("ETH") }
    var toCurrencyDisp by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = { TopBar(navControll) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = inputAmount,
                    onValueChange = { inputAmount = it },
                    label = { Text(stringResource(R.string.enter_amount_text)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(16.dp))

                DropDown(stringResource(R.string.from_currency_text), fromCurrency) { fromCurrency = it }

                Spacer(modifier = Modifier.height(8.dp))

                DropDown(stringResource(R.string.to_currency_text), toCurrency) { toCurrency = it }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Simulate conversion (replace with real API call)
                        convertedAmount = convert(inputAmount.toDoubleOrNull() ?: 0.0, fromCurrency, toCurrency)
                        toCurrencyDisp = toCurrency
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Convert")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.converted_amount_text, convertedAmount, toCurrencyDisp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
fun convert(amount: Double, fromCurrency: String, toCurrency: String) : Double{
    val indFrom = coins.indexOf(fromCurrency)
    val indTo = coins.indexOf(toCurrency)
    return (amount * values[indFrom]) / values[indTo]
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(label : String, selectedCurrency : String, currencySelected : (String) -> Unit){
   var isExpanded : Boolean by rememberSaveable { mutableStateOf(false)}


        ExposedDropdownMenuBox(

            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded

                Log.d("AA", isExpanded.toString())
            }
        ) {
            TextField(

                value = selectedCurrency,
                label = { Text(label) },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                coins.forEachIndexed { i, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            currencySelected(coins[i])
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }

            }
        }

}

