package edu.ib.hellobaby.notifications

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

var TAG_REMINDER_WORKER = "Jajco"

class ReminderNotificationWorker(private val appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {

    private val msg = "msg"

    override fun doWork(): Result {
        NotificationHandler.createReminderNotification(appContext)
        Log.v("dziala", msg)
        return Result.success()

    }
}