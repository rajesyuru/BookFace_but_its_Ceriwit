package com.example.bookface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookFaceAdapter extends RecyclerView.Adapter<BookFaceAdapter.ViewHolder> {

    Context mContext;
    int mResource;
    ArrayList<BookFace> mBookface;

    public BookFaceAdapter(Context context, int resource, ArrayList<BookFace> bookFaces) {
        mContext = context;
        mResource = resource;
        mBookface = bookFaces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookFace bookFace = mBookface.get(position);

        holder.tvUsername.setText(bookFace.getUsername());
        holder.tvMessage.setText(bookFace.getMessage());

    }

    @Override
    public int getItemCount() {
        return mBookface.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
