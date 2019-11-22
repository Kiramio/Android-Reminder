package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    val animals: ArrayList<String> = ArrayList()

    private fun addAnimals() {
        animals.add("To Swim")
        animals.add("Finish Homework for CIS400")
        animals.add("Team Work in CIS453")
        animals.add("Read Novel")
        animals.add("Learn Italian")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        addAnimals()
        rview1.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)
        val myAdapter = MyAnimalAdapter(animals, this)
        //myAdapter.setMyItemClickListener(this)
        rview1.adapter = myAdapter


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // handler navigation menu item selection!
        when(item.itemId){
            R.id.nav_ongoing -> {
                supportFragmentManager.beginTransaction().add(R.id.meContainer, ongoingFragment()).commit() }
            R.id.nav_finished -> {
                val intent = Intent(this, finished::class.java)
                intent.putExtra("action", 0)
                startActivity(intent) }
            R.id.nav_trash -> {
                val intent = Intent(this, trashcan::class.java)
                intent.putExtra("action", 1)
                startActivity(intent) }
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(mainAct.isDrawerOpen(GravityCompat.START)){
            mainAct.closeDrawer(GravityCompat.START)
        }
        else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.ongo-> {
                supportFragmentManager.beginTransaction().add(R.id.meContainer, ongoingFragment()).commit()
                return true
            }
            R.id.finish-> {
                val intent = Intent(this, finished::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                val intent = Intent(this, trashcan::class.java)
                startActivity(intent)
                return true
            }
        }
    }
}
