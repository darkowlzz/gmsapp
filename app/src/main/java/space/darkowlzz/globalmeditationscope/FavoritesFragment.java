package space.darkowlzz.globalmeditationscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sunny on 25/11/15.
 */
public class FavoritesFragment extends Fragment {

    private ArrayList<MediEvent> favorites;

    TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        tinyDB = new TinyDB(getActivity().getApplicationContext());

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        favorites = (ArrayList) tinyDB.getListObject(MainActivity.FAVORITE_EVENTS, MediEvent.class);

        RVAdapter adapter = new RVAdapter(favorites, MainActivity.FAVORITES_FRAGMENT);
        rv.setAdapter(adapter);

        return view;
    }
}
