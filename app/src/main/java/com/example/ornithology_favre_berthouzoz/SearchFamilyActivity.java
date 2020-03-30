package com.example.ornithology_favre_berthouzoz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.ViewModel.BirdViewModel;
import com.example.ViewModel.FamilyViewModel;
import com.example.room.Bird;
import com.example.room.Family;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFamilyActivity extends AppCompatActivity {

    public static final int ADD_FAMILY_REQUEST = 1;
    public static final int EDIT_FAMILY_REQUEST = 2;


    private BirdViewModel birdViewModel;
    private FamilyViewModel familyViewModel;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final FamilyAdapter adapter = new FamilyAdapter();
        recyclerView.setAdapter(adapter);

        final BirdAdapter adapterBird = new BirdAdapter();



        familyViewModel = new ViewModelProvider(this).get(FamilyViewModel.class);
        familyViewModel.getAllFamilies().observe(this, new Observer<List<Family>>() {

            @Override
            public void onChanged(@Nullable List<Family> families) { //everytime something changes, the adaptater is updated
                //update the recycler view
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
                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILY, adapter.getFamilyAt(viewHolder.getAdapterPosition()).getFamily());

                        startActivityForResult(intent, EDIT_FAMILY_REQUEST);

                        adapter.notifyDataSetChanged();

                        break;

                    //delete on right
                    case ItemTouchHelper.RIGHT:

                        //we get the position of the family at the chosen position.
                        familyViewModel.deleteFamily(adapter.getFamilyAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(SearchFamilyActivity.this, "Family deleted.", Toast.LENGTH_SHORT).show();

                        break;
                }


            }

        }).attachToRecyclerView(recyclerView); //attach to our list of family

        //handle the click when clicking on a family
        adapter.setOnItemClickListener(
                new FamilyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Family family) {

                        Intent intent = new Intent(SearchFamilyActivity.this, SearchNameActivity.class);

                        intent.putExtra(AddEditFamilyActivity.EXTRA_FAMILY, family.getFamily());

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


            Family familyObject = new Family(family);
            familyViewModel.insertFamily(familyObject);

            Toast.makeText(this, "Family saved", Toast.LENGTH_SHORT).show();



        } else if (requestCode == EDIT_FAMILY_REQUEST && resultCode == RESULT_OK) {


            String family = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY);


            if (AddEditFamilyActivity.EXTRA_FAMILY.equals(null)) {

                Toast.makeText(this, "Bird can't be updated", Toast.LENGTH_SHORT).show();
                return;

            }

            //update
            String fam = data.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY);

            Family familyObj = new Family(fam);

            familyViewModel.updateFamily(familyObj);


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

        Intent intent = new Intent(this, SettingsActivity.class);

        switch (item.getItemId()) {
            case R.id.back_to_settings:
                startActivity(intent);
                return true;

            case R.id.delete_all:
                familyViewModel.deleteAllFamilies();
                Toast.makeText(this,"All families deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
