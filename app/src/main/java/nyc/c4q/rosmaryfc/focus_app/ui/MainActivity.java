package nyc.c4q.rosmaryfc.focus_app.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import nyc.c4q.rosmaryfc.focus_app.BlockService;
import nyc.c4q.rosmaryfc.focus_app.BlockSessionFragment;
import nyc.c4q.rosmaryfc.focus_app.MonitoringAppsFragment;
import nyc.c4q.rosmaryfc.focus_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BlockService.class);
        this.startService(intent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);


                setupViewPager(viewPager);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.float_plus_button);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "Add a time", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabTextColors();

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new BlockSessionFragment(), "Block Sessions");
        adapter.addFragment(new MonitoringAppsFragment(), "Blocked Apps ");

//        adapter.addFragment(new BlockTimes(), "BlockTimes");
//        adapter.addFragment(new MonitoringAppsFragment(), "TBD");
        viewPager.setAdapter(adapter);


    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
//
//    public void addBlockSessionOnClick (View view) {
//        Intent intent = new Intent (this, FocusSessionActivity.class );
//        startActivity(intent);
//    }

}
