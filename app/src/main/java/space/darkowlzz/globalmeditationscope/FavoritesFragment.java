package space.darkowlzz.globalmeditationscope;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by sunny on 25/11/15.
 */
public class FavoritesFragment extends Fragment {

    private ArrayList<MediEvent> favorites, allFavorites;

    RecyclerView rv;
    LinearLayout emptyView;
    TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        setHasOptionsMenu(true);

        tinyDB = new TinyDB(getActivity().getApplicationContext());

        favorites = (ArrayList) tinyDB.getListObject(MainActivity.FAVORITE_EVENTS, MediEvent.class);
        allFavorites = new ArrayList<>(favorites);

        emptyView = (LinearLayout) view.findViewById(R.id.emptyView);

        if (favorites.size() > 0) {
            emptyView.setVisibility(View.GONE);
        }
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(favorites, MainActivity.FAVORITES_FRAGMENT);
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
                favorites.clear();
                for (MediEvent event : allFavorites) {
                    if (event.hostName.toLowerCase().startsWith(newText.toLowerCase())) {
                        favorites.add(event);
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
                    searchMenuItem.collapseActionView();
                }
            }
        });
        */
    }
}
