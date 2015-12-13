package space.darkowlzz.gsheets2a;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sunny on 04/12/15.
 */
public class GSheets2A {
    String key;
    ArrayList columns;
    DataResult callback;

    public interface DataResult {
        void onReceiveData(JSONObject object);
    }

    public GSheets2A(String key) {
        this.key = key;
        //this.columns = columns;
    }

    public String getKey() {
        return key;
    }

    public void getData(final DataResult callback) {
        new DownloadWebpageTask(new DownloadWebpageTask.AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                callback.onReceiveData(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=" + key);
    }
}
