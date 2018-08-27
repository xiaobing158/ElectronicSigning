package com.example.bigbing.electronicsigning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import fragment.Fragment1;
import fragment.Fragment2;
import fragment.Fragment3;

public class BottomNavigationView extends AppCompatActivity {

    private FragmentTransaction transaction;


    private android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment1 fragment1 = new Fragment1();
                    replaceFragment(fragment1);
                    return true;
                case R.id.navigation_dashboard:
                    // 添加fragment
                    Fragment2 fragment2 = new Fragment2();
                    replaceFragment(fragment2);
                    return true;
                case R.id.navigation_notifications:
                    Fragment3 fragment3 = new Fragment3();
                    replaceFragment(fragment3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        Fragment1 fragment1 = new Fragment1();
        replaceFragment(fragment1);
        android.support.design.widget.BottomNavigationView navigation = (android.support.design.widget.BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 添加Fragment
     */
    private void replaceFragment(Fragment from){
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,from);
        transaction.commit();
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
