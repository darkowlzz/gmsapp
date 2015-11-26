package space.darkowlzz.globalmeditationscope;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sunny on 26/11/15.
 */
public class CategoryFragment extends Fragment {

    private ArrayList<MediEvent> events;
    TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        String selectedCategory = getArguments().getString(MainActivity.BUNDLE_ARG_SELECTED_CATEGORY);
        ((MainActivity) getActivity()).setActionBarTitle(selectedCategory);

        tinyDB = new TinyDB(getContext());
        events = (ArrayList) tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(events, MainActivity.CATEGORY_FRAGMENT);
        rv.setAdapter(adapter);

        return view;
    }

}
