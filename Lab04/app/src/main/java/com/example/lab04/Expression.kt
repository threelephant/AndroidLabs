package com.example.lab04

class Expression(var expressionType: ExpressionType,
                 var number1: Int,
                 var number2: Int) {

    fun getQuestion(): String {
        return when(expressionType) {
            ExpressionType.Sum -> "$number1 + $number2"
            ExpressionType.Minus -> "$number1 - $number2"
            ExpressionType.Multiply -> "$number1 * $number2"
        }
    }

    fun getAnswer(): Int {
        return when(expressionType) {
            ExpressionType.Sum -> number1 + number2
            ExpressionType.Minus -> number1 - number2
            ExpressionType.Multiply -> number1 * number2
        }
    }
}