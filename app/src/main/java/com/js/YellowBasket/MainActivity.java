package com.js.YellowBasket;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_navigation;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MyApplication2_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

        setUpViewPager();
        bottom_navigation.setOnNavigationItemSelectedListener(item ->  {
                switch (item.getItemId())
                {
                    case R.id.accessibility_custom_action_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.accessibility_custom_action_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.accessibility_custom_action_3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.accessibility_custom_action_4:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            });
    }
    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottom_navigation.getMenu().findItem(R.id.accessibility_custom_action_1).setChecked(true);
                    break;
                case 1:
                    bottom_navigation.getMenu().findItem(R.id.accessibility_custom_action_2).setChecked(true);
                    break;
                case 2:
                    bottom_navigation.getMenu().findItem(R.id.accessibility_custom_action_3).setChecked(true);
                    break;
                case 3:
                    bottom_navigation.getMenu().findItem(R.id.accessibility_custom_action_4).setChecked(true);
                    break;
            }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

}
