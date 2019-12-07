package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import com.example.android.dessertpusher.DessertTimer
import kotlinx.android.synthetic.main.nav_header.*
import timber.log.Timber
import android.provider.MediaStore
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment.getExternalStorageDirectory
import java.io.File

//import sun.jvm.hotspot.utilities.IntArray




class Homepage : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, ongoingFragment.OnFragmentInteractionListener, trashcanFragment.OnFragmentInteractionListener
, finishedFragment.OnFragmentInteractionListener, CreateNewRemainder.OnFragmentInteractionListener, ongoingFragment.OnRecyclerInteractionListener, memoDetailFragment.OnFragmentInteractionListener {


    private var mTwo = false
    val animals: ArrayList<String> = ArrayList()
    private lateinit var dessertTimer: DessertTimer
    val CAMERA_REQUEST_CODE=0
    val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE= 1001;
    private var position = -1
    private var reminder:ReminderData? = null

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

    override fun onStart() {
        super.onStart()
        Timber.i("onStart Called")
        Log.i("Homepage", "onStart Called")
        dessertTimer.startTimer()
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
        dessertTimer.stopTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dessertTimer = DessertTimer(this.lifecycle)

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
                abc.setBackgroundResource(R.drawable.galaxy)
            }
            R.id.nav_custom2-> {
                navView.setBackgroundColor(getResources().getColor(R.color.gray))
                abc.setBackgroundResource(R.drawable.add)
            }
            R.id.nav_camera-> {
                val Intent3 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(Intent3.resolveActivity(packageManager) != null) {
                    startActivityForResult(Intent3, CAMERA_REQUEST_CODE)
                }
            }
            R.id.nav_gallery-> {
                val Intent2 = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(Intent2, IMAGE_PICK_CODE)
            }

        }
        homepage.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    val Intent2 = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(Intent2, IMAGE_PICK_CODE)
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            circular1.setImageURI(data?.data)
        }
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(resultCode== Activity.RESULT_OK && data !=null){
                    circular1.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this,"Unrecognized request code",Toast.LENGTH_SHORT)
            }
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }


    override fun onItemClicked(rememo: ReminderData) {
        this.reminder = rememo

        supportFragmentManager.beginTransaction().replace(R.id.meContainer,
            memoDetailFragment.newInstance(reminder!!)).addToBackStack(null).commit()
    }




    fun onRecyclerInteraction(reminder:ReminderData, position: Int) {
        this.position = position
        this.reminder = reminder
        loadDetailFragment()
    }


    private fun loadDetailFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.meContainer,
            memoDetailFragment.newInstance(reminder!!)).addToBackStack(null).commit()
    }

}
