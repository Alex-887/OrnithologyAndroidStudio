package com.example.ornithology_favre_berthouzoz;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.firebaseDatabase.FirebaseDatabaseHelper;
import com.example.firebaseRepository.BirdRepositoryFirebase;
import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseAdapter.RecyclerAdapter_Bird;
import com.example.firebaseAdapter.RecyclerView_Bird;
import com.example.firebaseViewModel.BirdViewModelFirebase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchNameActivity extends AppCompatActivity{

    public static final int ADD_BIRD_REQUEST = 1;
    public static final int EDIT_BIRD_REQUEST = 2;

    private static final String TAG = "AccountsActivity";

    private List<Bird_Firebase> birds;
    private BirdViewModelFirebase birdViewModel;
    private SearchView searchView;
    private String family;
    private BirdRepositoryFirebase birdrepo;
    private BirdAdapter birdAdapter;
    private RecyclerAdapter_Bird<Bird_Firebase> adapter;

    private RecyclerView mRecyclerView;

    public SearchNameActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);

//        RecyclerView.Adapter adapter = new Adapter();
//        mRecyclerView.setAdapter(adapter);





//        birds = new ArrayList<>();
//        adapter = new RecyclerAdapter_Bird<>(new RecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.d(TAG, "clicked position:" + position);
//                Log.d(TAG, "clicked on: " + birds.get(position).getName());
//
//                Intent intent = new Intent(SearchNameActivity.this, InfoBirdActivity.class);
//                intent.putExtra("birdId", birds.get(position).getId());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(View v, int position) {
//                Log.d(TAG, "longClicked position:" + position);
//                Log.d(TAG, "longClicked on: " + birds.get(position).getName());
//
//                //createDeleteDialog(position);
//            }
//
//    });




        new FirebaseDatabaseHelper().readBirds(new FirebaseDatabaseHelper.DataBirdsStatus() {
            @Override
            public void DataIsLoaded(List<Bird_Firebase> birds, List<String> keys) {
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                new RecyclerView_Bird().setConfig(mRecyclerView, SearchNameActivity.this,
                        birds, keys);



            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            mRecyclerView.setBackgroundColor(Color.parseColor("#252624"));
        }




       // birdViewModel = new ViewModelProvider(this).get(BirdViewModelFirebase.class);




        Intent intent = getIntent();

        //if there is something inside EXTRA_FAMILY, it means it comes from the activity of searching a family
//        if(intent.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY) != null){
//
//            String currentFamily = intent.getStringExtra(AddEditFamilyActivity.EXTRA_FAMILY);
//
//          BirdViewModelFactory factory = new BirdViewModelFactory(this.getApplication(), currentFamily);
//
//           birdViewModel = new ViewModelProvider(this, factory).get(BirdViewModel.class);
//
//            birdViewModel.getAllBirdsFromFamily(currentFamily).observe(this, new Observer<List<Bird>>() {
//
//                List<Bird> birdsList = adapter.getCurrentList();
//
//                @Override
//                public void onChanged(@Nullable List<Bird> birds) {
//
//                    adapter.submitList(birds);
//
//                }
//
//            });
//
//
//
//        }
//
//        //if the user click directly on the button search by name -> means he didn't come from another activity
//        else{
//
            //family is null cause it is not used, getAllBirds() requires no arguments
//         BirdViewModelFactory factory = new BirdViewModelFactory(this.getApplication(), "");
//
//         birdViewModel = new ViewModelProvider(this, factory).get(BirdViewModel.class);
//
//            birdViewModel.getBirds().observe(this, new Observer<List<Bird_Firebase>>() {
//
//            @Override
//            public void onChanged(@Nullable List<Bird_Firebase> birds) { //everytime something changes, the adapter is updated
//                //update the recycler view
//                adapter.submitList(birds);
//            }
//
//        });
//
//       }



        //to delete and update with swipe
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //we can swipe to right and left to delete
//
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }

//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//
//            //update on left
//                switch (direction){
//                    case ItemTouchHelper.LEFT:
//
//                        Intent intent = new Intent(SearchNameActivity.this, AddEditBirdActivity.class);
//                        intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, adapter.getBirdAt(viewHolder.getAdapterPosition()).getId());
//                        intent.putExtra(AddEditBirdActivity.EXTRA_NAME, adapter.getBirdAt(viewHolder.getAdapterPosition()).getName());
//                        intent.putExtra(AddEditBirdActivity.EXTRA_FAMILY, adapter.getBirdAt(viewHolder.getAdapterPosition()).getFamily());
//                        intent.putExtra(AddEditBirdActivity.EXTRA_DESCRIPTION, adapter.getBirdAt(viewHolder.getAdapterPosition()).getDescription());
//                        intent.putExtra(AddEditBirdActivity.EXTRA_BIOLOGY, adapter.getBirdAt(viewHolder.getAdapterPosition()).getBiology());
//
//                        startActivityForResult(intent, EDIT_BIRD_REQUEST);
//
//                        adapter.notifyDataSetChanged();
//
//                        break;
//
//                        //delete on right
//                    case ItemTouchHelper.RIGHT:
//
//                        //we get the position of the bird at the chosen position.
//                        birdViewModel.deleteBird(adapter.getBirdAt(viewHolder.getAdapterPosition()));
//                        Toast.makeText(SearchNameActivity.this, "Bird deleted.", Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//            }
//
//        }).attachToRecyclerView(mRecyclerView); //attach to our list of birds

        //handle the click when clicking on a bird
//        adapter.setOnItemClickListener(
//                new BirdAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Bird bird) {
//
//
//                Intent intent = new Intent(SearchNameActivity.this, InfoBirdActivity.class);
//
//                //send the datas to the bird info tabbed activity
//                intent.putExtra(AddEditBirdActivity.EXTRA_IDBIRD, bird.getId());
//                intent.putExtra(AddEditBirdActivity.EXTRA_NAME, bird.getName());
//                intent.putExtra(AddEditBirdActivity.EXTRA_FAMILY, bird.getFamily());
//                intent.putExtra(AddEditBirdActivity.EXTRA_DESCRIPTION, bird.getDescription());
//                intent.putExtra(AddEditBirdActivity.EXTRA_BIOLOGY, bird.getBiology());
//
//                //start the menu of the chosen bird
//                startActivity(intent);
//
//            }
//        });
//    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //int familyId = data.getIntExtra(AddEditBirdActivity.EXTRA_FAMILYID, -1);
//
//
//        //adding a new bird
//        if (requestCode == ADD_BIRD_REQUEST && resultCode == RESULT_OK) {
//            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
//            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);
//            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
//            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);
//
//            Family familyObj = new Family(family);
//            familyViewModel.insertFamily(familyObj);
//
//            Bird bird = new Bird(name, family, description, biology);
//            birdViewModel.insertBird(bird);
//
//
//            Toast.makeText(this, "Bird saved", Toast.LENGTH_SHORT).show();
//
//        } else if (requestCode == EDIT_BIRD_REQUEST && resultCode == RESULT_OK) {
//
//
//             //familyId = data.getIntExtra(AddEditBirdActivity.EXTRA_FAMILYID, -1);
//            int id = data.getIntExtra(AddEditBirdActivity.EXTRA_IDBIRD, -1);
//
//            if (id == -1) {
//
//                Toast.makeText(this, "Bird can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//
//            }
//
//            //bird update
//            String name = data.getStringExtra(AddEditBirdActivity.EXTRA_NAME);
//            String family = data.getStringExtra(AddEditBirdActivity.EXTRA_FAMILY);
//            String biology = data.getStringExtra(AddEditBirdActivity.EXTRA_BIOLOGY);
//            String description = data.getStringExtra(AddEditBirdActivity.EXTRA_DESCRIPTION);
//
//
//            //create a family if it's not already there
//            Family familyObject = new Family(family);
//            familyViewModel.updateFamily(familyObject);
//
//            //create a bird with new value
//            Bird bird = new Bird(name, family, description, biology);
//            bird.setId(id);
//
//
//            birdViewModel.updateBird(bird);
//
//            Toast.makeText(this, "Bird updated", Toast.LENGTH_SHORT).show();
//
//        } else {
//
//            Toast.makeText(this, "Bird not saved", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }



//
//    //toolbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.search_by_name, menu);
//
//
//        return true;
//    }
//
//
//    //start settings when clicking on the settings icon
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        Intent intent = new Intent(this, ActivitySettings.class);
//
//        switch (item.getItemId()) {
//            case R.id.back_to_settings:
//                startActivity(intent);
//                return true;
//
//            case R.id.delete_all:
//                birdViewModel.deleteAllBirds();
//                Toast.makeText(this,"All birds deleted", Toast.LENGTH_SHORT).show();
//                return true;
//
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }




    }
}
