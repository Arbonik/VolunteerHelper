package com.arbonik.helper

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.arbonik.helper.auth.SharedPreferenceUser
import com.arbonik.helper.auth.SignInActivity
import com.arbonik.helper.auth.USER_CATEGORY
import com.arbonik.helper.notifications.NotificationService
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var sharedPreferenceUser = SharedPreferenceUser()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancelAll()
      //  this.startService(Intent(this, NotificationService::class.java))
        if (!sharedPreferenceUser.checkAuth()){
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val inflater = navController.navInflater
        val graph = when (SharedPreferenceUser.currentUser?.category){
            USER_CATEGORY.VETERAN ->
            {
                navView.inflateMenu(R.menu.bottom_nav_menu)
                inflater.inflate(R.navigation.mobile_navigation)
            }
            USER_CATEGORY.VOLONTEER ->
            {
                navView.inflateMenu(R.menu.bottom_nav_menu_vol)
                inflater.inflate(R.navigation.mobile_navigation_volonteer)
            }
            USER_CATEGORY.ADMIN -> TODO()
            null -> TODO()
        }
        navController.graph = graph
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.logout -> {
                    sharedPreferenceUser.loginOut()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                }
            }
        return super.onOptionsItemSelected(item)
    }
}
