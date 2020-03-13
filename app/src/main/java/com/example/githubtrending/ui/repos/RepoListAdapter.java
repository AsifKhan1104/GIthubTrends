package com.example.githubtrending.ui.repos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.githubtrending.R;
import com.example.githubtrending.models.RepoResponse;
import com.example.githubtrending.ui.RepoDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepoListAdapter extends Adapter<RepoListAdapter.CustomViewHolder> implements Filterable {
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    public ArrayList<RepoResponse> mFilteredList;
    public ArrayList<RepoResponse> mList;

    public RepoListAdapter(Context context2, List<RepoResponse> list) {
        this.context = context2;
        this.mList = (ArrayList) list;
        this.mFilteredList = (ArrayList) list;
    }

    @NonNull
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_repo, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textViewTitle.setText(mFilteredList.get(position).getName());
        holder.textViewDesc.setText(mFilteredList.get(position).getDescription());
        holder.textViewAuthor.setText(mFilteredList.get(position).getAuthor());
        holder.textViewLang.setText(mFilteredList.get(position).getLanguage());
        holder.textViewStars.setText(String.valueOf(mFilteredList.get(position).getStars()));
        holder.textViewForks.setText(String.valueOf(mFilteredList.get(position).getForks()));

        // show repo image
        Picasso.get().load(mFilteredList.get(position).getAvatar())
                .into(holder.imageViewProfile);

        // show language color
        if (mFilteredList.get(position).getLanguage() != null) {
            GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.ic_circle);
            drawable.setColor(Color.parseColor(mFilteredList.get(position).getLanguageColor()));
            holder.imageViewLangColor.setImageDrawable(drawable);
        } else {
            holder.textViewLang.setVisibility(View.GONE);
            holder.imageViewLangColor.setVisibility(View.GONE);
        }

        // on view click
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RepoDetailActivity.class);
                intent.putExtra("repoList", mFilteredList.get(position));
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.mFilteredList.size();
    }

    public Filter getFilter() {
        return mFilter;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProfile, imageViewLangColor;
        public TextView textViewTitle, textViewDesc, textViewAuthor, textViewLang,
                textViewStars, textViewForks;
        public CardView cardView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageViewProfile = (ImageView) itemView.findViewById(R.id.item_profile_img);
            imageViewLangColor = (ImageView) itemView.findViewById(R.id.item_img_language);
            textViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            textViewDesc = (TextView) itemView.findViewById(R.id.item_desc);
            textViewAuthor = (TextView) itemView.findViewById(R.id.item_author);
            textViewLang = (TextView) itemView.findViewById(R.id.item_language);
            textViewStars = (TextView) itemView.findViewById(R.id.item_stars);
            textViewForks = (TextView) itemView.findViewById(R.id.item_fork);
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
                final ArrayList<RepoResponse> nlist = new ArrayList<RepoResponse>(count);

                String filterableString;
                for (int i = 0; i < count; i++) {
                    filterableString = "" + mFilteredList.get(i).getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        RepoResponse response = mFilteredList.get(i);
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
            mFilteredList = (ArrayList<RepoResponse>) results.values;
            notifyDataSetChanged();
        }

    }

}
