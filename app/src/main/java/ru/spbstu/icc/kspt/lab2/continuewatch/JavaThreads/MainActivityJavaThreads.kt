package ru.spbstu.icc.kspt.lab2.continuewatch.JavaThreads

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.R

class MainActivityJavaThreads : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var backgroundThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.saved_seconds_key), secondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(getString(R.string.saved_seconds_key))
    }

    override fun onStart() {
        super.onStart()

        backgroundThread = Thread {
            try {
                var start = System.currentTimeMillis()
                var delta: Long = 0;
                while (!Thread.currentThread().isInterrupted) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text =
                            getString(R.string.seconds_elapsed, secondsElapsed++)
                    }
                    Thread.sleep(1000 + delta)
                    delta = start + 1000 - System.currentTimeMillis()
                    start += 1000
                }
                Thread.currentThread().interrupt()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
        backgroundThread.start()

    }

    override fun onStop() {
        super.onStop()
        Log.d("Counting Thread", "Thread interrupted")
        backgroundThread.interrupt()

    }

}
