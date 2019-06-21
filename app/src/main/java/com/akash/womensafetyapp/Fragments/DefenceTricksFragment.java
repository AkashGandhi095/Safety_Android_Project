package com.akash.womensafetyapp.Fragments;


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

import com.akash.womensafetyapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefenceTricksFragment extends Fragment {


    public DefenceTricksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_defence_tricks, container, false);

        MyPagerAdapters adapters = new MyPagerAdapters(getChildFragmentManager());

        ViewPager pager = view.findViewById(R.id.Pager);
        pager.setAdapter(adapters);

        TabLayout tabLayout = view.findViewById(R.id.myTabs);
        tabLayout.setupWithViewPager(pager);
        pager.setCurrentItem(0);
        return view;
    }


    private class MyPagerAdapters extends FragmentPagerAdapter {

        public MyPagerAdapters(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return new VideoFragment();
                }
                case 1: {
                    return new PostFragment();
                }


            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return getResources().getText(R.string.Video);

                case 1:
                    return getResources().getText(R.string.post);


            }
            return super.getPageTitle(position);
        }

    }
}
