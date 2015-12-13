package space.darkowlzz.globalmeditationscope;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import space.darkowlzz.gsheets2a.GSheets2A;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // App constants
    public static final String ALL_EVENTS = "allEvents";
    public static final String FAVORITE_EVENTS = "favoriteEvents";
    public static final String REMINDER_COUNTER = "reminderCounter";

    public static final String FAVORITES_FRAGMENT = "FavoritesFragment";
    public static final String CATEGORIES_FRAGMENT = "CategoriesFragment";
    public static final String CATEGORY_FRAGMENT = "CategoryFragment";
    public static final String ABOUT_GMS = "AboutGMS";

    public static enum Category {
        BREATHE, MOVE, LISTEN, HEAL, CREATE, ALL
    };

    public static final String BUNDLE_ARG_SELECTED_CATEGORY = "selectedCategory";

    public static String CURRENT_FRAGMENT;
    CategoriesFragment categoriesFragment;
    private ArrayList<MediEvent> events;

    TinyDB tinyDB;

    NavigationView navigationView;
    Toolbar toolbar;
    Snackbar updateSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        tinyDB = new TinyDB(getBaseContext());

        if (tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class).isEmpty()) {
            //initializeData();
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

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // download new data
            updateSnackbar = Snackbar.make(toolbar, "Updating data... please wait.", Snackbar.LENGTH_INDEFINITE);
            //GSheets2A gSheets2A = new GSheets2A("1JRhPZhaug2uQs23PpGZkQfdK-7fsN2-O6ATaz9oqQXg");
            GSheets2A gSheets2A = new GSheets2A("1rE6mYXea5ZG_MOgQR7Jh1419-fUiH3sz4x35nvdfH9Q");
            gSheets2A.getData(new GSheets2A.DataResult() {
                @Override
                public void onReceiveData(JSONObject object) {
                    processJson(object);
                }
            });
        } else {
            // Could check for new data.
            updateSnackbar = Snackbar.make(toolbar, "Couldn't update the data. No Internet connection.", Snackbar.LENGTH_SHORT);
        }
        updateSnackbar.show();
    }

    private void processJson(JSONObject object) {
        events = new ArrayList<>();
        MediEvent mediEvent;
        //DateTime date = new DateTime(2015, 11, 28, 7, 35);

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                Integer position = columns.getJSONObject(0).getInt("v");
                Integer dateOfMonth = columns.getJSONObject(1).getInt("v");
                String timeOfDay = columns.getJSONObject(2).getString("f");
                String name = columns.getJSONObject(3).getString("v");
                String periHandle = columns.getJSONObject(4).getString("v");
                String desc = columns.getJSONObject(5).getString("v");
                Category category = getCategory(columns.getJSONObject(6).getString("v"));

                // Strip any @ preceding the handle
                if (periHandle.startsWith("@")) {
                    periHandle = periHandle.substring(1);
                }

                DateTime time = getDateTime(dateOfMonth, timeOfDay);

                mediEvent = new MediEvent(position, "Unknown", desc, name,
                                          periHandle, periHandle, category);
                mediEvent.setTime(time);
                events.add(mediEvent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Update favorites with the new events
        events = updateFavorites(events);
        tinyDB.putListObject(MainActivity.ALL_EVENTS, events);
        updateSnackbar.dismiss();
        Snackbar.make(toolbar, "Data updated!", Snackbar.LENGTH_SHORT).show();
    }

    protected DateTime getDateTime(Integer dateOfMonth, String timeOfDay) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("hh:mm a");
        DateTime time = dateTimeFormatter.parseDateTime(timeOfDay);
        time = new DateTime(2015, 12, dateOfMonth, time.getHourOfDay(), time.getMinuteOfHour());
        return time;
    }

    public static Category getCategory(String category) {
        category = category.toUpperCase();
        switch (category) {
            case "BREATHE":
                return Category.BREATHE;
            case "MOVE":
                return Category.MOVE;
            case "LISTEN":
                return Category.LISTEN;
            case "HEAL":
                return Category.HEAL;
            case "CREATE":
                return Category.CREATE;
            default:
                return Category.ALL;
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

                case FAVORITES_FRAGMENT:
                    showCategoriesFragment();
                    break;

                case ABOUT_GMS:
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
        } else if (id == R.id.nav_favorites) {
            showFavoriteFragment();
        } else if (id == R.id.nav_intro) {
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_gms) {
            showAboutGMSFragment();
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

    private void showAboutGMSFragment() {
        AboutGmsFragment aboutGmsFragment = new AboutGmsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
        transaction.replace(R.id.fragment_container, aboutGmsFragment);
        transaction.commit();
        getSupportActionBar().setTitle(R.string.about_gms_title);
        CURRENT_FRAGMENT = ABOUT_GMS;

        navigationView.getMenu().getItem(2).setChecked(true);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public static DateTime convertTimeZone(LocalDateTime date, String sourceTimeZone, String destTimeZone) {
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(sourceTimeZone));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(destTimeZone));
        return dstDateTime.toLocalDateTime().toDateTime();
    }

    protected ArrayList<MediEvent> updateFavorites(ArrayList<MediEvent> events) {
        ArrayList<MediEvent> favorites = (ArrayList) tinyDB.getListObject(MainActivity.FAVORITE_EVENTS, MediEvent.class);
        MediEvent tempEvent;
        for (int i = 0; i < favorites.size(); ++i){
            for (int j = 0; j < events.size(); ++j) {
                if (favorites.get(i).eventID == events.get(j).eventID) {
                    // Get the event
                    tempEvent = events.get(j);
                    // Update favorite value
                    tempEvent.favorite = true;
                    // Put it back in the list
                    events.set(j, tempEvent);
                    favorites.set(i, tempEvent);
                }
            }
        }

        tinyDB.putListObject(MainActivity.FAVORITE_EVENTS, favorites);
        return events;
    }
}
