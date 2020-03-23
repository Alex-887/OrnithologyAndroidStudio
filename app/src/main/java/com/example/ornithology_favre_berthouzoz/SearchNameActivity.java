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
import com.example.room.Bird;
import com.example.room.Dao;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchNameActivity extends AppCompatActivity{

    public static final int ADD_BIRD_REQUEST = 1;
    public static final int EDIT_BIRD_REQUEST = 2;






    private BirdViewModel birdViewModel;


    private SearchView searchView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);

        //add button
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

        final BirdAdapter adapter = new BirdAdapter();
        recyclerView.setAdapter(adapter);

        birdViewModel = new ViewModelProvider(this).get(BirdViewModel.class);
        birdViewModel.getAllBirds().observe(this, new Observer<List<Bird>>() {

            @Override
            public void onChanged(@Nullable List<Bird> birds) { //everytime something changes, the adaptater is updated
                //update the recycler view
                adapter.submitList(birds);
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

                        Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
                        intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, adapter.getBirdAt(viewHolder.getAdapterPosition()).getIdBird());
                        intent.putExtra(AddEditBirdActivity.EXTRA_NAME, adapter.getBirdAt(viewHolder.getAdapterPosition()).getName());
                        intent.putExtra(AddEditBirdActivity.EXTRA_FAMILY, adapter.getBirdAt(viewHolder.getAdapterPosition()).getFamily());
                        intent.putExtra(AddEditBirdActivity.EXTRA_DESCRIPTION, adapter.getBirdAt(viewHolder.getAdapterPosition()).getDescription());
                        intent.putExtra(AddEditBirdActivity.EXTRA_BIOLOGY, adapter.getBirdAt(viewHolder.getAdapterPosition()).getBiology());
                        startActivityForResult(intent, EDIT_BIRD_REQUEST);

                        adapter.notifyDataSetChanged();

                        break;

                        //delete on right
                    case ItemTouchHelper.RIGHT:

                        //we get the position of the bird at the chosen position.
                        birdViewModel.deleteBird(adapter.getBirdAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(SearchNameActivity.this, "Bird deleted.", Toast.LENGTH_SHORT).show();

                        break;
                }


            }

        }).attachToRecyclerView(recyclerView); //attach to our list of birds

        //handle the click when clicking on a bird
        adapter.setOnItemClickListener(
                new BirdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bird bird) {


                Intent intent = new Intent(SearchNameActivity.this, TabbedInfoBird.class);

                //send the datas to the bird info tabbed activity
                intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, bird.getIdBird());
                intent.putExtra(AddEditBirdActivity.EXTRA_NAME, bird.getName());
                intent.putExtra(AddEditBirdActivity.EXTRA_FAMILY, bird.getFamily());
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


        //adding a new bird
        if (requestCode == ADD_BIRD_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);
            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);

            Bird bird = new Bird(name, family, description, biology);
            birdViewModel.insertBird(bird);

            Toast.makeText(this, "Bird saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_BIRD_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditBirdActivity.EXTRA_IDBIRD, -1);

            if (id == -1) {


                Toast.makeText(this, "Bird can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }


            //bird update
            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);
            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);

            //create a bird with new values
            Bird bird = new Bird(name, family, description, biology);
            bird.setIdBird(id);

            birdViewModel.updateBird(bird);

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


//        final MenuItem item = menu.findItem(R.id.recycler_view);
//        final SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });




        return true;
    }


    //start settings when clicking on the settings icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, SettingsActivity.class);

        switch (item.getItemId()) {
            case R.id.back_to_settings:
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
