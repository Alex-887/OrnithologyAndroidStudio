package com.example.ornithology_favre_berthouzoz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ViewModel.FamilyViewModel;
import com.example.room.Family;
import com.example.room.FamilyDao;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

class FamilyAdapter extends ListAdapter<Family, FamilyAdapter.FamilyHolder> {

    private FamilyDao dao;


    private FamilyViewModel familyViewModel;

//    private List<Bird> birdList = (List<Bird>) birdViewModel.getAllBirds();
//    private List<Bird> birdListFull;




    //for the update
    private FamilyAdapter.OnItemClickListener listener;

    public FamilyAdapter() {
        super(DIFF_CALLBACK);


    }
    

    private static final DiffUtil.ItemCallback<Family> DIFF_CALLBACK = new DiffUtil.ItemCallback<Family>() {
        @Override
        public boolean areItemsTheSame(@NonNull Family oldItem, @NonNull Family newItem) {
            return oldItem.getFamily() == newItem.getFamily(); //if the id are the same, we have the same object twice
        }

        @Override
        public boolean areContentsTheSame(@NonNull Family oldItem, @NonNull Family newItem) {
            return  oldItem.getFamily().equals(newItem.getFamily());
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

        Family currentFamily = getItem(position);
        holder.textViewFamily.setText(currentFamily.getFamily());



    }



    //get the position of a selected bird
    public Family getFamilyAt(int position) {
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
        void onItemClick(Family family);
    }


    //catch the click
    public void setOnItemClickListener(FamilyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
