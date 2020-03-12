package com.example.ornithology_favre_berthouzoz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.room.Bird;
import com.example.room.Database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddBird extends AppCompatActivity {
    String[]biotope;
    String[]taille;

    private EditText Name, Family;

    private Spinner spinner1, spinner2;
    private Button BnSave;
    public static Database myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbird);
        myDataBase = Room.databaseBuilder(getApplicationContext(),
                Database.class, "birdsdb").allowMainThreadQueries().build();

        Name = (EditText) findViewById(R.id.txt_name);
        Family  = (EditText) findViewById(R.id.txt_family);
        BnSave = (Button) findViewById(R.id.buttonSave);

        BnSave.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String family = Family.getText().toString();

                Bird bird = new Bird();
                bird.setName(name);
                bird.setFamily(family);

                AddBird.myDataBase.dao().addBird(bird);
                Toast.makeText(AddBird.this, "Bird added successfully", Toast.LENGTH_SHORT).show();
                Name.setText("");
                Family.setText("");

            }
        });

    }





}
