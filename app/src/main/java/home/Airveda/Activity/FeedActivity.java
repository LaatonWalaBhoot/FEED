package home.airveda.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import home.airveda.Model.ClientInterface;
import home.airveda.Model.Details;
import home.airveda.Views.MyAdapter;
import home.airveda.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedActivity extends AppCompatActivity {
    private RecyclerView Mylist;
    ArrayList<Details> Item = new ArrayList<>();
    ArrayList<Details> Itemlist = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Mylist = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Mylist.setLayoutManager(layoutManager);
        getJSON();
    }

    private void getJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.myjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ClientInterface request = retrofit.create(ClientInterface.class);
        Call<ArrayList<Details>> call = request.getDetails();

        final ProgressDialog progressDialog = new ProgressDialog(FeedActivity.this);
        progressDialog.setMessage("LOADING...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        call.enqueue(new Callback<ArrayList<Details>>() {
            @Override
            public void onResponse(Call<ArrayList<Details>> call, Response<ArrayList<Details>> response) {
               if(response.isSuccess()) {
                   progressDialog.dismiss();
                   Item=response.body();
                   for(int i=0;i<Item.size();i++) {
                       String name = Item.get(i).getName();
                       String title = Item.get(i).getTitle();
                       String date = Item.get(i).getDate();
                       String caption = Item.get(i).getCaption();
                       String description = Item.get(i).getDescription();
                       String url = Item.get(i).getUrl();

                       if(i==0) {
                           Itemlist.add(new Details(date,title,url,caption,name,description,"NO"));
                       }
                       else {
                           Itemlist.add(new Details(checkDuplicate(date),title,url,caption,name,description,"NO"));
                       }
                   }
                   adapter = new MyAdapter(Itemlist,FeedActivity.this);
                   Mylist.setAdapter(adapter);
               }
            }

            @Override
            public void onFailure(Call<ArrayList<Details>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public String checkDuplicate(String time) {
        String value = "";
        int flag=0;
        for(int i=0;i<Itemlist.size();i++) {
            if(Itemlist.get(i).getDate().equals(time)) {
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

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 555) {
            String choice_holder = data.getStringExtra("CHOICE");
            Integer index_holder = data.getIntExtra("INDEX",0);
            if (choice_holder.equals("YES")) {
                Itemlist.get(index_holder).setChoice("YES");
            } else if (choice_holder.equals("NO")) {
                Itemlist.get(index_holder).setChoice("NO");
            }
            adapter.notifyDataSetChanged();
        }

    }
}
