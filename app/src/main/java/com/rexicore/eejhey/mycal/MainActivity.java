package com.rexicore.eejhey.mycal;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.rexicore.common.slidingtabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById( R.id.tool_bar );
        setSupportActionBar( toolbar );

        ViewPager viewpager = (ViewPager) findViewById( R.id.pager );
        viewpager.setAdapter( new ViewPagerAdapter( getSupportFragmentManager() ) );

        SlidingTabLayout mSTL = (SlidingTabLayout) findViewById( R.id.tabs );
        mSTL.setCustomTabColorizer( new SlidingTabLayout.TabColorizer(){

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        mSTL.setDistributeEvenly(true);
        mSTL.setViewPager( viewpager );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private CharSequence[] tabTitles = {"Tab 1", "Tab 2"};
        private int numPages = 2;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return tabTitles[0];
                case 1:
                    return tabTitles[1];
            }
            return "Error";
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new mCalendarView();
            return fragment;
        }

        @Override
        public int getCount() {
            return numPages;
        }
    }
}
