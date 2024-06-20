package com.example.cryptowr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = inputAmount,
            onValueChange = { inputAmount = it },
            label = { Text("Enter Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = fromCurrency,
            onValueChange = { fromCurrency = it },
            label = { Text("From Currency (BTC, ETH, LTC, BNB)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = toCurrency,
            onValueChange = { toCurrency = it },
            label = { Text("To Currency (BTC, ETH, LTC, BNB)") },
            modifier = Modifier.fillMaxWidth()
        )

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
            text = "Converted Amount: $convertedAmount $toCurrencyDisp",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun convert(amount: Double, fromCurrency : String, toCurrency : String): Double {
    return when (fromCurrency){
        "BTC" -> convertBTC(toCurrency, amount)
        "ETH" -> convertETH(toCurrency, amount)
        "LTC" -> convertLTC(toCurrency, amount)
        "BNB" -> convertBNB(toCurrency, amount)
        else -> {0.0}
    }
}
fun convertBTC(toCurrency: String, amount: Double): Double {
    return when (toCurrency) {
        "BTC" -> amount
        "ETH" -> amount * 18.44
        "LTC" -> amount * 873.83
        "BNB" -> amount * 108.85
        else -> 0.0
    }
}

fun convertETH(toCurrency: String, amount: Double): Double {
    return when (toCurrency) {
        "BTC" -> amount * 0.054
        "ETH" -> amount
        "LTC" -> amount * 47.39
        "BNB" -> amount * 5.92
        else -> 0.0
    }
}

fun convertLTC(toCurrency: String, amount: Double): Double {
    return when (toCurrency) {
        "BTC" -> amount * 0.05
        "ETH" -> amount * 0.5
        "LTC" -> amount
        "BNB" -> amount * 0.12
        else -> 0.0
    }
}
fun convertBNB(toCurrency: String, amount: Double): Double {
    return when (toCurrency) {
        "BTC" -> amount * 0.0092
        "ETH" -> amount * 0.17
        "LTC" -> amount * 8.06
        "BNB" -> amount
        else -> 0.0
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoWrTheme {
        CryptoWr()
    }
}