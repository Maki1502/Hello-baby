package edu.ib.hellobaby


import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        readPermission()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}