package com.example.mykotlinflowapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val myCountDownTimer = flow {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        println("The value from VM is $currentValue")
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
            println("The value from VM is $currentValue")
        }
    }

    init {
        countDownFlow()
    }

    private fun countDownFlow() {
        viewModelScope.launch {
            myCountDownTimer
                .filter { it % 2 == 0 }
                .map { it * it }
                .collect {
                    println("The value is $it")
                }

            val reducedResultAddAll = myCountDownTimer.reduce { accumulator, value ->
                accumulator + value
            }

            println("Accumulated reduced value(sum of all numbers 0 to 10) is $reducedResultAddAll")

            val foldedResultPlusInitialValue = myCountDownTimer.fold(100) { acc: Int, value: Int ->
                acc + value
            }.plus(1)

            println("Accumulated folded value(sum of all numbers 0 to 10 + 100 initial + 1 end value) is $foldedResultPlusInitialValue")

            val foldedResultPlusInitialValueWithOperator =
                myCountDownTimer.filter { it % 2 == 0 }.fold(100) { acc: Int, value: Int ->
                    acc + value
                }.plus(1)

            println("Accumulated folded value(10 + 8 + 6 + 4 + 2 + 0 + 100 initial + 1 end value) is $foldedResultPlusInitialValueWithOperator")

            flatMapsExample()
        }
    }

    private fun flatMapsExample() {
        val flow1 = flow {
            emit(1)
            delay(500)
            emit(2)
        }

        viewModelScope.launch {
            flow1.flatMapConcat { valueFromFlow1 ->
                flow {
                    emit(valueFromFlow1 + 1)
                    delay(500)
                    emit(valueFromFlow1 + 2)
                }
            }.collect {
                println("The value from the flow1 + flow is $it")
            }
        }
    }
}
