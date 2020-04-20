package com.example.ornithology_favre_berthouzoz.ui.main;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.firebaseEntities.Family_Firebase;
import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseViewModel.BirdListViewModelFirebase;
import com.example.firebaseViewModel.FamilyListViewModelFirebase;
import com.example.ornithology_favre_berthouzoz.R;
import com.example.pojo.FamiliesWithBirds;
import com.example.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SearchNameActivity extends AppCompatActivity{

    public static final int ADD_BIRD_REQUEST = 1;
    public static final int EDIT_BIRD_REQUEST = 2;

    private static final String TAG = "BirdsActivity";

    private List<Bird_Firebase> birds;
   // private List<FamiliesWithBirds> familiesWithBirds;
    private BirdListViewModelFirebase birdListViewModelFirebase;
    private SortedMap<Family_Firebase, List<Bird_Firebase>> familyEntityMultimap;

    private String currentFamily;




    public SearchNameActivity() {
    }

    private void setupViewModels() {


        //FACTORY TO INVOKE VIEW MODEL
        BirdListViewModelFirebase.Factory factory = new BirdListViewModelFirebase.Factory(
                getApplication(), "");

        birdListViewModelFirebase = new ViewModelProvider(this, factory).get(BirdListViewModelFirebase.class);

        birdListViewModelFirebase.getAllBirds().observe(this, familiesWithBirds -> {
            if (familiesWithBirds != null) {
                setupMap(familiesWithBirds);
            }
        });
    }



    private void setupMap(List<FamiliesWithBirds> familiesWithBirds) {
        familyEntityMultimap = new TreeMap<>();
        for (FamiliesWithBirds fWB : familiesWithBirds) {
            familyEntityMultimap.put(fWB.family, fWB.birds);
        }




    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent currentintent = getIntent();
        currentFamily = currentintent.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILYID);



        //theme
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_search_name);


        //add button to start add edit bird activity
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
                startActivityForResult(intent, ADD_BIRD_REQUEST);
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //RECYCLER VIEW ADAPTER
        final BirdAdapter adapter = new BirdAdapter();
        final FamiliesWithBirdAdapter familiesWithBirdAdapter = new FamiliesWithBirdAdapter();
        recyclerView.setAdapter(adapter);

        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            recyclerView.setBackgroundColor(Color.parseColor("#252624"));
        }

        Intent intent = getIntent();

        //if there is something inside EXTRA_FAMILY, it means it comes from the activity of searching a family

            //String currentFamily = intent.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILYID);

            //FACTORY TO INVOKE VIEW MODEL
            BirdListViewModelFirebase.Factory factory = new BirdListViewModelFirebase.Factory(
                    getApplication(), currentFamily);


            birdListViewModelFirebase = new ViewModelProvider(this, factory).get(BirdListViewModelFirebase.class);
            birdListViewModelFirebase.getBirdsFromName().observe(this, bird_firebases -> {
                if (bird_firebases != null) {
                    birds = bird_firebases;
                    adapter.submitList(birds);
                }
            });






        //to delete and update with swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //we can swipe to right and left to delete


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


          //----------------UPDATE-------------------
                switch (direction){
                    case ItemTouchHelper.LEFT:

                        Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
                        intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, adapter.getBirdAt(viewHolder.getAdapterPosition()).getId());
                        intent.putExtra(AddEditBirdActivity.EXTRA_NAME, adapter.getBirdAt(viewHolder.getAdapterPosition()).getName());
                        intent.putExtra(AddEditBirdActivity.EXTRA_DESCRIPTION, adapter.getBirdAt(viewHolder.getAdapterPosition()).getDescription());
                        intent.putExtra(AddEditBirdActivity.EXTRA_BIOLOGY, adapter.getBirdAt(viewHolder.getAdapterPosition()).getBiology());

                        startActivityForResult(intent, EDIT_BIRD_REQUEST);

                        adapter.notifyDataSetChanged();

                        break;

           //----------------DELETE-------------------
                    case ItemTouchHelper.RIGHT:

                        //we get the position of the bird at the chosen position.
                        birdListViewModelFirebase.deleteBird(adapter.getBirdAt(viewHolder.getAdapterPosition()), new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        }, currentFamily);
                        Toast.makeText(SearchNameActivity.this, "Bird deleted.", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

        }).attachToRecyclerView(recyclerView); //attach to our list of birds


        //handle the click when clicking on a bird
        adapter.setOnItemClickListener(
                new BirdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bird_Firebase bird) {


                Intent intent = new Intent(SearchNameActivity.this, InfoBirdActivity.class);

                //send the datas to the bird info activity
                intent.putExtra(AddEditBirdActivity.EXTRA_NAME, bird.getName());
                intent.putExtra(AddEditBirdActivity.EXTRA_DESCRIPTION, bird.getDescription());
                intent.putExtra(AddEditBirdActivity.EXTRA_BIOLOGY, bird.getBiology());

                //start the menu of the chosen bird
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //----------------ADD-------------------
        if (requestCode == ADD_BIRD_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);



            Bird_Firebase bird = new Bird_Firebase(name, description, biology);
            bird.setFamilyId(currentFamily);

            birdListViewModelFirebase.createBird(bird, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            }, currentFamily);


            Toast.makeText(this, "Bird saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_BIRD_REQUEST && resultCode == RESULT_OK) {



            //----------------UPDATE-------------------

            String id = data.getStringExtra(AddEditBirdActivity.EXTRA_IDBIRD);
            if (id == null) {

                Toast.makeText(this, "Bird can't be updated", Toast.LENGTH_SHORT).show();
                return;

            }

            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);


            //create a bird with new value
            Bird_Firebase bird = new Bird_Firebase(name, description, biology);
            bird.setId(id);
            bird.setFamilyId(currentFamily);


            birdListViewModelFirebase.updateBird(bird, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            }, currentFamily);

            Toast.makeText(this, "Bird updated", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Bird not saved", Toast.LENGTH_SHORT).show();

        }

    }



    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_by_name, menu);


        return true;
    }


    //start settings when clicking on the settings icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, ActivitySettings.class);

        switch (item.getItemId()) {
            case R.id.back_to_settings:
                startActivity(intent);
                return true;

            case R.id.delete_all:
                birdListViewModelFirebase.deleteAllBirds(new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                }, currentFamily);


                Toast.makeText(this,"All birds deleted", Toast.LENGTH_SHORT).show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
