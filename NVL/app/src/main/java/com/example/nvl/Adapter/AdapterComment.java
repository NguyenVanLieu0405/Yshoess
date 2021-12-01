package com.example.nvl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nvl.Class.Comment;
import com.example.nvl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterComment extends BaseAdapter {
    List<Comment>comments;
    Context context;
    public AdapterComment(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comment,parent,false);
//        ImageView img = view.findViewById(R.id.imgItemComment);
        TextView tvName = view.findViewById(R.id.tvNameComment);
        TextView tvCmt=view.findViewById(R.id.tvComment);
//        Picasso.with(context).load(comments.get(position).getPhotoUrl()).into(img);
        tvName.setText("Người dùng: "+comments.get(position).getName());
        tvCmt.setText(comments.get(position).getComment());
        return view;
    }
}
