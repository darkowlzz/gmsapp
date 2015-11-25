package space.darkowlzz.globalmeditationscope;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sunny on 24/11/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder> {

    List<MediEvent> events;
    Context ctx;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title, description;
        TextView hostName, date, time;
        ImageView image, peri, twitter;
        final ImageView fav;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            title = (TextView) itemView.findViewById(R.id.event_title);
            description = (TextView) itemView.findViewById(R.id.event_description);
            hostName = (TextView) itemView.findViewById(R.id.host_name);
            time = (TextView) itemView.findViewById(R.id.time);
            date = (TextView) itemView.findViewById(R.id.date);
            image = (ImageView) itemView.findViewById(R.id.event_image);
            fav = (ImageView) itemView.findViewById(R.id.fav);
            peri = (ImageView) itemView.findViewById(R.id.peri);
            twitter = (ImageView) itemView.findViewById(R.id.twitter);
        }
    }

    RVAdapter(List<MediEvent> events) {
        this.events = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);

        ctx = parent.getContext();
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        final MediEvent evnt = events.get(position);

        holder.title.setText(evnt.title);
        holder.description.setText(evnt.description);
        holder.hostName.setText(evnt.hostName);
        holder.time.setText(evnt.getTime());
        holder.date.setText(evnt.getDate());
        MainActivity.mImageLoader.displayImage("", holder.image);


        if (evnt.favorite) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on, ctx.getTheme()));
            } else {
                holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            }
        } else  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off, ctx.getTheme()));
            } else {
                holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off));
            }
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evnt.favorite) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off, ctx.getTheme()));
                    } else {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                    }
                } else  {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on, ctx.getTheme()));
                    } else {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                    }
                }
                evnt.favorite = !evnt.favorite;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
