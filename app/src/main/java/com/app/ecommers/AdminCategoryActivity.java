package com.app.ecommers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        ImageView objheadphones = findViewById(R.id.headphoness);
        ImageView objWatches = findViewById(R.id.watches);
        ImageView objLaptops = findViewById(R.id.laptops);
        ImageView objtshirts = findViewById(R.id.tShirts);
        ImageView objsports = findViewById(R.id.sportstShirts);
        ImageView objfemaleDresses = findViewById(R.id.female_dresses);
        ImageView objswethers = findViewById(R.id.sweather);
        ImageView objglasses = findViewById(R.id.glasses);
        ImageView objhats = findViewById(R.id.hats);
        ImageView objWallets = findViewById(R.id.purses_bags);
        ImageView objshoes = findViewById(R.id.shoess);
        ImageView objmobilePhone = findViewById(R.id.mobiles);
        objtshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "tshirts");
                startActivity(i);
            }
        });
        objsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "sports");
                startActivity(i);
            }
        });
        objfemaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "femaleDresses");
                startActivity(i);
            }
        });
        objswethers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "swethers");
                startActivity(i);
            }
        });
        objglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "glasses");
                startActivity(i);
            }
        });
        objhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "hats");
                startActivity(i);
            }
        });
        objWallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", " Wallets");
                startActivity(i);
            }
        });
        objshoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", " shoes");
                startActivity(i);
            }
        });
        objheadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", " headphones");
                startActivity(i);
            }
        });

        objLaptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", " Laptops");
                startActivity(i);
            }
        });
        objWatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "   Watches");
                startActivity(i);
            }
        });
        objmobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                i.putExtra("category", "   Watches");
                startActivity(i);
            }
        });

    }
}
