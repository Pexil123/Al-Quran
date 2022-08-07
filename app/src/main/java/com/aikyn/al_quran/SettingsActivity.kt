package com.aikyn.al_quran

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*

class SettingsActivity : AppCompatActivity() {
    var pref: SharedPreferences? = null
    var spinner: Spinner? = null
    var reciter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)
        reciter = pref?.getInt("reciter", 0)!!
        spinner = findViewById(R.id.spinner)

        val reciters = resources.getStringArray(R.array.reciters)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, reciters)
        spinner?.setAdapter(arrayAdapter)

        spinner?.setSelection(reciter)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                when (pos) {
                    0 -> {
                        reciter = 0
                        saveReciter(reciter)
                        Toast.makeText(this@SettingsActivity, spinner?.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                    }

                    1 -> {
                        reciter = 1
                        saveReciter(reciter)
                        Toast.makeText(this@SettingsActivity, spinner?.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                    }

                    2 -> {
                        reciter = 2
                        saveReciter(reciter)
                        Toast.makeText(this@SettingsActivity, spinner?.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                    }

                    3 -> {
                        reciter = 3
                        saveReciter(reciter)
                        Toast.makeText(this@SettingsActivity, spinner?.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

    }

    fun saveReciter(num: Int) {
        val editor = pref?.edit()
        editor?.putInt("reciter", num)
        editor?.apply()
    }

}