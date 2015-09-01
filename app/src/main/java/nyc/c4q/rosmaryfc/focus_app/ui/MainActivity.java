package nyc.c4q.rosmaryfc.focus_app.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import nyc.c4q.rosmaryfc.focus_app.App;
import nyc.c4q.rosmaryfc.focus_app.BlockSessionFragment;
import nyc.c4q.rosmaryfc.focus_app.DatabaseHelper;
import nyc.c4q.rosmaryfc.focus_app.R;
import nyc.c4q.rosmaryfc.focus_app.TestFragmentList;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;

    DatabaseHelper databaseHelper;

    List<App> monitoringApps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHelper = DatabaseHelper.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        if(savedInstanceState != null){
            return;
        } else if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.float_plus_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Add a time", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabTextColors();

    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (databaseHelper != null) {
//            try {
//                Dao<App, ?> appDao = databaseHelper.getDao(App.class);
//                monitoringApps = appDao.query(appDao.queryBuilder().where().eq("APP_MONITOR", true).prepare());
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (monitoringApps.size() != 0) {
//            MonitoringAppAdapter adapter = new MonitoringAppAdapter(this, monitoringApps);
//            monitoring_app_list.setAdapter(adapter);
//        }
//    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new TestFragmentList(), "App Monitor ");
        adapter.addFragment(new BlockSessionFragment(), "Block Sessions");
//        adapter.addFragment(new BlockTimes(), "BlockTimes");
//        adapter.addFragment(new TestFragmentList(), "TBD");
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
