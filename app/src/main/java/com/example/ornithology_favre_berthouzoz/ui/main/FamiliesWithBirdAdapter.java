package com.example.ornithology_favre_berthouzoz.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ornithology_favre_berthouzoz.R;
import com.example.pojo.FamiliesWithBirds;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class FamiliesWithBirdAdapter extends ListAdapter<FamiliesWithBirds, FamiliesWithBirdAdapter.FamiliesWithBirdHolder>  {



    //for the update
    private OnItemClickListener listener;

    public FamiliesWithBirdAdapter() {
        super(DIFF_CALLBACK);

    }


    //check if the items already exists

    private static final DiffUtil.ItemCallback<FamiliesWithBirds> DIFF_CALLBACK = new DiffUtil.ItemCallback<FamiliesWithBirds>() {
        @Override
        public boolean areItemsTheSame(@NonNull FamiliesWithBirds oldItem, @NonNull FamiliesWithBirds newItem) {
            return oldItem.family.getFamilyId() == newItem.family.getFamilyId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FamiliesWithBirds oldItem, @NonNull FamiliesWithBirds newItem) {
            return oldItem.family.getFamilyId().equals(newItem.family.getFamilyId())
                    && oldItem.family.getFamilyName().equals(newItem.family.getFamilyName()) ;
        }
    };



    @NonNull
    @Override
    public FamiliesWithBirdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bird_item, parent, false);

        return new FamiliesWithBirdHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamiliesWithBirdHolder holder, int position) {


        //set the right name and the right family at the bird in a list
        FamiliesWithBirds currentBird = getItem(position);

        holder.textViewFamily.setText(currentBird.family.getFamilyName());


        holder.textViewBird.setText(currentBird.birds.get(position).getName()); //get family id

    }



    //get the position of a selected bird
    public FamiliesWithBirds getFamiliesWithBirdAt(int position) {
        return getItem(position);
    }




    class FamiliesWithBirdHolder extends RecyclerView.ViewHolder {
        private TextView textViewBird;
        private TextView textViewFamily;

        public FamiliesWithBirdHolder(@NonNull View itemView) {
            super(itemView);
            textViewBird = itemView.findViewById(R.id.text_view_bird);
            textViewFamily = itemView.findViewById(R.id.text_view_family);


            //update -> catch the click // take the listener at the right position
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }



    //update
    public interface OnItemClickListener {
        void onItemClick(FamiliesWithBirds bird);
    }


    //catch the click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
