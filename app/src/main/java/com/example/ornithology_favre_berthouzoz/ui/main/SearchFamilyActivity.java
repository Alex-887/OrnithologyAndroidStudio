package com.example.ornithology_favre_berthouzoz.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebaseEntities.Family_Firebase;
import com.example.firebaseViewModel.FamilyListViewModelFirebase;
import com.example.ornithology_favre_berthouzoz.R;
import com.example.util.OnAsyncEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFamilyActivity extends AppCompatActivity {

    public static final int ADD_FAMILY_REQUEST = 1;
    public static final int EDIT_FAMILY_REQUEST = 2;

    private static final String TAG = "FamiliesActivity";


    private List<Family_Firebase> families;
    private FamilyListViewModelFirebase viewModelList;


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

        //add button
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchFamilyActivity.this, AddEditFamilyActivity.class);
                startActivityForResult(intent, ADD_FAMILY_REQUEST);
            }
        });






        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        //RECYCLER VIEW ADAPTER
        final FamilyAdapter adapter = new FamilyAdapter();
        recyclerView.setAdapter(adapter);

        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            recyclerView.setBackgroundColor(Color.parseColor("#252624"));
        }



        //FACTORY TO INVOKE VIEW MODEL
        FamilyListViewModelFirebase.Factory factory = new FamilyListViewModelFirebase.Factory(
                getApplication());


        viewModelList = new ViewModelProvider(this, factory).get(FamilyListViewModelFirebase.class);
        viewModelList.getFamilies().observe(this, family_firebases -> {
            if (family_firebases != null) {
                families = family_firebases;
                adapter.submitList(families);
            }
        });





       //to delete with swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //we can swipe to right and left to delete


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                //update on left
                switch (direction){
                    case ItemTouchHelper.LEFT:

                        Intent intent = new Intent(SearchFamilyActivity.this, AddEditFamilyActivity.class);
                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILY, adapter.getFamilyAt(viewHolder.getAdapterPosition()).getFamilyName());
                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILYID, adapter.getFamilyAt(viewHolder.getAdapterPosition()).getFamilyId());

                        startActivityForResult(intent, EDIT_FAMILY_REQUEST);

                        adapter.notifyDataSetChanged();

                        break;

                    //delete on right
                    case ItemTouchHelper.RIGHT:


                        Family_Firebase family = adapter.getFamilyAt(viewHolder.getAdapterPosition());



                        //we get the position of the family at the chosen position.
                        viewModelList.deleteFamily(adapter.getFamilyAt(viewHolder.getAdapterPosition()), new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "Delete family: success");
                              //  Toast.makeText(SearchFamilyActivity.this, "Family deleted.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d(TAG, "Create family: failure", e);
                                //Toast.makeText(SearchFamilyActivity.this, "An error occured during deletion.", Toast.LENGTH_SHORT).show();
                            }
                        });


                        break;
                }


            }

        }).attachToRecyclerView(recyclerView); //attach to our list of family

        //handle the click when clicking on a family
        adapter.setOnItemClickListener(
                new FamilyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Family_Firebase family) {

                        Intent intent = new Intent(SearchFamilyActivity.this, SearchNameActivity.class);

                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILY, family.getFamilyName());
                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILYID, family.getFamilyId());
                        //start the menu of the chosen family
                        startActivity(intent);

                    }
                });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //insert a family
        if (requestCode == ADD_FAMILY_REQUEST && resultCode == RESULT_OK) {
            //get the string out of the edit text
            String family = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY);
            String familyId = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILYID);

            Family_Firebase familyObject = new Family_Firebase();

            familyObject.setFamilyName(family);

            viewModelList.createFamily(familyObject, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });

            Toast.makeText(this, "Family saved", Toast.LENGTH_SHORT).show();


            Toast.makeText(this, "Family updated", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_FAMILY_REQUEST && resultCode == RESULT_OK) {


            String familyId = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILYID);

            if (familyId == null) {

                Toast.makeText(this, "Family can't be updated", Toast.LENGTH_SHORT).show();
                return;

            }

            String familyName = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY);


            Family_Firebase familyObj = new Family_Firebase();
            familyObj.setFamilyName(familyName);

            familyObj.setFamilyId(familyId);

            viewModelList.updateFamily(familyObj, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });


            Toast.makeText(this, "Family updated", Toast.LENGTH_SHORT).show();

        }

        else
        {

            Toast.makeText(this, "Family not saved", Toast.LENGTH_SHORT).show();
        }

    }




    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_by_family, menu);


        return true;
    }



    //start settings when clicking on the settings icon and delete all functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, ActivitySettings.class);

        switch (item.getItemId()) {
            case R.id.back_to_settings:
                startActivity(intent);
                return true;

            case R.id.delete_all:


                viewModelList.deleteAllFamilies(new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                Toast.makeText(this,"All families deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
