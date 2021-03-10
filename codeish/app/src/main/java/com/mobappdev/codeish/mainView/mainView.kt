package com.mobappdev.codeish.mainView

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mobappdev.codeish.MainActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter1.gettingstarted
import com.mobappdev.codeish.mainView.data.Topic
import com.mobappdev.codeish.mainView.data.TopicList
import com.mobappdev.codeish.profile.Profile
import com.mobappdev.codeish.settings.Settings
import com.mobappdev.codeish.shop.Shop
import com.mobappdev.codeish.userdata.userSpecificData
import www.sanju.motiontoast.MotionToast

class mainView : AppCompatActivity() {

    lateinit var allTopics : ArrayList<Topic>
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topics_recycler)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerlayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navview : NavigationView = findViewById(R.id.navview)

        navview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> {
                    val intent = Intent(this, Shop::class.java)
                    startActivity(intent)
                }
                R.id.item2 -> {
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                }
                R.id.item3 -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        createTopicsList()

        var rvTopic = findViewById<RecyclerView>(R.id.rvTopic)
        rvTopic?.layoutManager = LinearLayoutManager(this)
        val adapter = TopicList(allTopics, this)
        rvTopic?.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createTopicsList(){
         allTopics =
            arrayListOf<Topic>(
                Topic(resources.getString(R.string.header1),
                    "test",
                    "Computer sind überall, nicht nur im Telefon und Laptops.",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header2),
                    "test1",
                    "Technologien und Magie!",
                    "imgage",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header3),
                    "test",
                    "Was ist Hardware? Dieses englische Wort beschreibt alle Dinge " +
                            "die man in echt bei einem Computer berühren kann!",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header4),
                    "test",
                    "Weißt du schon wie ein Computer Probleme löst?",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header5),
                    "test",
                    "Hast du schon mal von dem Wort DATEN gehört?",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header6),
                    "test",
                    "Ich möchte das Programmieren gerne selber ausprobieren!",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header7),
                    "test",
                    "Hast du gewusst dass auch viele verschiedene Sprachen gibt, " +
                            "um einen Computer Anweisungen zu geben?",
                    "img",
                    Intent(this, gettingstarted::class.java)),
                Topic(resources.getString(R.string.header8),
                    "test",
                    "Kennst du dich mit Snapchat, " +
                            "Instagram und Tiktok aus?",
                    "img",
                    Intent(this, gettingstarted::class.java)))
    }
}