package com.example.findbook.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.findbook.fragment.ProfileFragment
import com.example.findbook.R
import com.example.findbook.fragment.DashbordFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    var titlename: String? = "User"
    lateinit var shardpreference : SharedPreferences

    lateinit var drawerlayout : DrawerLayout
    lateinit var coordinatolayout: CoordinatorLayout
    lateinit var  toolbar : androidx.appcompat.widget.Toolbar
    lateinit var framelayout : FrameLayout
    lateinit var navigationview : NavigationView

    var previousMenuItem: MenuItem? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shardpreference = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)

        titlename = shardpreference.getString("Title", "Welcome")



        drawerlayout = findViewById(R.id.drawerlayout)
        coordinatolayout = findViewById(R.id.coordinatorlayout)
        toolbar = findViewById(R.id.toolbar)
        framelayout = findViewById(R.id.framelayout)
        navigationview = findViewById(R.id.navvigationview)

        setUpToolbar()
        openDashboard()



        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerlayout,
            R.string.open_drawer, R.string.close_drawer
        )

        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationview.setNavigationItemSelectedListener {

            if (previousMenuItem !=null){
                previousMenuItem?.isChecked = false
        }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it


            when(it.itemId){
                R.id.dashbord ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, DashbordFragment())
                        .addToBackStack("Home")
                        .commit()
                    supportActionBar?.title = "Home"
                    drawerlayout.closeDrawers()

                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, ProfileFragment())
                        .addToBackStack("Profile")
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerlayout.closeDrawers()
                }


            }
             return@setNavigationItemSelectedListener true
        }

    }


    fun setUpToolbar(){

        setSupportActionBar(toolbar)
        supportActionBar?.title = titlename
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home){

            drawerlayout.openDrawer(GravityCompat.START)

        }

        return  super.onOptionsItemSelected(item)
    }

    // For OpenDashbordScreenFirst

      fun openDashboard(){
          val fragment = DashbordFragment()
          val transaction = supportFragmentManager.beginTransaction()
          transaction.replace(R.id.framelayout, fragment)
          transaction.commit()
          supportActionBar?.title = "Home"
          navigationview.setCheckedItem(R.id.dashbord)
      }

    override fun onBackPressed() {
        var frag = supportFragmentManager.findFragmentById(R.id.framelayout)

        when(frag){
            !is DashbordFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}