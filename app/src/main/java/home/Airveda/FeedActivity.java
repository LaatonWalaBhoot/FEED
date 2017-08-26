package home.airveda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
public class FeedActivity extends AppCompatActivity {

    HashMap<Integer,Details> Item = new HashMap<>();
    private RecyclerView Mylist;
    private MyAdapter adapter;
    Boolean Like = false;
    Integer index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        new GetData().execute();
        Mylist = (RecyclerView)findViewById(R.id.recycler_view);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 555){
            String choice_holder = data.getStringExtra("CHOICE");
            if(choice_holder.equals("YES")) {
                Item.get(index).setChoice("YES");
            }
            else if(choice_holder.equals("NO")) {
                Item.get(index).setChoice("NO");
            }
            adapter.notifyDataSetChanged();
        }

    }
    public String checkDuplicate(String time) {
        String value = "";
        int flag=0;
        for(int i=0;i<Item.size();i++) {
            if(Item.get(i).getTime().equals(time)) {
                flag=1;
            }
        }
        if(flag==1) {
            value="";
        }
        else {
            value=time;
        }
        return value;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        HashMap<Integer,Details> ItemList = new HashMap<>();

        public MyAdapter(HashMap<Integer,Details> Item) {
            this.ItemList.putAll(Item);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }


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

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if(ItemList.get(position).getTime().equals("")) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                holder.time.setLayoutParams(lp);
            }
            else {
                holder.time.setText(ItemList.get(position).getTime());
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
            if(!ItemList.get(position).getUri().equals("")) {
                Picasso.with(getApplicationContext()).load(ItemList.get(position).getUri()).into(holder.image);
            }
            else {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,0,0);
                holder.image.setLayoutParams(lp);
                holder.caption.setTextSize(getResources().getDimension(R.dimen.textSize));
            }
            if(ItemList.get(position).getChoice().equals("NO")) {
                Like=false;
                holder.choice.setText("LIKE");
                holder.choice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                holder.choice.setBackground(getResources().getDrawable(R.drawable.button_unliked));
            }
            else if(ItemList.get(position).getChoice().equals("YES")){
                Like=true;
                holder.choice.setText("UNLIKE");
                holder.choice.setTextColor(Color.WHITE);
                holder.choice.setBackground(getResources().getDrawable(R.drawable.button_liked));
            }
            holder.choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Item.get(position).getChoice().equals("NO")) {
                        Like=true;
                        holder.choice.setText("UNLIKE");
                        holder.choice.setTextColor(Color.WHITE);
                        holder.choice.setBackground(getResources().getDrawable(R.drawable.button_liked));
                        Item.get(position).setChoice("YES");
                        notifyDataSetChanged();
                    }
                    else if(Item.get(position).getChoice().equals("YES")) {
                        Like=false;
                        holder.choice.setText("LIKE");
                        holder.choice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                        holder.choice.setBackground(getResources().getDrawable(R.drawable.button_unliked));
                        Item.get(position).setChoice("NO");
                        notifyDataSetChanged();
                    }
                }
            });

            holder.display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = ItemList.get(position).getIndex();
                    Intent intent = new Intent(getApplicationContext(),DescriptionActivity.class);
                    intent.putExtra("TITLE",ItemList.get(position).getTitle());
                    intent.putExtra("URL",ItemList.get(position).getUri());
                    intent.putExtra("CAPTION",ItemList.get(position).getCaption());
                    intent.putExtra("NAME",ItemList.get(position).getName());
                    intent.putExtra("DESCRIPTION",ItemList.get(position).getDescription());
                    intent.putExtra("CHOICE",ItemList.get(position).getChoice());
                    startActivityForResult(intent,200);
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

    private class GetData extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog = new ProgressDialog(FeedActivity.this);
        String Name,Title,Date,Caption,Description,Url;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Fetching data...Please don't lose Internet Connection");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL("https://api.myjson.com/bins/x5279");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader( in ));

                String singleLine;
                try {
                    while ((singleLine = reader.readLine()) != null) {
                        result.append(singleLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                        connection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            String JsonString = result.toString();

            if(JsonString!=null) {
                try {
                    JSONArray Array_JSON = new JSONArray(JsonString);

                    for(int i=0;i<Array_JSON.length();i++) {
                        JSONObject obj_Product = Array_JSON.getJSONObject(i);
                        Name = obj_Product.getString("name");
                        Title = obj_Product.getString("title");
                        Date = obj_Product.getString("date");
                        Caption = obj_Product.getString("caption");
                        Description = obj_Product.getString("description");
                        Url = obj_Product.getString("url");

                        if(i==0) {
                            Item.put(i,new Details(i,Date,Title,Url,Caption,Name,Description,"NO"));
                        }
                        else {
                            Item.put(i,new Details(i,checkDuplicate(Date),Title,Url,Caption,Name,Description,"NO"));
                        }
                    }

                }catch (final JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            adapter = new MyAdapter(Item);
            Mylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            Mylist.setAdapter(adapter);
        }
    }

    static public class Details {
        Integer index;
        String time;
        String title;
        String uri;
        String caption;
        String name;
        String description;
        String choice;

        public Details(Integer index, String time, String title, String uri, String caption, String name, String description,String choice) {
            this.index=index;
            this.time=time;
            this.title=title;
            this.uri=uri;
            this.caption=caption;
            this.name=name;
            this.description=description;
            this.choice=choice;
        }
        public Integer getIndex() { return index; }

        public String getTime() { return time; }

        public String getTitle() { return title; }

        public String getCaption() { return caption; }

        public String getName() { return name; }

        public String getDescription() { return description; }

        public String getChoice() { return  choice; }

        public String setChoice(String choice) {
            this.choice=choice;
            return choice;
        }

        public String getUri() { return uri; }
    }
}
