package com.example.ornithology_favre_berthouzoz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ViewModel.BirdViewModel;
import com.example.firebaseEntities.Bird_Firebase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class BirdAdapter extends ListAdapter<Bird_Firebase, BirdAdapter.BirdHolder>  {




    private BirdViewModel birdViewModel;

//    private List<Bird> birdList = (List<Bird>) birdViewModel.getAllBirds();
//    private List<Bird> birdListFull;


    //for the update
    private OnItemClickListener listener;

    public BirdAdapter() {
        super(DIFF_CALLBACK);

    }


    //check if the items already exists

    private static final DiffUtil.ItemCallback<Bird_Firebase> DIFF_CALLBACK = new DiffUtil.ItemCallback<Bird_Firebase>() {
        @Override
        public boolean areItemsTheSame(@NonNull Bird_Firebase oldItem, @NonNull Bird_Firebase newItem) {
            return oldItem.getId() == newItem.getId(); //if the id are the same, we have the same object twice
        }

        @Override
        public boolean areContentsTheSame(@NonNull Bird_Firebase oldItem, @NonNull Bird_Firebase newItem) {
            return oldItem.getFamilyId().equals(newItem.getFamilyId()) && oldItem.getName()
                    .equals(newItem.getName()) ;
        }
    };



    @NonNull
    @Override
    public BirdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bird_item, parent, false);

        return new BirdHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BirdHolder holder, int position) {


        //set the right name and the right family at the bird in a list
        Bird_Firebase currentBird = getItem(position);

        holder.textViewBird.setText(currentBird.getName());

       // holder.textViewFamily.setText(currentBird.getFamily()); //get family id

    }



    //get the position of a selected bird
    public Bird_Firebase getBirdAt(int position) {
        return getItem(position);
    }


    class BirdHolder extends RecyclerView.ViewHolder {
        private TextView textViewBird;
        private TextView textViewFamily;

        public BirdHolder(@NonNull View itemView) {
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


//    //search bar
//    @Override
//    public Filter getFilter() {
//
//        return birdFilter;
//    }

//    private Filter birdFilter = new Filter(){
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Bird> filteredList = new ArrayList<>();
//
//            if(constraint ==null || constraint.length()==0){
//                filteredList.addAll(birdListFull);
//            } else{
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for(Bird bird : birdListFull){
//                    if(bird.getName().toLowerCase().contains(filterPattern)){
//                        filteredList.add(bird);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            birdList.clear();
//            birdList.addAll((List)results.values);
//            notifyDataSetChanged();
//
//        }
//    };


    //update
    public interface OnItemClickListener {
        void onItemClick(Bird_Firebase bird);
    }


    //catch the click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
