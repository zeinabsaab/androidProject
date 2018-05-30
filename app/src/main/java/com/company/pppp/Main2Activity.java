package com.company.pppp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Main2Activity extends AppCompatActivity {
//FirebaseReference helper;
    private final static int pick=1;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageButton b1 = (ImageButton) findViewById(R.id.add1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im=(ImageView)findViewById(R.id.photos);
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, pick);
            }


        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            // img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            im.setImageURI(selectedImage);


        }

        final EditText category=(EditText)findViewById(R.id.category);
        Button save=(Button)findViewById(R.id.done);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getdata

                String cat=category.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("database").child("Category");
                Category n=new Category(cat);
//fghfghkuuhi7rygdtttdtyyj
                reference.push().setValue(n);
                Toast.makeText(Main2Activity.this,"category insert",Toast.LENGTH_LONG).show();
                //image upload
                FirebaseStorage    mStorageRef =FirebaseStorage.getInstance();
                // FirebaseStorage storage = FirebaseStorage.getInstance(customApp, "gs://my-custom
                StorageReference k=mStorageRef.getReferenceFromUrl("gs://pppp-e27bd.appspot.com");
                StorageReference s=k.child("images");
                ImageView a=(ImageView)findViewById(R.id.photos);
                a.setDrawingCacheEnabled(true);
                a.buildDrawingCache();
                // Bitmap bitmap = ((BitmapDrawable) a.getDrawable()).getBitmap();
                BitmapDrawable bitmapDrawable=(BitmapDrawable)a.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = s.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });

            }
        });
    }

    public void cancel(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }


}



