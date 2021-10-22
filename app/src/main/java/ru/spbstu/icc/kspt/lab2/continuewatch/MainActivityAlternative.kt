package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityAlternative : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private var countAllowed: Boolean = true
    lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences

    private var backgroundThread = Thread {
        while (true) {
            if (countAllowed) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                }
                Thread.sleep(1000)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        backgroundThread.start()
    }


    override fun onResume() {
        super.onResume()
        countAllowed = true
        secondsElapsed = sharedPref.getInt(
            getString(R.string.saved_seconds_key),
            resources.getInteger(R.integer.saved_seconds_default_key)
        )
    }

    override fun onStop() {
        super.onStop()
        with (sharedPref.edit()) {
            putInt(getString(R.string.saved_seconds_key), secondsElapsed)
            apply()
        }
    }

    override fun onPause() {
        super.onPause()
        countAllowed = false
    }


}
