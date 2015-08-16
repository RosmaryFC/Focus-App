package nyc.c4q.rosmaryfc.focus_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.focus_logo)
    ImageView focus_logo;



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectViews();
    }

    protected void injectViews() {
        ButterKnife.bind(this);


    }


    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

//    protected void setupToolbar() {
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
//            toolbar.getMenu();
//        }
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void focusSessionOnClick (View view) {
        Intent intent = new Intent (this, FocusSessionActivity.class);
        startActivity(intent);
    }

    public void appsOnClick(View view) {
        Intent intent = new Intent (this, AppMonitor.class);
        startActivity(intent);
    }

}
