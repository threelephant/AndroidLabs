package com.example.lab08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), OnDataListener {

    private var isTwoPane = false

    private var difficulty: Int = 1
    private var countAnswers: Int = 0
    private var countCorrectAnswers: Int = 0
    private var correctPercent: Float = 0.0F

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt("countAnswers", countAnswers)
            putInt("countCorrectAnswers", countCorrectAnswers)
            putFloat("correctPercent", correctPercent)
            putInt("difficulty", difficulty)
        }
        super.onSaveInstanceState(outState)
    }

    fun incrementCountCorrectAnswers() {
        countCorrectAnswers++
        val correctView: TextView = findViewById(R.id.correct_view_id)
        correctView.text = "Верно: $countCorrectAnswers"
        incrementCountAnswers()
    }

    fun incrementCountAnswers() {
        countAnswers++
        val wrongView: TextView = findViewById(R.id.wrong_view_id)
        val wrongAnswers = countAnswers - countCorrectAnswers
        wrongView.text = "Неверно: $wrongAnswers"
        correctPercent = countCorrectAnswers.toFloat() / countAnswers.toFloat()

        if (countCorrectAnswers > 10 && correctPercent > 0.7) {
            difficulty = 35
        }
        else if (countCorrectAnswers > 5 && correctPercent > 0.3) {
            difficulty = 15
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                countAnswers = getInt("countAnswers")
                countCorrectAnswers = getInt("countCorrectAnswers")
                correctPercent = getFloat("correctPercent")
                difficulty = getInt("difficulty")
            }
        }

        val correctView: TextView = findViewById(R.id.correct_view_id)
        correctView.text = "Верно: $countCorrectAnswers"

        val wrongView: TextView = findViewById(R.id.wrong_view_id)
        val wrongAnswers = countAnswers - countCorrectAnswers
        wrongView.text = "Неверно: $wrongAnswers"

        isTwoPane = findViewById<View>(R.id.frame_left) != null

        if (isTwoPane) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_left, LeftFragment())
                .add(R.id.frame_right, RightFragment(0, difficulty))
                .commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LeftFragment())
                .commit()
        }
    }

    override fun onData(Data: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                if (isTwoPane)
                    R.id.frame_right
                else
                    R.id.container,
                RightFragment(Data, difficulty)
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }
}