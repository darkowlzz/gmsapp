package space.darkowlzz.globalmeditationscope;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<MediEvent> events;

    public static ImageLoader mImageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getApplicationContext());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        initializeData();

        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(events);
        rv.setAdapter(adapter);
    }

    private void initializeData() {
        events = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 11, 13, 8, 0);
        Date date = cal.getTime();
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date));
        events.add(new MediEvent("Dive deep into the hollowness within you with MaxWell", "10 min breathing meditation with MaxWell", "Max Well", date));
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date));
        events.add(new MediEvent("Dive deep into the hollowness within you with MaxWell", "10 min breathing meditation with MaxWell", "Max Well", date));
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date));
        events.add(new MediEvent("Dive deep into the hollowness within you with MaxWell", "10 min breathing meditation with MaxWell", "Max Well", date));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
