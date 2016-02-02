package space.darkowlzz.globalmeditationscope.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import space.darkowlzz.globalmeditationscope.services.MediNotificationService;
import space.darkowlzz.globalmeditationscope.R;
import space.darkowlzz.globalmeditationscope.utils.TinyDB;
import space.darkowlzz.globalmeditationscope.activities.MainActivity;
import space.darkowlzz.globalmeditationscope.model.MediEvent;

/**
 * Created by sunny on 24/11/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder> {

    List<MediEvent> events;
    String fragmentName;
    Context ctx;
    TinyDB tinyDB;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title, description;
        TextView hostName, date, time;
        ImageView peri, twitter;
        //CircleImageView image;
        ImageView image;
        final ImageView fav;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            //title = (TextView) itemView.findViewById(R.id.event_title);
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

    public RVAdapter(List<MediEvent> events, String fragmentName) {
        this.events = events;
        this.fragmentName = fragmentName;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);

        ctx = parent.getContext();
        tinyDB = new TinyDB(ctx);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {
        final MediEvent evnt = events.get(position);

        //holder.title.setText(evnt.title);
        holder.description.setText(evnt.description);
        holder.hostName.setText(evnt.hostName);
        holder.time.setText(evnt.getTime());
        holder.date.setText(evnt.getDate());
        //MainActivity.mImageLoader.displayImage("", holder.image);
        Picasso.with(ctx)
                .load("https://twitter.com/" + evnt.twitterHandle + "/profile_image?size=original")
                .placeholder(R.drawable.gms_logo).fit()
                .into(holder.image);

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

        holder.peri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent periIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(evnt.getPeriUri()));
                ((MainActivity)ctx).startActivity(periIntent);
            }
        });

        holder.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(evnt.getTwitterUri()));
                ((MainActivity)ctx).startActivity(twitterIntent);
            }
        });

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MediEvent> allEvents = (ArrayList) tinyDB.getListObject(MainActivity.ALL_EVENTS, MediEvent.class);
                ArrayList<MediEvent> favEvents = (ArrayList) tinyDB.getListObject(MainActivity.FAVORITE_EVENTS, MediEvent.class);

                int index = findIndexOfEvent(evnt.eventID, allEvents);

                if (evnt.favorite) {
                    // Remove from favorite events
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off, ctx.getTheme()));
                    } else {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                    }
                    evnt.favorite = !evnt.favorite;
                    favEvents.remove(findIndexOfEvent(evnt.eventID, favEvents));
                    if (fragmentName == MainActivity.FAVORITES_FRAGMENT) {
                        // remove the card in runtime with animation
                        removeAt(position);
                    }
                    removeReminder(evnt);
                } else  {
                    // Add to favorite events
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on, ctx.getTheme()));
                    } else {
                        holder.fav.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                    }
                    evnt.favorite = !evnt.favorite;
                    favEvents.add(evnt);
                    setReminder(evnt);
                }
                //evnt.favorite = !evnt.favorite;
                allEvents.set(index, evnt);
                tinyDB.putListObject(MainActivity.FAVORITE_EVENTS, favEvents);
                tinyDB.putListObject(MainActivity.ALL_EVENTS, allEvents);
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

    private int findIndexOfEvent(int eventID, ArrayList<MediEvent> mediEvents) {
        for (MediEvent m : mediEvents) {
            if (m.eventID.equals(eventID)) {
                return mediEvents.indexOf(m);
            }
        }
        return -1;
    }

    // Remove element from recyclerView
    public void removeAt(int position) {
        events.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, events.size());
    }

    public void setReminder(MediEvent mediEvent) {
        DateTime now = new DateTime();

        if (mediEvent.getLocalDateObj().isBefore(now)) {
            return;
        } else {

            int reminderCount = tinyDB.getInt(MainActivity.REMINDER_COUNTER) + 1;
            tinyDB.putInt(MainActivity.REMINDER_COUNTER, reminderCount);

            Intent intent = new Intent(ctx, MediNotificationService.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("eventID", mediEvent.eventID);
            //dataBundle.putString("eventTitle", mediEvent.title);
            dataBundle.putString("eventHost", mediEvent.hostName);
            dataBundle.putString("eventURI", mediEvent.getPeriUri());
            intent.putExtras(dataBundle);

            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(ctx, mediEvent.eventID, intent, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, mediEvent.getLocalDateObj().getMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, mediEvent.getLocalDateObj().getMillis(), pendingIntent);
            }

        }

        //Toast.makeText(ctx, "Reminder set!", Toast.LENGTH_SHORT).show();
    }

    public void removeReminder(MediEvent mediEvent) {
        Intent intent = new Intent(ctx, MediNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(ctx, mediEvent.eventID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
