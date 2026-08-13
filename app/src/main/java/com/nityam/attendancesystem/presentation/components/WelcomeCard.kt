package com.nityam.attendancesystem.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeCard(count : Int ,
                onIncrement: () -> Unit ,
                onDecrement: () -> Unit
){

    Card(
        modifier = Modifier
                     .fillMaxWidth()
                      .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = "Count : $count")

            Row(){
                  Button(onClick = onIncrement) {
                      Text(text = "+")
                  }

                Spacer(modifier = Modifier.width(5.dp))
                Button(onClick = onDecrement) {
                    Text(text = "-")
                }
            }

//            Text(text = "Welcome")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text =  "Attendance System")
//            Spacer(modifier =  Modifier.height(16.dp))
//
//            Button(onClick = {}) {
//                Text(text =  "Continue")
//            }

        }

    }
}

