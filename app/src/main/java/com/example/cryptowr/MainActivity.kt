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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptowr.ui.theme.CryptoWrTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

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
    var inputAmount by remember { mutableStateOf("") }
    var convertedAmount : Double? by remember { mutableStateOf(0.0) }
    var fromCurrency by remember { mutableStateOf("BTC") }
    var toCurrency by remember { mutableStateOf("ETH") }
    var toCurrencyDisp by remember { mutableStateOf("") }

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
            label = { Text("From Currency") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = toCurrency,
            onValueChange = { toCurrency = it },
            label = { Text("To Currency") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Simulate conversion (replace with real API call)
                convertedAmount = 0.0
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoWrTheme {
        CryptoWr()
    }
}