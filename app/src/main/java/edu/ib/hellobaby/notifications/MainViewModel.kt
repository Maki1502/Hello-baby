package edu.ib.hellobaby.notifications

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val prefStore: PreferenceStore,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    fun scheduleReminderNotification(hourOfDay: Int, minute: Int) {
        prefStore.setReminderTime(hourOfDay, minute)
        notificationScheduler.scheduleReminderNotification(hourOfDay, minute)
    }

    fun getReminderTime() = prefStore.getReminderTime()

    fun cancelReminderNotification() {
        prefStore.cancelReminder()
        notificationScheduler.cancelAll()
    }

}