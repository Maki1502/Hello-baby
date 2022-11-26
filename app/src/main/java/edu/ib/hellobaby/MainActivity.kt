package edu.ib.hellobaby


import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_search -> {
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_add -> {
                moveToFragment(CalFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_settings -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        readPermission()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(CalFragment())
    }

    //Function for asking for permission to write and read Google Calendar
    private fun readPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

    private fun writePermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermissions() {
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

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }
}