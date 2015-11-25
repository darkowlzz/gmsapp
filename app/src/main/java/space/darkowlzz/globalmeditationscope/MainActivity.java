package space.darkowlzz.globalmeditationscope;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // App constants
    public static final String ALL_EVENTS = "allEvents";
    public static final String FAVORITE_EVENTS = "favoriteEvents";
    public static final String EVENTS_FRAGMENT = "EventsFragment";
    public static final String FAVORITES_FRAGMENT = "FavoritesFragment";

    public static ImageLoader mImageLoader = null;

    String CURRENT_FRAGMENT;
    EventsFragment eventsFragment;

    NavigationView navigationView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

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

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            getSupportActionBar().setTitle(getString(R.string.events_fragment_title));

            eventsFragment = new EventsFragment();
            eventsFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, eventsFragment).commit();

            CURRENT_FRAGMENT = EVENTS_FRAGMENT;

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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

        if (id == R.id.nav_events) {
            showEventsFragment();
        } else if (id == R.id.nav_favorites) {
            showFavoriteFragment();
        } else if (id == R.id.nav_about_gms) {

        } else if (id == R.id.nav_about_medi) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showEventsFragment() {
        eventsFragment = new EventsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, eventsFragment);
        transaction.commit();
        getSupportActionBar().setTitle(getString(R.string.events_fragment_title));
        CURRENT_FRAGMENT = EVENTS_FRAGMENT;

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void showFavoriteFragment() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, favoritesFragment);
        transaction.commit();
        getSupportActionBar().setTitle(getString(R.string.favorites_fragment_title));
        CURRENT_FRAGMENT = FAVORITES_FRAGMENT;

        navigationView.getMenu().getItem(1).setChecked(true);
    }
}
