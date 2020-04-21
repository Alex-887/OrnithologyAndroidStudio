package com.example.ornithology_favre_berthouzoz.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ornithology_favre_berthouzoz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AddEditBirdActivity extends AppCompatActivity {


    public static final String EXTRA_IDBIRD=
            "com.example.ornithology_favre_berthouzoz.EXTRA_IDBIRD";
    public static final String EXTRA_NAME =
            "com.example.ornithology_favre_berthouzoz.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION =
            "com.example.ornithology_favre_berthouzoz.EXTRA_DESCRIPTION";
    public static final String EXTRA_BIOLOGY =
            "com.example.ornithology_favre_berthouzoz.EXTRA_BIOLOGY";


    private EditText editName, editDescription, editBiology;


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




        //id of the text in the .xml
        editName =  findViewById(R.id.edit_txt_name);
        editBiology  =  findViewById(R.id.edit_txt_biology);
        editDescription  =  findViewById(R.id.edit_txt_description);




        //close icon
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);



        Intent intent = getIntent();

        //only triggered if it contains an ID, so it's only happens when we have an update situation
        if(intent.hasExtra(EXTRA_IDBIRD)){
            // UPDATE
            setTitle("Edit a bird");


            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editBiology.setText(intent.getStringExtra(EXTRA_BIOLOGY));
            editDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

        }
        else
            {
            setTitle("Add a new bird");
        }


    }



        //method to save the bird
    private void saveBird(){
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        String biology = editBiology.getText().toString();

        //if it's not empty, save, otherwise send a toast message
        if(name.trim().isEmpty()){
            Toast.makeText(this,"Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }



        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_BIOLOGY, biology);




        //update with id
        String id = getIntent().getStringExtra(EXTRA_IDBIRD);
        if(id != null){
            data.putExtra(EXTRA_IDBIRD, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }

    //MENU

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




}
