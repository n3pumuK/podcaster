package de.exercicse.jrossbach.podcast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import de.exercicse.jrossbach.podcast.search.SearchPodcastFragment

class MainActivity : AppCompatActivity() {

    private lateinit var container : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        setUpFragments()
    }

    private fun setUpFragments() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = SearchPodcastFragment.newInstance()
        container.removeAllViews()
        fragmentTransaction.add(R.id.container, fragment).commit()
    }

    fun replaceCurrentFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }
}
