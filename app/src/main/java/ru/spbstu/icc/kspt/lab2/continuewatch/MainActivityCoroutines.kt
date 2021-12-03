package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class MainActivityCoroutines : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)

        lifecycleScope.launch {
            Thread.currentThread().name = "Counting Thread"
            Log.d("Counting", "Coroutine started")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var start = System.currentTimeMillis()
                var delta: Long = 0
                while (isActive) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text =
                            getString(R.string.seconds_elapsed, secondsElapsed++)
                    }
                    delay(1000 + delta)
                    delta = start + 1000 - System.currentTimeMillis()
                    start += 1000
                    Log.d(
                        "Counting",
                        "Seconds counted:${secondsElapsed} Err sum, ms:${System.currentTimeMillis() - start}"
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.saved_seconds_key), secondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(getString(R.string.saved_seconds_key))
    }
}
