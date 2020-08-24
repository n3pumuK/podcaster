package de.exercicse.jrossbach.podcaster.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.feature.search.ui.SearchPodcastFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpFragments()
    }

    private fun setUpFragments() {
        container.removeAllViews()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, SearchPodcastFragment())
            .commit()
    }

    fun replaceCurrentFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }
}
