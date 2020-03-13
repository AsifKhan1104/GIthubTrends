package com.example.githubtrending.ui.devs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.githubtrending.R;
import com.example.githubtrending.models.DevResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DevsListAdapter extends Adapter<DevsListAdapter.CustomViewHolder> implements Filterable {
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    public ArrayList<DevResponse> mFilteredList;
    public ArrayList<DevResponse> mList;

    public DevsListAdapter(Context context2, List<DevResponse> list) {
        this.context = context2;
        this.mList = (ArrayList) list;
        this.mFilteredList = (ArrayList) list;
    }

    @NonNull
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_devs, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textViewTitle.setText(((DevResponse) this.mFilteredList.get(position)).getName());
        holder.textViewDesc.setText(((DevResponse) this.mFilteredList.get(position)).getUrl());
        holder.textViewAuthor.setText(((DevResponse) this.mFilteredList.get(position)).getUsername());
        holder.textViewRepoName.setText(((DevResponse) this.mFilteredList.get(position)).getRepo().getName());
        holder.textViewRepoDesc.setText(((DevResponse) this.mFilteredList.get(position)).getRepo().getDescription());
        Picasso.get().load(mFilteredList.get(position).getAvatar())
                .into(holder.imageViewProfile);
    }

    public int getItemCount() {
        return this.mFilteredList.size();
    }

    public Filter getFilter() {
        return mFilter;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProfile;
        public TextView textViewTitle, textViewDesc, textViewAuthor, textViewRepoName, textViewRepoDesc;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.imageViewProfile = (ImageView) itemView.findViewById(R.id.item_profile_img);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            this.textViewDesc = (TextView) itemView.findViewById(R.id.item_desc);
            this.textViewAuthor = (TextView) itemView.findViewById(R.id.item_author);
            this.textViewRepoName = (TextView) itemView.findViewById(R.id.item_repo_name);
            this.textViewRepoDesc = (TextView) itemView.findViewById(R.id.item_repo_desc);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.count = mList.size();
                results.values = mList;
            } else {
                int count = mFilteredList.size();
                final ArrayList<DevResponse> nlist = new ArrayList<DevResponse>(count);

                String filterableString;
                for (int i = 0; i < count; i++) {
                    filterableString = "" + mFilteredList.get(i).getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        DevResponse response = mFilteredList.get(i);
                        nlist.add(response);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredList = (ArrayList<DevResponse>) results.values;
            notifyDataSetChanged();
        }

    }

}
