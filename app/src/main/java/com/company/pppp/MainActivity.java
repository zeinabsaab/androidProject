package com.company.pppp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private static  final int pick=1;
    ImageView  img;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMultiData();
        Toast.makeText(this,"on remplit le spinner",Toast.LENGTH_LONG).show();

        ImageButton b1 = (ImageButton) findViewById(R.id.add1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            img = (ImageView) findViewById(R.id.image1);
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
            img.setImageURI(selectedImage);


        }
    }

    public void add(View view) {
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
    }

    public void done(View view) {

        EditText title=(EditText)findViewById(R.id.edit_txt_title);
        String title1=title.getText().toString();
        EditText description =(EditText)findViewById(R.id.edit_txt_new);
        String description1=description.getText().toString();

        EditText source=(EditText)findViewById(R.id.edit_txt_source) ;
        String source1=source.getText().toString();
        Calendar right=Calendar.getInstance();
        int annee=right.get(Calendar.YEAR);
        int mois=right.get(Calendar.MONTH);
        int jour=right.get(Calendar.DAY_OF_MONTH);
        int hour=right.get(Calendar.HOUR_OF_DAY);
        int minute=right.get(Calendar.MINUTE);
        int second=right.get(Calendar.SECOND);
       final Spinner spin=(Spinner)findViewById(R.id.spinner);
        String attch1=spin.getSelectedItem().toString();
        String time=jour+"/"+mois+"/"+annee+"/"+hour+":"+minute+":"+second;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("database").child("News");

    News n = new News(title1, source1, description1,attch1, time);
    reference.push().setValue(n);
    Toast.makeText(MainActivity.this, "le news est inserer", Toast.LENGTH_LONG).show();

//image upload
        FirebaseStorage    mStorageRef =FirebaseStorage.getInstance();
        // FirebaseStorage storage = FirebaseStorage.getInstance(customApp, "gs://my-custom
        StorageReference k=mStorageRef.getReferenceFromUrl("gs://pppp-e27bd.appspot.com");
        StorageReference s=k.child("images/News.jpg");
        ImageView a=(ImageView)findViewById(R.id.image1);
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
public void getMultiData()
{
DatabaseReference myref=FirebaseDatabase.getInstance().getReference("database");
myref.child("Category").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final List<String> pa=new ArrayList<String>();
            Spinner sp=(Spinner)findViewById(R.id.spinner);

            for(DataSnapshot snapshot:dataSnapshot.getChildren())
            {
                Category category=snapshot.getValue(Category.class);

                pa.add(category.getCategory());

            }
            ArrayAdapter<String> ad=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,pa);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(ad);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
//System.out.println("the read failed "+ firebaseError.getMessage());
        }
    });
}

}
