package com.example.ornithology_favre_berthouzoz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditFamilyActivity extends AppCompatActivity {

    public static final String EXTRA_FAMILY =
            "com.example.ornithology_favre_berthouzoz.EXTRA_FAMILY";

    private EditText editFamily;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);



        editFamily  =  findViewById(R.id.edit_txt_family);

        //close icon
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        //update
        Intent intent = getIntent();




        //update and add share the same actvity, the only difference with them is the title and if the editText must be already filled(update) or the edit text are empty
        if(intent.hasExtra(EXTRA_FAMILY)){
            setTitle("Edit a family");

            editFamily.setText(intent.getStringExtra(EXTRA_FAMILY));

        }

        //add = empty lines
        else{

            setTitle("Add a family");

        }
    }


    private void saveFamily(){
        String family = editFamily.getText().toString();


        //if it's not empty, save, otherwise send a toast message
        if(family.trim().isEmpty() ){
            Toast.makeText(this,"Please enter a family", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_FAMILY, family);


        //update with id
        //family = getIntent().getStringExtra(EXTRA_FAMILY, null); //-1 because we will never have an entry which is -1
        if(family == null){
            data.putExtra(EXTRA_FAMILY, family);
        }


        setResult(RESULT_OK, data);
        finish();

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_family, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.save_family:
                saveFamily();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }










}