package ru.spbstu.icc.kspt.lab2.continuewatch.ExecutionService

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutorInstance: Application() {
    val executor: ExecutorService = Executors.newFixedThreadPool(1)
}
