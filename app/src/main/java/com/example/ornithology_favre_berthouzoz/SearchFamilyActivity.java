package com.example.ornithology_favre_berthouzoz;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchFamilyActivity extends AppCompatActivity {

    ListView family_array;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_search_family);


        family_array = (ListView) findViewById(R.id.family_array);

        ArrayList<String> arrayFamily = new ArrayList<>();
        arrayFamily.addAll(Arrays.asList(getResources().getStringArray(R.array.family_array)));

        adapter = new ArrayAdapter<String>(
                SearchFamilyActivity.this,
                android.R.layout.simple_list_item_1,
                arrayFamily ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTextColor(Color.argb(255,3,169,255));

                return item;
            }
        };
        family_array.setAdapter(adapter);
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.search_by_family, menu);
            MenuItem item = menu.findItem(R.id.family_array);
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




    public void addFamily(View addFamilyView) {
        Intent intent = new Intent(this, AddFamily.class);
        startActivity(intent);

    }

    public void infoFamily(View addFamilyView) {
        Intent intent = new Intent(this, InfoFamily.class);
        startActivity(intent);

    }


}
