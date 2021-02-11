package com.example.lab02

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
//    private var viewsCount = 0
    private lateinit var randomViewNumbers: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            randomViewNumbers = ArrayList(savedInstanceState
                    .getIntArray("randomCounts")?.toList())

            for (count in randomViewNumbers) {
                addTextView(count)
            }

            findViewById<ScrollView>(R.id.scrollView)
                    .verticalScrollbarPosition = savedInstanceState.getInt("scrollPosY")
        } else {
            randomViewNumbers = mutableListOf()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Log.i("MyInfo", "Метод onResume")
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putIntArray("randomCounts", randomViewNumbers.toIntArray())

        val scrollPosY = findViewById<ScrollView>(R.id.scrollView).verticalScrollbarPosition
        outState.putInt("scrollPosY", scrollPosY)
    }

    fun buttonAddClick(view: View) {
        val number: Int = Random.nextInt(0, 1000)

        randomViewNumbers.add(number)
        addTextView(number)
    }

    private fun addTextView(number: Int) {
        val textView = TextView(this)
        textView.text = number.toString()
        textView.textSize = 24f

        val container = findViewById<LinearLayout>(R.id.innerContainer)
        container.addView(textView)
    }

//    private fun addButton() {
//        val button = Button(this)
//        button.text = "Кнопка №${ viewsCount }"
//        button.tag = viewsCount
//        button.setOnClickListener {
//            val toast = Toast.makeText(this,
//                        "Нажата кнопка ${ it.tag }",
//                        Toast.LENGTH_SHORT)
//            toast.show()
//        }
//
//        val container: ViewGroup = findViewById(R.id.innerContainer)
//        container.addView(button)
//    }
}