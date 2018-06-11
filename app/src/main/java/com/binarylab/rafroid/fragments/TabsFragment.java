package com.binarylab.rafroid.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.binarylab.rafroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabsFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private static List<TabFragment> mFragments;

    public static TabsFragment newInstance(TabFragment... fragments) {
        TabsFragment instance = new TabsFragment();
        mFragments = new ArrayList<>(Arrays.asList(fragments));
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabs, container, false);

        mViewPager = v.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = v.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //Set the tab icons
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {

            if(mFragments.get(i).getIcon() == null)
                continue;

            mTabLayout.getTabAt(i).setIcon(mFragments.get(i).getIcon());
        }

        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for(TabFragment fragment: mFragments){
            adapter.addFragment(fragment, fragment.getTitle());
        }
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
