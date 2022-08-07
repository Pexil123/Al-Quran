package com.aikyn.al_quran

import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.net.URL

class AyahAudio : AppCompatActivity() {
    var ayahTextView: TextView? = null
    var numberOfAyahView: TextView? = null
    var mp: MediaPlayer? = null
    var playbtn: Button? = null
    var seekBar: SeekBar? = null
    var noInet: TextView? = null
    var surahName: TextView? = null
    var lazy: ConstraintLayout? = null

    var numberOfAyah = ""
    var ayahText = ""
    var numberOfSurah = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayah_audio)
        ayahTextView = findViewById(R.id.ayah_audio_text)
        numberOfAyahView = findViewById(R.id.ayah_audio_number)
        playbtn = findViewById(R.id.ayah_play)
        seekBar = findViewById(R.id.ayah_seekBar)
        noInet = findViewById(R.id.noInet)
        surahName = findViewById(R.id.ayah_audio_surah_name)
        lazy = findViewById(R.id.lazy)

        val i = intent

        numberOfAyah = i.getStringExtra("nAyah")!!
        ayahText = i.getStringExtra("ayah")!!
        numberOfSurah = i.getStringExtra("nSurah")!!

        ayahTextView?.text = ayahText
        numberOfAyahView?.text = numberOfAyah

        getAudio().execute(numberOfSurah)

        seekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mp?.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        playbtn?.setOnClickListener {
            if (mp?.isPlaying == true) {
                mp?.pause()
                playbtn?.setBackgroundResource(R.drawable.ayah_play)
            } else {
                mp?.start()
                playbtn?.setBackgroundResource(R.drawable.ayah_pause)
            }
        }


    }

    inner class getAudio: AsyncTask<String, Void, String>(){
        var result = ""
        override fun doInBackground(vararg p0: String?): String {
            var num = p0[0]
            while (true) {
                try {
                    result = URL("https://api.alquran.cloud/v1/surah/$num/ar.alafasy").readText(Charsets.UTF_8)
                    break
                } catch (e: Exception) {
                    e.printStackTrace()
                    noInet?.visibility = View.VISIBLE
                    continue
                }
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val jsonObject = JSONObject(result)
            val aya = jsonObject.getJSONObject("data").getJSONArray("ayahs")
                .getJSONObject(numberOfAyah!!.toInt() - 1).getString("audio")

            val name = jsonObject.getJSONObject("data").getString("englishName")
            surahName?.text = name

            val ayahAudio = Uri.parse(aya)

            mp = MediaPlayer.create(this@AyahAudio, ayahAudio)
            initializeSeekBar()
            lazy?.visibility = View.GONE
        }
    }

    fun initializeSeekBar () {
        seekBar?.max = mp!!.duration
        val dur = mp!!.duration - 100

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    seekBar?.progress = mp!!.currentPosition
                    if (mp!!.currentPosition > dur) {
                        mp?.pause()
                        mp!!.seekTo(0)
                        playbtn?.setBackgroundResource(R.drawable.ayah_play)
                    }
                    handler.postDelayed(this, 16)
                } catch (e: Exception) {
                    seekBar?.progress = 0
                }
            }
        }, 0)
    }

    override fun onBackPressed() {
        try {
            mp?.stop()
            mp?.reset()
            mp?.release()
        } catch (e: Exception){}

        finish()

    }

}