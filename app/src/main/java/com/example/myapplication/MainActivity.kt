package com.example.myapplication

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager


class MainActivity :BaseActivity() {
    var sNavigationDrawer: SNavigationDrawer? = null
     var action: String? = null
    var isDarkTheme: Boolean = false
    var navDrawerRunnable:Handler = Handler()
    var fragment: Fragment? = null
     var runnable: Runnable? = null
      val navigateLibrary = Runnable {
          var fragment1:Fragment = BlankFragment()
          var transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
          transaction.replace(R.id.frameLayout,fragment1).commitAllowingStateLoss()


    }
    val navigateNowplaying = Runnable {
        var fragment2:Fragment = FlankFragment()
        var transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment2).commitAllowingStateLoss()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        action = intent.action

        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        sNavigationDrawer = findViewById(R.id.navigationDrawer)
        isReadStoragePermissionGranted()
        isWriteStoragePermissionGranted()
        var menuItem:ArrayList<MenuItem> = ArrayList<MenuItem>()
        menuItem.add(MenuItem("NOW PLAYING",R.drawable.ic_launcher_background))
        menuItem.add(MenuItem("SONGS LIST",R.drawable.abc_btn_check_to_on_mtrl_015))
        menuItem.add(MenuItem("ALBUMS",R.drawable.abc_btn_check_to_on_mtrl_015))
        sNavigationDrawer!!.setMenuItemList(menuItem)
        navDrawerRunnable.postDelayed({
 sNavigationDrawer!!.setOnMenuItemClickListener(object:SNavigationDrawer.OnMenuItemClickListener{
     override fun onMenuItemClicked(p0: Int) {
         when (p0) {
             0 -> {
            runnable = navigateNowplaying
             }
             1 -> {
                 runnable = navigateLibrary

             }
             2 -> {

             }
             3 -> {

             }
         }
         if (runnable != null) {

             var handler = Handler()
             handler.postDelayed({ runnable!!.run() }, 350)
         }
     }


 } )
        }, 700)



    }
    fun isReadStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true
            } else {


                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
                return false
            }
        } else {

            return true
        }
    }

    fun isWriteStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true
            } else {


                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                return false
            }
        } else {

            return true
        }
    }


}
