package com.example.ornithology_favre_berthouzoz;

import android.os.Bundle;

import com.example.room.Bird;
import com.example.room.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class SearchNameActivity extends AppCompatActivity {


    ListView birds_array;
    ArrayAdapter<String> adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);


        //with list inside "String"
        ArrayList<String> arrayBird = new ArrayList<>();
//        List<Bird> listBird = AddBird.myDataBase.dao().getBirds();

//        System.out.println(listBird.get(0).getName());



//        for(int i = 0; i< listBird.size(); i++)
//        {
//            arrayBird.add(listBird.get(i).getName());
//        }



        birds_array = (ListView) findViewById(R.id.birds_array);

        arrayBird.addAll(Arrays.asList(getResources().getStringArray(R.array.birds_array)));


        adapter = new ArrayAdapter<String>(
                SearchNameActivity.this,
                android.R.layout.simple_list_item_1,
                arrayBird
        );

        birds_array.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_by_name,menu);
        MenuItem item = menu.findItem(R.id.birds_array);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    public void addBird(View addBirdView) {
        Intent intent = new Intent(this, AddBird.class);

        startActivity(intent);

    }

    public void infoBird(View addBirdView) {
        Intent intent = new Intent(this, InfoBird.class);

        startActivity(intent);

    }
}
