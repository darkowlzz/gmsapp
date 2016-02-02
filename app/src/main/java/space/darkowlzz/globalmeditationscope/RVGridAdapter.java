package space.darkowlzz.globalmeditationscope;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import space.darkowlzz.globalmeditationscope.model.MediCategory;

/**
 * Created by sunny on 26/11/15.
 */
public class RVGridAdapter extends RecyclerView.Adapter<RVGridAdapter.CategoryViewHolder> {

    private ArrayList<MediCategory> itemList;
    private Context context;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView categoryName;
        ImageView categoryImage;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            categoryName = (TextView) itemView.findViewById(R.id.grid_item_label);
            categoryImage = (ImageView) itemView.findViewById(R.id.grid_item_image);
        }
    }

    public RVGridAdapter(Context context, ArrayList<MediCategory> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_grid, null);
        CategoryViewHolder cvh = new CategoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.categoryName.setText(itemList.get(position).name);
        Picasso.with(context)
                .load(itemList.get(position).image)
                .into(holder.categoryImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.BUNDLE_ARG_SELECTED_CATEGORY, itemList.get(position).name);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(bundle);
                FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
                transaction.replace(R.id.fragment_container, categoryFragment);
                transaction.commit();
                MainActivity.CURRENT_FRAGMENT = MainActivity.CATEGORY_FRAGMENT;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}
