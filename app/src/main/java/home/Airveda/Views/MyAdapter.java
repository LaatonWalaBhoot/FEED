package home.airveda.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import home.airveda.Activity.DescriptionActivity;
import home.airveda.Model.Details;
import home.airveda.R;

/**
 * Created by Aishwarya on 8/28/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<Details> ItemList = new ArrayList<>();
    Context context;

    public MyAdapter(ArrayList<Details> Item, Context context) {
        this.context=context;
        ItemList=Item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final  MyViewHolder holder, final int position) {
        if(ItemList.get(position).getDate().equals("")) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            holder.time.setLayoutParams(lp);
        }
        else {
            holder.time.setText(ItemList.get(position).getDate());
        }
        holder.title.setText(ItemList.get(position).getTitle());
        holder.name.setText("From "+ItemList.get(position).getName());
        if(!ItemList.get(position).getCaption().equals("")) {
            holder.caption.setText(ItemList.get(position).getCaption());
        }
        else {
            holder.caption.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,0,30,0);
            holder.image.setLayoutParams(lp);
        }
        if(!ItemList.get(position).getUrl().equals("")) {
            Picasso.with(context).load(ItemList.get(position).getUrl()).into(holder.image);
        }
        else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);
            holder.image.setLayoutParams(lp);
            holder.caption.setTextSize(context.getResources().getDimension(R.dimen.textSize));
        }
        if(ItemList.get(position).getChoice().equals("NO")) {
            holder.choice.setText("LIKE");
            holder.choice.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            holder.choice.setBackground(context.getResources().getDrawable(R.drawable.button_unliked));
        }
        else if(ItemList.get(position).getChoice().equals("YES")){
            holder.choice.setText("UNLIKE");
            holder.choice.setTextColor(Color.WHITE);
            holder.choice.setBackground(context.getResources().getDrawable(R.drawable.button_liked));
        }
        holder.choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ItemList.get(position).getChoice().equals("NO")) {
                    holder.choice.setText("UNLIKE");
                    holder.choice.setTextColor(Color.WHITE);
                    holder.choice.setBackground(context.getResources().getDrawable(R.drawable.button_liked));
                    ItemList.get(position).setChoice("YES");
                }
                else if(ItemList.get(position).getChoice().equals("YES")) {
                    holder.choice.setText("LIKE");
                    holder.choice.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    holder.choice.setBackground(context.getResources().getDrawable(R.drawable.button_unliked));
                    ItemList.get(position).setChoice("NO");
                }
            }
        });

        holder.display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DescriptionActivity.class);
                intent.putExtra("TITLE",ItemList.get(position).getTitle());
                intent.putExtra("URL",ItemList.get(position).getUrl());
                intent.putExtra("CAPTION",ItemList.get(position).getCaption());
                intent.putExtra("NAME",ItemList.get(position).getName());
                intent.putExtra("DESCRIPTION",ItemList.get(position).getDescription());
                intent.putExtra("CHOICE",ItemList.get(position).getChoice());
                intent.putExtra("INDEX",position);
                ((Activity)context).startActivityForResult(intent,111);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}