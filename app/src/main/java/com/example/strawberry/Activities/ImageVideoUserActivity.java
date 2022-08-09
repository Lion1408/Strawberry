package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Adapters.ImageAdapter;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityImageUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageVideoUserActivity extends AppCompatActivity {
    ActivityImageUserBinding binding;
    GridView gridView;
    List<String> list = new ArrayList<>();
    ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = getIntent().getParcelableExtra("User");
        Post post = getIntent().getParcelableExtra("Post");
        gridView = findViewById(R.id.gridview);
        binding.backProfileUser.setOnClickListener(v -> {
            finish();
        });
        adapter = new ImageAdapter(getApplicationContext(), list, R.layout.item_image);
        adapter.setImageOnClick(new ImageOnClick() {
            @Override
            public void onclickImage(String image) {
                Dialog dialog = new Dialog(ImageVideoUserActivity.this, R.style.Theme_Strawberry);
                dialog.setContentView(R.layout.dialog_show_image);
                ImageView img, backImage;
                img = dialog.findViewById(R.id.showImage);
                backImage = dialog.findViewById(R.id.backImage);
                Glide.with(img).load(image).into(img);
                backImage.setOnClickListener(v -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.imageUser.setText("Ảnh của " + ((post.getIdUser().equals(user.getIdUser()))?"bạn":post.getFullName()));
                list.clear();
                for (DataSnapshot i : snapshot.child("images/idUser" + post.getIdUser()).getChildren()) {
                    list.add(i.getValue(String.class));
                    System.out.println(i.getValue(String.class));
                }
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}