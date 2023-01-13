package edu.ib.hellobaby.notifications

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


var SHARED_PREF_REMINDER_HOUR = "21"
var SHARED_PREF_REMINDER_MINUTE = "37"

class PreferenceStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPref = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

    /**
     * @return the hour of the day in which the reminder should be shown, default is 22, if canceled -1
     * @return the minute at which the reminder should be shown, default is 0, if canceled -1
     */

    fun getReminderTime(): Pair<Int, Int> {
        return Pair(
            sharedPref.getInt(SHARED_PREF_REMINDER_HOUR, 21),
            sharedPref.getInt(SHARED_PREF_REMINDER_MINUTE, 37)
        )
    }

    fun setReminderTime(hour: Int, minute: Int) {
        sharedPref.edit {
            putInt(SHARED_PREF_REMINDER_HOUR, hour)
            putInt(SHARED_PREF_REMINDER_MINUTE, minute)
        }
    }

    fun cancelReminder() {
        sharedPref.edit {
            putInt(SHARED_PREF_REMINDER_HOUR, -1)
            putInt(SHARED_PREF_REMINDER_MINUTE, -1)
        }
    }


    fun isDefaultReminderSet() = sharedPref.getBoolean("is_default_reminder_set", false)

    fun saveDefaultReminderIsSet() {
        sharedPref.edit { putBoolean("is_default_reminder_set", true) }
    }

}