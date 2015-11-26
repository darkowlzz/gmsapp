package space.darkowlzz.globalmeditationscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sunny on 25/11/15.
 */
public class EventsFragment extends Fragment {

    private ArrayList<MediEvent> events;
    TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        tinyDB = new TinyDB(getContext());

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        if (tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class).isEmpty()) {
            initializeData();
        } else {
            events = (ArrayList) tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class);
        }

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(events, MainActivity.EVENTS_FRAGMENT);
        rv.setAdapter(adapter);

        return view;
    }

    private void initializeData() {
        events = new ArrayList<MediEvent>();
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 11, 13, 8, 0);
        Date date = cal.getTime();
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date, 1));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date, 2));
        events.add(new MediEvent("Dive deep into the hollowness within you with Max Goldberg", "10 min breathing meditation with MaxWell", "Max Well", date, 3));
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date, 4));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date, 5));
        events.add(new MediEvent("Dive deep into the hollowness within you with Max Goldberg", "10 min breathing meditation with MaxWell", "Max Well", date, 6));
        events.add(new MediEvent("Morning with Anita Wing Lee", "Light guided meditation with Anita.", "Anita Wing Lee", date, 7));
        events.add(new MediEvent("Sketch with Mr. A. Singh", "Let out your creativity with @Mr. A. Singh", "Amrit Singh", date, 8));
        events.add(new MediEvent("Dive deep into the hollowness within you with Max Goldberg", "10 min breathing meditation with MaxWell", "Max Well", date, 9));

        tinyDB.putListObject(MainActivity.ALL_EVENTS, events);
    }
}
