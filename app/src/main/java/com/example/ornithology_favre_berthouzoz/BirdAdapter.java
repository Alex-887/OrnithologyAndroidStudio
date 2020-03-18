package com.example.ornithology_favre_berthouzoz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.room.Bird;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.BirdHolder> {


    private List<Bird> birds = new ArrayList<>();

    //for the update
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BirdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bird_item, parent, false);

        return new BirdHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BirdHolder holder, int position) {

        Bird currentBird = birds.get(position);
        holder.textViewBird.setText(currentBird.getName());
        holder.textViewFamily.setText(currentBird.getFamily());

    }

    @Override
    public int getItemCount() {
        return birds.size();
    }

    public void setBirds(List<Bird> birds) {
        this.birds = birds;
        notifyDataSetChanged();
    }

    //get the position of a selected bird
    public Bird getBirdAt(int position) {
        return birds.get(position);
    }

    class BirdHolder extends RecyclerView.ViewHolder {
        private TextView textViewBird;
        private TextView textViewFamily;

        public BirdHolder(@NonNull View itemView) {
            super(itemView);
            textViewBird = itemView.findViewById(R.id.text_view_bird);
            textViewFamily = itemView.findViewById(R.id.text_view_family);


            //update
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(birds.get(position));
                    }
                }
            });
        }
    }


    //update
    public interface OnItemClickListener {
        void onItemClick(Bird bird);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }


}
