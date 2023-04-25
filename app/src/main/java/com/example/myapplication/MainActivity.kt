package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            addName.setOnClickListener {

                val db = DBHelper(this@MainActivity, null)

                val name = nameEditText.text.toString()
                val age = ageEditText.text.toString()

                db.addName(name, age)

                Toast.makeText(this@MainActivity, "$name added to db", Toast.LENGTH_SHORT).show()

                nameEditText.text.clear()
                ageEditText.text.clear()
            }

            printName.setOnClickListener {

                Name.text = "Name\n"
                Age.text = "Age\n"

                val db = DBHelper(this@MainActivity, null)

                val cursor = db.readName()

                cursor!!.moveToFirst()
                var count = 0
                if (cursor.moveToFirst()){
                    count = cursor.getInt(0)
                }
                if (count == 0){
                    return@setOnClickListener
                }
                Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.COL_NAME)) + "\n")
                Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.COL_AGE)) + "\n")

                while (cursor.moveToNext()){
                    Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.COL_NAME)) + "\n")
                    Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.COL_AGE)) + "\n")
                }

                cursor.close()
            }

            deleteName.setOnClickListener {
                val dbHelper = DBHelper(this@MainActivity,null)
                val db = dbHelper.writableDatabase

                db.delete(DBHelper.TABLE_NAME,null,null)

                Name.text = "Name\n"
                Age.text = "Age\n"

                db.close()

            }
        }


    }
}