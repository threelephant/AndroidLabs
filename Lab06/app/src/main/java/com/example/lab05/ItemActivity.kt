package com.example.lab05

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File

class ItemActivity : AppCompatActivity() {
    private var index = 0L
    private lateinit var item: Item
    private var currentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val intent = intent
        index = intent?.getLongExtra("index", -1) ?: -1
        item = intent?.getParcelableExtra("item") ?: Item()

        val editTitle = findViewById<EditText>(R.id.title)
        editTitle.setText(item.title)
        val editKind = findViewById<EditText>(R.id.kind)
        editKind.setText(item.kind)
        val editPrice = findViewById<EditText>(R.id.price)
        editPrice.setText(item.price.toString())
        val editWeight = findViewById<EditText>(R.id.weight)
        editWeight.setText(item.weight.toString())
        val editYear = findViewById<EditText>(R.id.year)
        editYear.setText(item.yearCreation.toString())

        if (item.photo != "") {
            val bmp = BitmapFactory.decodeFile((item.photo))
            findViewById<ImageView>(R.id.ivPhoto).setImageBitmap(bmp)
            currentPhotoPath = item.photo
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item.itemId == R.id.action_save) {
            this.item.title = findViewById<EditText>(R.id.title).
                text.toString()
            this.item.kind = findViewById<EditText>(R.id.kind).
                text.toString()
            this.item.price = findViewById<EditText>(R.id.price).
                text.toString().toDouble()
            this.item.weight = findViewById<EditText>(R.id.weight).
                text.toString().toDouble()
            this.item.yearCreation = findViewById<EditText>(R.id.year).
                text.toString().toInt()
            this.item.photo = currentPhotoPath

            val intent = Intent()
            intent.putExtra("index", index)
            intent.putExtra("item", this.item)
            setResult(Activity.RESULT_OK, intent)

            finish()
            return true
        }

        if (item.itemId == R.id.action_photo) {
            val photoFile = File.createTempFile(
                "photo",
                ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

            currentPhotoPath = photoFile.absolutePath

            val photoURI = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID,
                photoFile
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, 0)

            return true
        }

        if (item.itemId == R.id.action_delete) {
            val dbConnection = SQLiteHelper(this).readableDatabase
            dbConnection.delete("items", "id=?", arrayOf(index.toString()))

            val intent = Intent()
            intent.putExtra("index", index)
            setResult(this.RESULT_OK_ITEM_DELETED, intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            val bmp = BitmapFactory.decodeFile(currentPhotoPath)
            val ivPhoto = findViewById<ImageView>(R.id.ivPhoto)
            ivPhoto.setImageBitmap(bmp)
        } else {
            currentPhotoPath = ""
        }
    }
}