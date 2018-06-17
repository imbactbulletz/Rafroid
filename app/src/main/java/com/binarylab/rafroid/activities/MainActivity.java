package com.binarylab.rafroid.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.binarylab.rafroid.R;
import com.binarylab.rafroid.fragments.ClassScheduleFragment;
import com.binarylab.rafroid.fragments.CurriculumFragment;
import com.binarylab.rafroid.fragments.ExamFragment;
import com.binarylab.rafroid.fragments.NewsFragment;

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
                if (mFragmentToSet != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);
                    fragmentTransaction.replace(R.id.fragment_container, mFragmentToSet);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //NavigationView that shows from the left
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // set item as selected to persist highlight
            item.setChecked(true);

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            //TODO: Swap fragments

            switch (item.getItemId()) {

                case R.id.nav_classes_schedule:
                    mFragmentToSet = ClassScheduleFragment.newInstance();
                    getSupportActionBar().setTitle(R.string.classes_schedule);
                    break;

                case R.id.nav_curriculum_schedule:
                    getSupportActionBar().setTitle(R.string.curriculum_schedule);
                    mFragmentToSet = CurriculumFragment.newInstance();
                    break;

                case R.id.nav_exam_schedule:
                    getSupportActionBar().setTitle(R.string.exam_schedule);
                    mFragmentToSet = ExamFragment.newInstance();
                    break;

                case R.id.nav_teaching_schedule:
                    getSupportActionBar().setTitle(R.string.teaching_schedule);
                    break;

                case R.id.nav_consultation_schedule:
                    getSupportActionBar().setTitle(R.string.consultation_schedule);
                    break;

                case R.id.nav_news:
                    mFragmentToSet = NewsFragment.newInstance();
                    getSupportActionBar().setTitle(R.string.news);
                    break;

                case R.id.nav_settings:
                    getSupportActionBar().setTitle(R.string.settings);
                    break;

                case R.id.nav_about:
                    getSupportActionBar().setTitle(R.string.about);
                    break;

                case R.id.nav_exit:
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.exit_dialog_message))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                                //Kill application with fire
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            })
                            .setNegativeButton(getString(R.string.no), null)
                            .show();
                    break;

                default:
                    break;
            }

            // close drawer when item is tapped
            drawerLayout.closeDrawers();

            return true;
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

            //Set title
            getSupportActionBar().setTitle(R.string.news);

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
