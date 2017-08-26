package home.airveda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Aishwarya on 8/22/2017.
 */

public class DescriptionActivity extends AppCompatActivity {

    TextView title,descritpion,caption,name;
    ImageView image;
    Button choice;
    Boolean Like = false;
    String choice_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        title = (TextView)findViewById(R.id.title);
        caption =(TextView)findViewById(R.id.caption);
        descritpion = (TextView)findViewById(R.id.description);
        name = (TextView)findViewById(R.id.name);
        image = (ImageView)findViewById(R.id.photo);
        choice = (Button)findViewById(R.id.choice);

        final String title_holder = getIntent().getStringExtra("TITLE");
        final String url_holder = getIntent().getStringExtra("URL");
        final String caption_holder = getIntent().getStringExtra("CAPTION");
        final String description_holder = getIntent().getStringExtra("DESCRIPTION");
        final String name_holder = getIntent().getStringExtra("NAME");
        choice_holder = getIntent().getStringExtra("CHOICE");

        if(choice_holder.equals("NO")) {
            Like=false;
            choice.setText("LIKE");
            choice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
            choice.setBackground(getResources().getDrawable(R.drawable.button_unliked));
        }
        if(choice_holder.equals("YES")) {
            Like = true;
            choice.setText("UNLIKE");
            choice.setTextColor(Color.WHITE);
            choice.setBackground(getResources().getDrawable(R.drawable.button_liked));
        }

        name.setText("From "+name_holder);
        title.setText(title_holder);
        descritpion.setText(description_holder);
        if(!caption_holder.equals("")) {
            caption.setText(caption_holder);
        }
        else {
            caption.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,30,30,0);
            image.setLayoutParams(lp);
        }
        if(!url_holder.equals("")) {
            Picasso.with(getApplicationContext()).load(url_holder).into(image);
        }
        else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,30,0,0);
            image.setLayoutParams(lp);
            caption.setTextSize(getResources().getDimension(R.dimen.textSize));
        }

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Like) {
                    Like = true;
                    choice.setText("UNLIKE");
                    choice.setTextColor(Color.WHITE);
                    choice.setBackground(getResources().getDrawable(R.drawable.button_liked));
                    choice_holder="YES";
                }
                else if(Like) {
                    Like = false;
                    choice.setText("LIKE");
                    choice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    choice.setBackground(getResources().getDrawable(R.drawable.button_unliked));
                    choice_holder="NO";
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DescriptionActivity.this,FeedActivity.class);
        intent.putExtra("CHOICE",choice_holder);
        setResult(555,intent);
        finish();
        super.onBackPressed();
    }
}
