package com.example.lab04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var expressions: IntArray = IntArray(4)
    private var numLimit: Int = 10
    private var answer: Int = 0
    private var rightAnswers: Int = 0
    private var wrongAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.rightAnswersCountTextView).text = "Верно: $rightAnswers"
        findViewById<TextView>(R.id.wrongAnswersCountTextView).text = "Неверно: $wrongAnswers"

        createExpressions()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val adapter = ExpressionAdapter(expressions) {
            if (expressions[it] == answer) {
                rightAnswers++
                findViewById<TextView>(R.id.rightAnswersCountTextView).text = "Верно: $rightAnswers"
                if (rightAnswers % 3 == 0) {
                    numLimit *= 10
                }
            } else {
                wrongAnswers++
                findViewById<TextView>(R.id.wrongAnswersCountTextView).text = "Неверно: $wrongAnswers"
            }

             createExpressions()
            recyclerView.adapter!!.notifyDataSetChanged()
        }
        recyclerView.adapter = adapter
    }

    private fun createExpressions() {
        val number1 = Random.nextInt(-numLimit, numLimit)
        val number2 = Random.nextInt(-numLimit, numLimit)

        val expressionType = randomExpression()
        val expression = Expression(expressionType, number1, number2)

        findViewById<TextView>(R.id.expressionTextView).text = expression.getQuestion()
        answer = expression.getAnswer()

        expressions[0] = answer
        for (i in 1..3) {
            expressions[i] = answer + Random.nextInt(-numLimit, numLimit)
        }
        expressions.shuffle()
    }

    private fun randomExpression(): ExpressionType {
        val expressionNum = Random.nextInt(ExpressionType.values().size)
        val expression = ExpressionType.values().firstOrNull { it.ordinal == expressionNum }

        return expression!!
    }
}