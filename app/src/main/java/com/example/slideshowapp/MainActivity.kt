package com.example.slideshowapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                SlideshowScreen()
            }
        }
    }
}

@Composable
fun SlideshowScreen() {
    val images = listOf(
        Pair(R.drawable.image1, "Image 1"),
        Pair(R.drawable.image2, "Image 2")
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = images[currentIndex].first),
            contentDescription = "Displayed Image",
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = images[currentIndex].second,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.width(100.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        innerTextField()
                    }
                }
            )

            Button(onClick = {
                val index = inputText.text.toIntOrNull()?.minus(1)
                if (index != null && index in images.indices) {
                    currentIndex = index
                } else {
                    Toast.makeText(context, "Enter a number between 1 and ${images.size}", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Go")
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = {
                currentIndex = (currentIndex - 1 + images.size) % images.size
            }) {
                Text(text = "Back")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                currentIndex = (currentIndex + 1) % images.size
            }) {
                Text(text = "Next")
            }
        }
    }
}
