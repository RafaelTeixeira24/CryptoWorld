@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cryptowr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptowr.ui.theme.CryptoWrTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoWrTheme {
                CryptoWr()
            }
        }
    }
}
@Composable
fun CryptoWr() {
    var inputAmount by rememberSaveable { mutableStateOf("") }
    var convertedAmount : Double? by rememberSaveable { mutableStateOf(0.0) }
    var fromCurrency by rememberSaveable { mutableStateOf("BTC") }
    var toCurrency by rememberSaveable { mutableStateOf("ETH") }
    var toCurrencyDisp by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = inputAmount,
            onValueChange = { inputAmount = it },
            label = { Text("Enter Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        DropDown("From Currency", fromCurrency, {fromCurrency = it})

        Spacer(modifier = Modifier.height(8.dp))


        DropDown("To currency", toCurrency, {toCurrency = it})

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
            text = "Converted Amount: %.2f $toCurrencyDisp".format(convertedAmount),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
val coins : List<String> = listOf("BTC", "ETH", "LTC", "BNB")
val values : List<Double> = listOf(57646.78, 3183.93, 66.39, 539.09)
fun convert(amount: Double, fromCurrency: String, toCurrency: String) : Double{
    val indFrom = coins.indexOf(fromCurrency)
    val indTo = coins.indexOf(toCurrency)
    return (amount * values[indFrom]) / values[indTo]
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoWrTheme {
        CryptoWr()
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(label : String, selectedCurrency : String, currencySelected : (String) -> Unit){
    val list : List<String> = listOf("BTC", "ETH", "LTC", "BNB")
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
                list.forEachIndexed { i, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            currencySelected(list[i])
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }

            }
        }

}