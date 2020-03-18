package com.example.ornithology_favre_berthouzoz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditBirdActivity extends AppCompatActivity {


    public static final String EXTRA_IDBIRD=
            "com.example.ornithology_favre_berthouzoz.EXTRA_IDBIRD";
    public static final String EXTRA_NAME =
            "com.example.ornithology_favre_berthouzoz.EXTRA_NAME";
    public static final String EXTRA_FAMILY =
            "com.example.ornithology_favre_berthouzoz.EXTRA_FAMILY";



//    String[]biotope;
//    String[]taille;

    private EditText editName, editFamily;

//    private Spinner spinner1, spinner2;
    public static Database myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbird);





//        myDataBase = Room.databaseBuilder(getApplicationContext(),
//                Database.class, "birdsdb").allowMainThreadQueries().build();

        editName = (EditText) findViewById(R.id.edit_txt_name);
        editFamily  = (EditText) findViewById(R.id.edit_txt_family);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);



        //update

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_IDBIRD)){
            setTitle("Edit a bird");

            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editFamily.setText(intent.getStringExtra(EXTRA_FAMILY));
        }
        else{
            setTitle("Add a new bird");
        }


    }






    private void saveBird(){
        String name = editName.getText().toString();
        String family = editFamily.getText().toString();

        //if it's not empty, save
        if(name.trim().isEmpty() || family.trim().isEmpty()){
            Toast.makeText(this,"Please enter a name and a family", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_FAMILY, family);

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





//        BnSave.setOnClickListener(new View.OnClickListener(){
//
//
//            @Override
//            public void onClick(View v) {
//                String name = Name.getText().toString();
//                String family = Family.getText().toString();
//
//                Bird bird = new Bird(name, family);
//                bird.setName(name);
//               // bird.setFamily(family);
//
//                AddBird.myDataBase.dao().insertBird(bird);
//                Toast.makeText(AddBird.this, "Bird added successfully", Toast.LENGTH_SHORT).show();
//                Name.setText("");
//                Family.setText("");
//
//            }
//        });







}
