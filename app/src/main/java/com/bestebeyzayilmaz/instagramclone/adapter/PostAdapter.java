package com.bestebeyzayilmaz.instagramclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bestebeyzayilmaz.instagramclone.databinding.RecyclerRowBinding;
import com.bestebeyzayilmaz.instagramclone.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    //3
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    //4
    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        holder.recyclerRowBinding.recyclerViewUserEmailText.setText(postArrayList.get(position).email);
        holder.recyclerRowBinding.recyclerViewCommentText.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);
    }

    //1
    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    //2
    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
