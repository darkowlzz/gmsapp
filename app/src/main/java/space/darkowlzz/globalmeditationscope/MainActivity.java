package space.darkowlzz.globalmeditationscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // App constants
    public static final String ALL_EVENTS = "allEvents";
    public static final String FAVORITE_EVENTS = "favoriteEvents";
    public static final String REMINDER_COUNTER = "reminderCounter";

    public static final String EVENTS_FRAGMENT = "EventsFragment";
    public static final String FAVORITES_FRAGMENT = "FavoritesFragment";
    public static final String CATEGORIES_FRAGMENT = "CategoriesFragment";
    public static final String CATEGORY_FRAGMENT = "CategoryFragment";

    public static enum Category {
        BREATHE, MOVE, LISTEN, HEAL, CREATE, ALL
    };

    public static final String BUNDLE_ARG_SELECTED_CATEGORY = "selectedCategory";

    public static ImageLoader mImageLoader = null;

    public static String CURRENT_FRAGMENT;
    CategoriesFragment categoriesFragment;
    private ArrayList<MediEvent> events;

    TinyDB tinyDB;

    NavigationView navigationView;
    //FloatingActionButton fab;

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

        tinyDB = new TinyDB(getBaseContext());

        if (tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class).isEmpty()) {
            initializeData();
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

            getSupportActionBar().setTitle(getString(R.string.categories_fragment_title));

            categoriesFragment = new CategoriesFragment();
            categoriesFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, categoriesFragment).commit();

            CURRENT_FRAGMENT = CATEGORIES_FRAGMENT;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            switch (CURRENT_FRAGMENT) {
                case CATEGORIES_FRAGMENT:
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    break;

                case CATEGORY_FRAGMENT:
                    showCategoriesFragment();
                    break;

                case EVENTS_FRAGMENT:
                    showCategoriesFragment();
                    break;

                case FAVORITES_FRAGMENT:
                    showCategoriesFragment();
                    break;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {
            showCategoriesFragment();
        } else if (id == R.id.nav_events) {
            showEventsFragment();
        } else if (id == R.id.nav_favorites) {
            showFavoriteFragment();
        } else if (id == R.id.nav_intro) {
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_gms) {

        } else if (id == R.id.nav_about_medi) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showCategoriesFragment() {
        categoriesFragment = new CategoriesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, categoriesFragment);
        transaction.commit();
        getSupportActionBar().setTitle(getString(R.string.categories_fragment_title));
        CURRENT_FRAGMENT = CATEGORIES_FRAGMENT;

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void showEventsFragment() {
        EventsFragment eventsFragment = new EventsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, eventsFragment);
        transaction.commit();
        getSupportActionBar().setTitle(getString(R.string.events_fragment_title));
        CURRENT_FRAGMENT = EVENTS_FRAGMENT;

        navigationView.getMenu().getItem(1).setChecked(true);
    }

    private void showFavoriteFragment() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, favoritesFragment);
        transaction.commit();
        getSupportActionBar().setTitle(getString(R.string.favorites_fragment_title));
        CURRENT_FRAGMENT = FAVORITES_FRAGMENT;

        navigationView.getMenu().getItem(2).setChecked(true);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void initializeData() {
        events = new ArrayList<MediEvent>();
        MediEvent mediEvent;
        DateTime date = new DateTime(2015, 11, 28, 7, 35);
        mediEvent = new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.",
                "Anita Wing Lee", "anitawinglee", "anitawinglee", MainActivity.Category.HEAL, 1);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 29, 6, 0);
        mediEvent = new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh",
                "Amrit Singh", "MrASingh", "MrASingh", MainActivity.Category.CREATE, 2);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 29, 16, 35);
        mediEvent = new MediEvent("Dive deep into the hollowness within you with Max Goldberg",
                "10 min breathing meditation with MaxWell", "Max Goldberg",
                "livingmaxwell", "livingmaxwell", MainActivity.Category.BREATHE, 3);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 53);
        mediEvent = new MediEvent("Music with Nadia Hosko", "Relax and enjoy the music.",
                "Nadia Hoske", "nadiahosko", "nadiahosko", MainActivity.Category.LISTEN, 4);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 56);
        mediEvent = new MediEvent("Move your body with Jennifer Wicks", "Light yoga session with Jennifer Wicks",
                "Jennifer Wicks", "oneinyoga", "oneinyoga", MainActivity.Category.MOVE, 5);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 56);
        mediEvent = new MediEvent("Dive deep into the hollowness within you with Max Goldberg",
                "10 min breathing meditation with MaxWell", "Max Goldberg", "livingmaxwell",
                "livingmaxwell", MainActivity.Category.BREATHE, 6);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 56);
        mediEvent = new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.",
                "Anita Wing Lee", "anitawinglee", "anitawinglee", MainActivity.Category.HEAL, 7);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 56);
        mediEvent = new MediEvent("Music with Nadia Hosko", "Relax and enjoy the music.",
                "Nadia Hoske", "nadiahosko", "nadiahosko", MainActivity.Category.LISTEN, 8);
        mediEvent.setTime(date);
        events.add(mediEvent);

        date = new DateTime(2015, 11, 28, 15, 56);
        mediEvent = new MediEvent("Move your body with Jennifer Wicks", "Light yoga session with Jennifer Wicks",
                "Jennifer Wicks", "oneinyoga", "oneinyoga", MainActivity.Category.MOVE, 9);
        mediEvent.setTime(date);
        events.add(mediEvent);

        tinyDB.putListObject(MainActivity.ALL_EVENTS, events);
    }

    public static DateTime convertTimeZone(LocalDateTime date, String sourceTimeZone, String destTimeZone) {
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(sourceTimeZone));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(destTimeZone));
        return dstDateTime.toLocalDateTime().toDateTime();
    }
}
