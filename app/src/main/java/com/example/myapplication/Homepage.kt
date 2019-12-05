package com.example.myapplication

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_homepage.*
import layout.PagerAdapter
import com.google.android.material.tabs.TabLayout
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Homepage : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, ongoingFragment.OnFragmentInteractionListener, trashcanFragment.OnFragmentInteractionListener
, finishedFragment.OnFragmentInteractionListener, CreateNewRemainder.OnFragmentInteractionListener {

    val animals: ArrayList<String> = ArrayList()

    private fun addAnimals() {
        animals.add("To Swim")
        animals.add("Finish Homework for CIS400")
        animals.add("Team Work in CIS453")
        animals.add("Read Novel")
        animals.add("Learn Italian")
    }

    fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fg2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, homepage, toolbar, R.string.open_nav, R.string.close_nav)
        homepage.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager1.adapter = PagerAdapter(supportFragmentManager)
        //myPageTransformer
        viewPager1.currentItem = 0

        btnPanel.setupWithViewPager(viewPager1)

            btnPanel.getTabAt(0)?.setText("Ongoing")
        btnPanel.getTabAt(1)?.setText("Finished")
        btnPanel.getTabAt(2)?.setText("Trashcan")

        val fragment = CreateNewRemainder()
        fab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.meContainer, fragment).addToBackStack(null).commit()
        }

        //var f: ongoingFragment = ongoingFragment()
        //replaceFragment(f)

        /*
        addAnimals()
        rview1.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)
        val myAdapter = MyAnimalAdapter(animals, this)
        //myAdapter.setMyItemClickListener(this)
        rview1.adapter = myAdapter
        */

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // handler navigation menu item selection!
        when(item.itemId){
            R.id.nav_ongoing -> {
                    val intent = Intent(this, Homepage::class.java)
                    intent.putExtra("action", 0)
                    startActivity(intent) }
            R.id.nav_finished -> {
                val intent = Intent(this, finished::class.java)
                intent.putExtra("action", 0)
                startActivity(intent) }
            R.id.nav_trash -> {
                val intent = Intent(this, trashcan::class.java)
                intent.putExtra("action", 1)
                startActivity(intent) }
            R.id.nav_share -> {

            }
            R.id.nav_custom1-> {
                navView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
            }
            R.id.nav_custom2-> {
                navView.setBackgroundColor(getResources().getColor(R.color.gray))
            }

        }
        homepage.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onBackPressed() {
        if(homepage.isDrawerOpen(GravityCompat.START)){
            homepage.closeDrawer(GravityCompat.START)
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
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
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
