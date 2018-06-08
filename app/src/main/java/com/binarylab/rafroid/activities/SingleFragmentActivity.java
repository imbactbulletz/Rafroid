package com.binarylab.rafroid.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.binarylab.rafroid.R;


public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";

    protected abstract Fragment createFragment();

    protected int getLayoutResId() {
        return R.layout.single_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = manager.findFragmentById(R.id.default_fragment_layout);
        Bundle bundle = getIntent().getExtras();
        if (fragment == null) {
            fragment = createFragment();
            fragment.setArguments(bundle);
            manager.beginTransaction()
                    .add(R.id.default_fragment_layout, fragment)
                    .commit();
        }

    }
}