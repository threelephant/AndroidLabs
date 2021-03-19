package com.example.lab08

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class RightFragment(Operation: Int = 0, Difficulty: Int) : Fragment() {

    constructor() : this(0, 1)

    private lateinit var mainContext: Context

    private val operation: Int = Operation
    private var difficulty: Int = Difficulty
    private var answersList: ArrayList<Int> = arrayListOf(0, 0, 0, 0)
    private var correctAnswer: Int = 0

    private fun incrementCountCorrectAnswers() {
        (mainContext as MainActivity).incrementCountCorrectAnswers()
    }

    private fun incrementCountAnswers() {
        (mainContext as MainActivity).incrementCountAnswers()
    }

    private fun View.checkAnswer(answer: Int) {
        if (answer == correctAnswer) {
            incrementCountCorrectAnswers()
        }
        else {
            incrementCountAnswers()
        }
    }

    private fun View.generateExpression() {
        val from: Int = -10 * difficulty
        val until: Int = 10 * difficulty
        val n1: Int = Random.nextInt(from, until)
        val n2: Int = Random.nextInt(from, until)
        val expressionView: TextView = findViewById(R.id.text_view_id)

        when (operation) {
            0 -> {
                expressionView.text = "$n1 + $n2"
                correctAnswer = n1 + n2
            }
            1 -> {
                expressionView.text = "$n1 - $n2"
                correctAnswer = n1 - n2
            }
            2 -> {
                expressionView.text = "$n1 * $n2"
                correctAnswer = n1 * n2
            }
            else -> {
                print("Error")
            }
        }

        answersList[0] = correctAnswer

        for (i in 1..3) {
            do {
                answersList[i] = correctAnswer + Random.nextInt(-10 * difficulty,
                        10 * difficulty)
            } while (answersList[i] == correctAnswer)
        }

        answersList.shuffle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt("correctAnswer", correctAnswer)
            putIntegerArrayList("answersList", answersList)
        }


        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                correctAnswer = getInt("correctAnswer")
                answersList = getIntegerArrayList("answersList") as ArrayList<Int>
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(
            R.layout.fragment_right,
            container, false)

        view.generateExpression()


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.adapter = AnswerAdapter(answersList) { position: Int ->
            view.checkAnswer(answersList[position])
            view.generateExpression()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        return view
    }
}