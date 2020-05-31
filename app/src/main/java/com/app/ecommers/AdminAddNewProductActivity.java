package com.app.ecommers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, ProductName, SaveCurrentDate, SaveCurrentTime;
    private Button AddProduct;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private ImageView InputProductImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private ProgressDialog loadingBar;
    private String ProductRandomKey, DownloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("ProductImages");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        AddProduct = findViewById(R.id.add_new_product);
        InputProductImage = findViewById(R.id.Select_Product_Image);
        InputProductName = findViewById(R.id.product_Name);
        InputProductDescription = findViewById(R.id.product_Description);
        InputProductPrice = findViewById(R.id.product_Price);
        loadingBar = new ProgressDialog(this);
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

    }

    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        ProductName = InputProductName.getText().toString();
        if (ImageUri == null) {


            Toast.makeText(this, "Product Image Is Mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {

            Toast.makeText(this, "Enter Product Description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {

            Toast.makeText(this, "Enter Product Price", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ProductName)) {

            Toast.makeText(this, "Enter Product Name ", Toast.LENGTH_SHORT).show();
        } else {

            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product..");
        loadingBar.setMessage("Dear Admin Please Wait, While We Are Adding New Product......!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        loadingBar.setTitle("Login Account");

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM,dd,yyyy");
        SaveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH,mm,ss a");
        SaveCurrentTime = currentTime.format(calender.getTime());
        ProductRandomKey = SaveCurrentDate + SaveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Product Image Added Succesfully....", Toast.LENGTH_SHORT).show();


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {

                            throw task.getException();

                        }
                        DownloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadImageUrl=task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Got Product Image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {


        HashMap<String, Object> product = new HashMap<>();
        product.put("pid", ProductRandomKey);
        product.put("date", SaveCurrentDate);
        product.put("time", SaveCurrentTime);
        product.put("Pid", ProductRandomKey);
        product.put("description", Description);
        product.put("image", DownloadImageUrl);
        product.put("category", CategoryName);
        product.put("price", Price);
        product.put("pname", ProductName);
        ProductRef.child(ProductRandomKey).updateChildren(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "Product Is Added Successfuly...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                    startActivity(i);
                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick  &&  resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);

        }


    }
}
