package com.example.ornithology_favre_berthouzoz.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebaseEntities.Family_Firebase;
import com.example.ornithology_favre_berthouzoz.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

class FamilyAdapter extends ListAdapter<Family_Firebase, FamilyAdapter.FamilyHolder> {


    //for the update
    private FamilyAdapter.OnItemClickListener listener;

    public FamilyAdapter() {
        super(DIFF_CALLBACK);

    }


    private static final DiffUtil.ItemCallback<Family_Firebase> DIFF_CALLBACK = new DiffUtil.ItemCallback<Family_Firebase>() {
        @Override
        public boolean areItemsTheSame(@NonNull Family_Firebase oldItem, @NonNull Family_Firebase newItem) {
            return oldItem.getFamilyName() == newItem.getFamilyName(); //if the id are the same, we have the same object twice
        }

        @Override
        public boolean areContentsTheSame(@NonNull Family_Firebase oldItem, @NonNull Family_Firebase newItem) {
            return  oldItem.getFamilyId().equals(newItem.getFamilyId());
        }
    };


    @NonNull
    @Override
    public FamilyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_item, parent, false);

        return new FamilyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyHolder holder, int position) {

        Family_Firebase currentFamily = getItem(position);
        holder.textViewFamily.setText(currentFamily.getFamilyName());



    }



    //get the position of a selected bird
    public Family_Firebase getFamilyAt(int position) {
        return getItem(position);
    }



    class FamilyHolder extends RecyclerView.ViewHolder {
        private TextView textViewFamily;

        public FamilyHolder(@NonNull View itemView) {
            super(itemView);
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
        void onItemClick(Family_Firebase family);
    }


    //catch the click
    public void setOnItemClickListener(FamilyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
