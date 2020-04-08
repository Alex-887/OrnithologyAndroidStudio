package com.example.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebase.Bird_Firebase;
import com.example.ornithology_favre_berthouzoz.R;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_Bird  {

    private Context mContext;

    private BirdsAdapter mBirdsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Bird_Firebase> birds, List<String> keys){
        mContext = context;
        mBirdsAdapter = new BirdsAdapter(birds, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBirdsAdapter);
    }




    class BirdItemView extends RecyclerView.ViewHolder{

        private TextView birdName;

        private String key;




    public BirdItemView(ViewGroup parent){
        super(LayoutInflater.from(mContext).
        inflate(R.layout.bird_item, parent, false));

        birdName = (TextView) itemView.findViewById(R.id.text_view_bird);


    }

    public void bind(Bird_Firebase bird, String key){


        birdName.setText(bird.getName());
        this.key = key;

    }

    }

    class BirdsAdapter extends RecyclerView.Adapter<BirdItemView>{
        private List<Bird_Firebase> mBirdsList;
        private List<String> mKeys;

        public BirdsAdapter(List<Bird_Firebase> mBirdsList, List<String> mKeys){
            this.mBirdsList = mBirdsList;
            this.mKeys = mKeys;
        }


        @NonNull
        @Override
        public BirdItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BirdItemView(parent);

        }

        @Override
        public void onBindViewHolder(@NonNull BirdItemView holder, int position) {
            holder.bind(mBirdsList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBirdsList.size();
        }
    }





}
