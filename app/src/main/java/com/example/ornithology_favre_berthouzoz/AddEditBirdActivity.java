package com.example.ornithology_favre_berthouzoz;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AddEditBirdActivity extends AppCompatActivity {


    public static final String EXTRA_IDBIRD=
            "com.example.ornithology_favre_berthouzoz.EXTRA_IDBIRD";
    public static final String EXTRA_NAME =
            "com.example.ornithology_favre_berthouzoz.EXTRA_NAME";
    public static final String EXTRA_FAMILY =
            "com.example.ornithology_favre_berthouzoz.EXTRA_FAMILY";
    public static final String EXTRA_DESCRIPTION =
            "com.example.ornithology_favre_berthouzoz.EXTRA_DESCRIPTION";
    public static final String EXTRA_BIOLOGY =
            "com.example.ornithology_favre_berthouzoz.EXTRA_BIOLOGY";


    //firebase upload image
    public static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private StorageTask mUploadTask;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    private EditText editName, editFamily, editDescription, editBiology;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_addbird);



        //image with firebase
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        //get into the uploads file in our storage
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");



        //id of the text in the .xml
        editName =  findViewById(R.id.edit_txt_name);
        editFamily  =  findViewById(R.id.edit_txt_family);
        editBiology  =  findViewById(R.id.edit_txt_biology);
        editDescription  =  findViewById(R.id.edit_txt_description);




        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddEditBirdActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






        //close icon
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);



        Intent intent = getIntent();

        //only triggered if it contains an ID, so it's only happens when we have an update situation
        if(intent.hasExtra(EXTRA_IDBIRD)){
            // UPDATE
            setTitle("Edit a bird");

            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editFamily.setText(intent.getStringExtra(EXTRA_FAMILY));
            editBiology.setText(intent.getStringExtra(EXTRA_BIOLOGY));
            editDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }
        else{
            setTitle("Add a new bird");
        }


    }


    //open file chooser (for image)
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*"); //only images
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    //load the image into the layout
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the user select a file and not nothing
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            //get the Uri of the image
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }
    }



        //method to save the bird
    private void saveBird(){
        String name = editName.getText().toString();
        String family = editFamily.getText().toString();
        String description = editDescription.getText().toString();
        String biology = editBiology.getText().toString();

        //if it's not empty, save, otherwise send a toast message
        if(name.trim().isEmpty() || family.trim().isEmpty()){
            Toast.makeText(this,"Please enter a name and a family", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_FAMILY, family);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_BIOLOGY, biology);




        //update with id
        int id = getIntent().getIntExtra(EXTRA_IDBIRD, -1); //-1 because we will never have an entry which is -1
        if(id != -1){
            data.putExtra(EXTRA_IDBIRD, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_bird, menu);
        return true;

    }


    //icon to save bird
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.save_bird:
                saveBird();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    //get the file extension of the file
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




    private void uploadFile() {
        if (mImageUri != null) {




            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));


            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {




                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);


                            Toast.makeText(AddEditBirdActivity.this, "Upload successful", Toast.LENGTH_LONG).show();


                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString());



                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {


                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddEditBirdActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }





}
