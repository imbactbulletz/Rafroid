package com.binarylab.rafroid.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.fragments.ClassScheduleFragment;
import com.binarylab.rafroid.fragments.ClassScheduleSearchFragment;
import com.binarylab.rafroid.fragments.NewsFragment;
import com.binarylab.rafroid.fragments.TabsFragment;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Fragment mFragmentToSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting default toolbar to be our toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting toolbar menu button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Setting up drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);
                fragmentTransaction.replace(R.id.fragment_container, mFragmentToSet);
                fragmentTransaction.commit();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //NavigationView that shows from the left
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as selected to persist highlight
                item.setChecked(true);

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                //TODO: Swap fragments

                switch (item.getItemId()){

                    case R.id.nav_classes_schedule:
                        ClassScheduleFragment classScheduleFragment = ClassScheduleFragment.newInstance();
                        classScheduleFragment.setTitle(getString(R.string.schedule));
                        ClassScheduleSearchFragment classScheduleSearchFragment = ClassScheduleSearchFragment.newInstance();
                        classScheduleSearchFragment.setTitle(getString(R.string.search));
                        mFragmentToSet = TabsFragment.newInstance(classScheduleFragment,classScheduleSearchFragment);
                        break;


                    case R.id.nav_news:
                        mFragmentToSet = NewsFragment.newInstance();
                        break;

                    default: break;
                }

                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                return true;
            }
        });

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            //Adding Default Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.fragment_container, NewsFragment.newInstance());
            fragmentTransaction.commit();

            //Set selection of the news inside the menu
            navigationView.getMenu().getItem(5).setChecked(true);
        }

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("showConnectionError")){
            Toast.makeText(getApplicationContext(), R.string.error_connecting_to_server, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Open drawer
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
