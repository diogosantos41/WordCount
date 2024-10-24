package com.dscoding.wordcount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.wordcount.ui.theme.WordCountTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordCountTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var text by remember { mutableStateOf("") }

                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Label") },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn {
                            items(getListOccurrences(text)) {
                                Text(text = "${it.first} - ${it.second}")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getListOccurrences(phrase: String): List<Pair<String, Int>> {
    val words = phrase.trim().lowercase().split(" ").filter { it.isNotEmpty() }
    val wordCountMap = words.groupingBy { it }.eachCount()
    return wordCountMap.entries.map { it.toPair() }.sortedBy { it.second }
}