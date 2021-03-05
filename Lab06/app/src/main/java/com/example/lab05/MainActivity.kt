package com.example.lab05

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private var items = ArrayList<Item>()
    private lateinit var con: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = SQLiteHelper(this)
        con = db.readableDatabase
        getItems()

        val listView: ListView = findViewById(R.id.listItems)
        listView.adapter = ItemAdapter(this, items)

        listView.setOnItemClickListener { adapterView: AdapterView<*>,
                                      view1: View, i: Int, l: Long ->
            val intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("index", items[i].id)
            intent.putExtra("item", items[i])
            startActivityForResult(intent, 0)
        }

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, ItemActivity::class.java)
            startActivityForResult(intent, 0)
        }
    }

    private fun getItems() {
        val cursor = con.query(
            "items",
            arrayOf("id", "kind", "title", "price", "weight", "yearCreation", "photo"),
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val item = Item()
            item.id = cursor.getLong(0)
            item.kind = cursor.getString(1)
            item.title = cursor.getString(2)
            item.price = cursor.getDouble(3)
            item.weight = cursor.getDouble(4)
            item.yearCreation = cursor.getInt(5)
            item.photo = cursor.getString(6)
            items.add(item)
            cursor.moveToNext()
        }
        cursor.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val index: Long = data?.getLongExtra("index", -1L) ?: -1L
            val item: Item = data?.getParcelableExtra("item") ?: Item()

            val cv = ContentValues()
            //cv.put("id", item.id)
            cv.put("kind", item.kind)
            cv.put("title", item.title)
            cv.put("price", item.price)
            cv.put("weight", item.weight)
            cv.put("yearCreation", item.yearCreation)
            cv.put("photo", item.photo)

            if (index != -1L) {
                val position = items.indexOfFirst { it.id == index.toLong() }
                items[position] = item
                con.update("items", cv, "id=?", arrayOf(index.toString()))
            } else {
                items.add(item)
                con.insert("items", null, cv)
            }

            val listView: ListView = findViewById(R.id.listItems)
            (listView.adapter as ItemAdapter).notifyDataSetChanged()
        } else if (resultCode == this.RESULT_OK_ITEM_DELETED) {
            val id: Long = data?.getLongExtra("index", -1) ?: -1
            val position: Int = items.indexOfFirst { it.id == id }
            items.removeAt(position)
            val listView: ListView = findViewById(R.id.listItems)
            (listView.adapter as ItemAdapter).notifyDataSetChanged()
        }
    }
}