package com.example.kotlin_flows_operators

import android.net.wifi.WifiInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_flows_operators.ui.theme.Kotlin_flows_operatorsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kotlin_flows_operatorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    consumer()
                }
            }
        }
    }
}

fun producer():Flow<Int>{

   return flow {
       for (i in 1..10){
           delay(1000)
           emit(i)
       }
   }
}

fun consumer(){
    CoroutineScope(Dispatchers.Main).launch {
        producer()

            //terminal operators:
            //terminal operators starts flow otherwise the flow will not start

//            .onStart {
//                emit(0) //we can emit here too
//                Log.d("flowsoperators","onstarts")
//            }
//            .onCompletion {
//                emit(20) //we can emit here too
//                Log.d("flowsoperators","oncompletetion")
//            }
//            .onEach {
//                Log.d("flowsoperators","about to commit:$it")
//            }
//            .collect{data->
//            Log.d("flowsoperators","$data")
//            }



        //non terminal operators:

            .buffer(4) //if producer or consumer doesnt work same time like producer producing
            //data in 100mills and consumer collecting in 1500 millis the buffer comes to work we can buffer
            //data those are produced and not comsumed yet.
            .map {
                //it*2
                it.toDouble()//map also helps to convert data into another class
            }
            .filter {
                it < 10 //it filters data
            }

            .collect{data->
            Log.d("flowsoperators","$data")
            }
    }
}

//note:there are many operators for more see doc



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kotlin_flows_operatorsTheme {

    }
}