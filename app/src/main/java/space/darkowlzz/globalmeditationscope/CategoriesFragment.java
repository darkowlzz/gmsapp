package space.darkowlzz.globalmeditationscope;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sunny on 26/11/15.
 */
public class CategoriesFragment extends Fragment {

    private ArrayList<MediCategory> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories , container, false);

        setHasOptionsMenu(false);
        initializeData();
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(glm);

        RVGridAdapter adapter = new RVGridAdapter(getContext(), categories);
        rv.setAdapter(adapter);

        return view;
    }

    private void initializeData() {
        categories = new ArrayList<>();
        categories.add(new MediCategory(getString(R.string.category_1), getImage(R.drawable.ic_breathe)));
        categories.add(new MediCategory(getString(R.string.category_2), getImage(R.drawable.ic_move)));
        categories.add(new MediCategory(getString(R.string.category_3), getImage(R.drawable.ic_listen)));
        categories.add(new MediCategory(getString(R.string.category_4), getImage(R.drawable.ic_heal)));
        categories.add(new MediCategory(getString(R.string.category_5), getImage(R.drawable.ic_create)));
        categories.add(new MediCategory(getString(R.string.category_6), getImage(R.drawable.ic_globe)));
    }

    private Drawable getImage(int drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(drawable, getContext().getTheme());
        } else {
            return getResources().getDrawable(drawable);
        }
    }
}