package home.airveda.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import home.airveda.R;

/**
 * Created by Aishwarya on 8/28/2017.
 */


public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView time,title,caption,name;
    ImageView image;
    Button choice;
    RelativeLayout display;

    public MyViewHolder(View view) {
        super(view);
        time = (TextView)view.findViewById(R.id.time);
        title = (TextView)view.findViewById(R.id.title);
        caption = (TextView)view.findViewById(R.id.caption);
        name = (TextView)view.findViewById(R.id.name);
        image = (ImageView)view.findViewById(R.id.photo);
        choice = (Button)view.findViewById(R.id.choice);
        display = (RelativeLayout)view.findViewById(R.id.display);
    }
}