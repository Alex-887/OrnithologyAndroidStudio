package com.example.firebaseAdapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebaseEntities.Bird_Firebase;
import com.example.firebaseEntities.Family_Firebase;
import com.example.ornithology_favre_berthouzoz.R;
import com.example.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter_Family<T> extends RecyclerView.Adapter<RecyclerAdapter_Family.ViewHolder> {

    private List<T> data;
    private RecyclerViewItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter_Family(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerAdapter_Family.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Family.ViewHolder holder, int position) {
        T item = data.get(position);

        if (item.getClass().equals(Bird_Firebase.class))
            holder.textView.setText(((Family_Firebase) item).getFamilyId());


    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter_Family.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter_Family.this.data instanceof Bird_Firebase) {
                        return ((Family_Firebase) RecyclerAdapter_Family.this.data.get(oldItemPosition)).getFamilyId().equals(((Family_Firebase) data.get(newItemPosition)).getFamilyId());
                    }
                    if (RecyclerAdapter_Family.this.data instanceof Bird_Firebase) {
                        return ((Family_Firebase) RecyclerAdapter_Family.this.data.get(oldItemPosition)).getFamilyId().equals(
                                ((Family_Firebase) data.get(newItemPosition)).getFamilyId());
                    }
                    return false;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter_Family.this.data instanceof Family_Firebase) {
                        Family_Firebase newAccount = (Family_Firebase) data.get(newItemPosition);
                        Family_Firebase oldAccount = (Family_Firebase) RecyclerAdapter_Family.this.data.get(newItemPosition);
                        return newAccount.getFamilyId().equals(oldAccount.getFamilyId())
                                && Objects.equals(newAccount.getFamily(), oldAccount.getFamily());
                    }
                    if (RecyclerAdapter_Family.this.data instanceof Family_Firebase) {
                        Family_Firebase newClient = (Family_Firebase) data.get(newItemPosition);
                        Family_Firebase oldClient = (Family_Firebase) RecyclerAdapter_Family.this.data.get(newItemPosition);
                        return Objects.equals(newClient.getFamilyId(), oldClient.getFamilyId())
                                && Objects.equals(newClient.getFamily(), oldClient.getFamily());
                    }
                    return false;
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
