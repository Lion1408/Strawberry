package com.example.strawberry.Adapters;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Model.Image;
import com.example.strawberry.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    ImageOnClick imageOnClick;

    public ImageOnClick getImageOnClick() {
        return imageOnClick;
    }

    public void setImageOnClick(ImageOnClick imageOnClick) {
        this.imageOnClick = imageOnClick;
    }

    Context context;
    List <String> list;
    private Integer layout;

    public ImageAdapter() {
    }

    public ImageAdapter(Context context, List<String> list, Integer layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, layout, null);
        ImageView img;
        img = view.findViewById(R.id.itemImageUser);
        Glide.with(img).load(list.get(i)).into(img);
        img.setOnClickListener(v -> {
            imageOnClick.onclickImage(list.get(i));
        });
        return view;
    }
}
