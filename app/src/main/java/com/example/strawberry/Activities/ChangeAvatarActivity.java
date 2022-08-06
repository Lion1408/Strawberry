package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityChangeAvatarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeAvatarActivity extends AppCompatActivity {
    ActivityChangeAvatarBinding binding;
    Uri imageUri = null;
    Integer n = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = getIntent().getParcelableExtra("User");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = 0;
                while (snapshot.child("posts/post" + n).getValue() != null) {
                    n++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.post.setOnClickListener(v -> {
            if (imageUri == null) {
                Constants.showToast("Chưa chọn ảnh!", getApplicationContext());
            } else {
                Post post = new Post();
                Date date = new Date();
                post.setContent("Cập nhật ảnh đại diện");
                post.setComment(0);
                post.setIdUser(user.getIdUser());
                post.setReaction(0);
                post.setTime(date.getTime() + "");
                post.setLinkAvt(user.getLinkAvt());
                post.setLinkVideo("null");
                post.setLinkImage("null");
                post.setFullName(user.getFullName());
                if (imageUri != null) {
                    StorageReference storageReference;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    Date now = new Date();
                    String fileName = formatter.format(now);
                    storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
                    storageReference.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            databaseReference.child("users/idUser" + user.getIdUser() + "/linkAvt").setValue(uri.toString());
                                            post.setLinkImage(uri.toString());
                                            post.setIdPost(n);
                                            databaseReference.child("posts/post" + n).setValue(post);
                                            databaseReference.child("userPosts/user" + user.getIdUser() + "/post" + n).setValue(post);
                                            databaseReference.child("images/idUser" + user.getIdUser() + "/image" + n).setValue(uri.toString());
                                            databaseReference.child("notifications/post" + post.getIdPost()).setValue(post);
                                            finish();
                                            Constants.showToast("Cập nhật ảnh đại diện thành công!", getApplicationContext());

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } else {
                    post.setIdPost(n);
                    databaseReference.child("posts/" + "post" + n).setValue(post);
                    databaseReference.child("userPosts/user" + user.getIdUser() + "/post" + n).setValue(post);
                    finish();
                    Constants.showToast("Đăng bài thành công!", getApplicationContext());
                }
            }

        });

        binding.backHome.setOnClickListener(v -> {
            finish();
        });

        binding.selectImage.setOnClickListener(v -> {
            selectImage();
        });

        binding.imageview.setOnClickListener(v -> {
            selectImage();
        });

    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            binding.imageview.setImageURI(imageUri);
            binding.selectImage.setVisibility(View.GONE);
        }
    }
}