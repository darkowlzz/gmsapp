package space.darkowlzz.globalmeditationscope;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by sunny on 26/11/15.
 */
public class CategoryFragment extends Fragment {

    private ArrayList<MediEvent> events, allEvents;
    TinyDB tinyDB;

    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        setHasOptionsMenu(true);
        String selectedCategory = getArguments().getString(MainActivity.BUNDLE_ARG_SELECTED_CATEGORY);
        ((MainActivity) getActivity()).setActionBarTitle(selectedCategory);

        tinyDB = new TinyDB(getContext());
        events = getEvents(selectedCategory);
        allEvents = new ArrayList<>(events);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(events, MainActivity.CATEGORY_FRAGMENT);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.events, menu);

        final SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                events.clear();
                for (MediEvent event : allEvents) {
                    if (event.hostName.toLowerCase().startsWith(newText.toLowerCase())) {
                        events.add(event);
                    }
                }

                rv.getAdapter().notifyDataSetChanged();

                return false;
            }
        });

        /*
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //searchMenuItem.collapseActionView();
                }
            }
        });
        */
    }

    public ArrayList getEvents(String category) {
        // Get category from string
        MainActivity.Category targetCategory = MainActivity.getCategory(category);
        ArrayList<MediEvent> events = (ArrayList) tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class);

        if (targetCategory == MainActivity.Category.ALL) {
            return events;
        }

        ArrayList<MediEvent> filteredEvents = new ArrayList<>();

        // Find events with the requred category and add them to the filteredEvents
        for (MediEvent event : events) {
            if (event.category == targetCategory) {
                filteredEvents.add(event);
            }
        }

        return filteredEvents;
    }
}
