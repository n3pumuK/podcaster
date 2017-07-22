package de.exercicse.jrossbach.podcast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import de.exercicse.jrossbach.podcast.search.SearchPodcastFragment;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        setUpFragments();
    }

    private void setUpFragments(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = SearchPodcastFragment.newInstance();
        container.removeAllViews();
        fragmentTransaction.add(R.id.container, fragment).commit();
    }

    public void replaceCurrentFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
    }

}
