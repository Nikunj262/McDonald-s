package com.example.mcdonalds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity {
    ImageView img;
    TextView name1, totalprice, qtn ;
    TextView pr;
    TextView txtdes;
    Button button1, btnplus, btnminus;
    String imageurl;
    int quantity =1;
    float cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        img = findViewById(R.id.fullImageView);
        name1 = findViewById(R.id.name);
        pr = findViewById(R.id.price);
        txtdes = findViewById(R.id.txtdes);
        totalprice = findViewById(R.id.totalprice);
        qtn = findViewById(R.id.qtn);
        btnplus = findViewById(R.id.btnplus);
        btnminus = findViewById(R.id.btnminus);

        imageurl = getIntent().getStringExtra("image@");
        Glide.with(this).load(imageurl).into(img);

        String name = getIntent().getStringExtra("name");
        name1.setText(name);
        String price = getIntent().getStringExtra("price").toString();

        pr.setText("$"+price);
        totalprice.setText("$"+price);
        String detail = getIntent().getStringExtra("detail").toString();
        txtdes.setText(detail);


        button1 = findViewById(R.id.orderbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (quantity != 0) {
                    intent = new Intent(Details.this, Checkout.class);
                    intent.putExtra("price", price);
                    intent.putExtra("name", name);
                    intent.putExtra("imageurl", imageurl);
                    intent.putExtra("finalprice", totalprice.getText().toString());
                }
                else{
                    intent = new Intent(Details.this, menu.class);
                }
                startActivity(intent);


            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost = Float.parseFloat(price);
                quantity++;
                qtn.setText(String.valueOf(quantity));
                float a = cost*Float.parseFloat(String.valueOf(quantity));
                totalprice.setText("$"+String.valueOf(a));
            }
        });
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity == 0){
                    Toast.makeText(Details.this,"Select atleast one item",Toast.LENGTH_SHORT).show();
                }
                else {
                    cost = Float.parseFloat(price);
                    quantity--;
                    qtn.setText(String.valueOf(quantity));
                    float a = cost * Float.parseFloat(String.valueOf(quantity));
                    totalprice.setText("$" + String.valueOf(a));
                }
            }
        });
    }
}