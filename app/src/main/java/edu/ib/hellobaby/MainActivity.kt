package edu.ib.hellobaby


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import edu.ib.hellobaby.notifications.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

const val DEFAULT_REMINDER_HOUR = 21
const val DEFAULT_REMINDER_MINUTE = 37

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
        viewModel.scheduleReminderNotification(DEFAULT_REMINDER_HOUR, DEFAULT_REMINDER_MINUTE)

        baby_btn.setOnClickListener {
            startActivity(Intent(this, BabyLoading::class.java))
        }

        book_btn.setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }

        cal_del_btn.setOnClickListener {
            startActivity(Intent(this, DeleteEvents::class.java))
        }

        srch_btn.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        sett_btn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    //Function for asking for permission to write and read Google Calendar
    public fun readPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

    public fun writePermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

    public fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()
        if (!readPermission()) {
            permissionsToRequest.add(Manifest.permission.READ_CALENDAR)
        }
        if (!writePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_CALENDAR)
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

    //Function for asking for permission to write and read Google Calendar
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // Log.d("PermisionRequest", "${permissions[i]} granted")
                }
            }
        }
    }

/*    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }*/
}
