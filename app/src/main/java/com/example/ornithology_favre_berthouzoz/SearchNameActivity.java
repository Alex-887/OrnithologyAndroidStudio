package com.example.ornithology_favre_berthouzoz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ViewModel.BirdViewModel;
import com.example.room.Bird;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchNameActivity extends AppCompatActivity {

    public static final int ADD_BIRD_REQUEST = 1;
    public static final int EDIT_BIRD_REQUEST = 2;

    private BirdViewModel birdViewModel;


    //ListView birds_array;
    //ArrayAdapter<String> adapter;
    //List<Bird> listBird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);


        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
                startActivityForResult(intent, ADD_BIRD_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BirdAdapter adapter = new BirdAdapter();
        recyclerView.setAdapter(adapter);

        birdViewModel = new ViewModelProvider(this).get(BirdViewModel.class);
        birdViewModel.getAllBirds().observe(this, new Observer<List<Bird>>() {

            @Override
            public void onChanged(@Nullable List<Bird> birds) { //everytime something changes, the adaptater is updated
                //update the recycler view
                adapter.setBirds(birds);
            }

        });


        //to delete with swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //we can swipe to right and left
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //we get the position of the bird at the chosen position.
                birdViewModel.delete(adapter.getBirdAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SearchNameActivity.this, "Bird deleted.", Toast.LENGTH_SHORT).show();

            }


        }).attachToRecyclerView(recyclerView); //attach to our list of birds


        //update

        adapter.setOnItemClickListener(new BirdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bird bird) {
                Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
                intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, bird.getIdBird());
                intent.putExtra(AddEditBirdActivity.EXTRA_NAME, bird.getName());
                intent.putExtra(AddEditBirdActivity.EXTRA_FAMILY, bird.getFamily());
                startActivityForResult(intent, EDIT_BIRD_REQUEST);


            }
        });


        //Bird.populateWithTest(AddBird.myDataBase);

        //with list inside "String"
        //ArrayList<String> arrayBird = new ArrayList<>();

        //get the birds in a list
        //List<Bird> listBird = AddBird.myDataBase.dao().getBirds();


//        System.out.println(listBird.get(0).getName());


        //get only the name of the bird in the array
//        for(int i = 0; i< listBird.size(); i++)
//        {
//            arrayBird.add(listBird.get(i).getName());
//        }

        //put the array on the list view
        //birds_array = (ListView) findViewById(R.id.birds_list_view);

        //arrayBird.addAll(Arrays.asList(getResources().getStringArray(R.array.birds_array)));

        //simple list
        //adapter = new ArrayAdapter<String>(
        //     SearchNameActivity.this,
        //       android.R.layout.simple_list_item_1,
        //         arrayBird
        // );

        //     birds_array.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BIRD_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);

            Bird bird = new Bird(name, family);
            birdViewModel.insertBird(bird);

            Toast.makeText(this, "Bird saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_BIRD_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditBirdActivity.EXTRA_IDBIRD, -1);

            if (id == -1) {
                Toast.makeText(this, "Bird can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);

            Bird bird = new Bird(name, family);
            bird.setIdBird(id);
            birdViewModel.update(bird);

            Toast.makeText(this, "Bird updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bird not saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_by_name, menu);
        return true;
    }

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

    //toolbar qui crash ------ à réparer

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_by_name, menu);
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
//         //       adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//
//    }


//    public void addBird(View addBirdView) {
//        Intent intent = new Intent(this, AddBird.class);
//
//        startActivity(intent);
//
//    }
//
//    public void infoBird(View addBirdView) {
//        Intent intent = new Intent(this, InfoBird.class);
//
//        startActivity(intent);
//
//    }


}
