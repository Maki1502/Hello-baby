package edu.ib.hellobaby.notifications

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationScheduler @Inject constructor(@ApplicationContext private val context: Context) {

    /**
     * @param hourOfDay the hour at which daily reminder notification should appear [0-23]
     * @param minute the minute at which daily reminder notification should appear [0-59]
     */
    fun scheduleReminderNotification(hourOfDay: Int, minute: Int) {
        val msg = "msg"
        Log.v("Reminder scheduling request received for $hourOfDay:$minute", msg)
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        Log.v("Scheduling reminder notification for ${target.timeInMillis - System.currentTimeMillis()} ms from now", msg)

        val notificationRequest = PeriodicWorkRequestBuilder<ReminderNotificationWorker>(1, TimeUnit.DAYS)
            .addTag(TAG_REMINDER_WORKER)
            .setInitialDelay(target.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS).build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "reminder_notification_work",
                ExistingPeriodicWorkPolicy.REPLACE,
                notificationRequest
            )
    }

    fun cancelAll() {
        WorkManager.getInstance(context).cancelAllWorkByTag(TAG_REMINDER_WORKER)
    }
}