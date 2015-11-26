package space.darkowlzz.globalmeditationscope;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sunny on 26/11/15.
 */
public class RVGridAdapter extends RecyclerView.Adapter<RVGridAdapter.CategoryViewHolder> {

    private ArrayList<MediCategory> itemList;
    private Context context;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public CategoryViewHolder(View itemView) {
            super(itemView);
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
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.categoryName.setText(itemList.get(position).name);
        holder.categoryImage.setImageDrawable(itemList.get(position).image);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}
