package com.example.lab01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addButtonClick(view: View) {
        makeOperation(CalcOperation.Addition)
    }

    fun subButtonClick(view: View) {
        makeOperation(CalcOperation.Substraction)
    }

    fun multiplyButtonClick(view: View) {
        makeOperation(CalcOperation.Multiplication)
    }

    fun divideButtonClick(view: View) {
        makeOperation(CalcOperation.Division)
    }

    fun powButtonClick(view: View) {
        makeOperation(CalcOperation.Power)
    }

    private fun makeOperation(operation: CalcOperation) {
        val edit1: EditText = findViewById(R.id.number1)
        val n1 = edit1.text.toString().toFloatOrNull()

        val edit2: EditText = findViewById(R.id.number2)
        val n2 = edit2.text.toString().toFloatOrNull()

        if (n1 == null || n2 == null) {
            val errorText = "Введите корректные числа"
            val textView: TextView = findViewById(R.id.result)
            textView.text = String.format(errorText)

            return
        }

        val n = when(operation) {
            CalcOperation.Addition -> n1 + n2
            CalcOperation.Substraction -> n1 - n2
            CalcOperation.Multiplication -> n1 * n2
            CalcOperation.Division -> n1 / n2
            CalcOperation.Power -> n1.pow(n2)
        }

        val textView: TextView = findViewById(R.id.result)
        val resText = resources.getString(R.string.operation_result)
        textView.text = String.format(resText, n)
    }
}