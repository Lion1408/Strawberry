package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityInforUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InforUserActivity extends AppCompatActivity {
    ActivityInforUserBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInforUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user1 = getIntent().getParcelableExtra("User");
        Post post = getIntent().getParcelableExtra("Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child("users/idUser" + user1.getIdUser()).getValue(User.class);
                binding.gender.setText(getString(R.string.gender) + " " + (user.getGender()==null?"":user.getGender()));
                binding.birthday.setText(getString(R.string.birthday) + " " + (user.getBirthday()==null?"":user.getBirthday()));
                binding.place.setText(getString(R.string.place) + " " + (user.getPlace()==null?"":user.getPlace()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.backProfileUser.setOnClickListener(v -> {
            finish();
        });
        binding.editInforUser.setOnClickListener(v -> {
            Dialog dialog = new Dialog(InforUserActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_edit_infor_user);
            TextView confirmChange;
            EditText textgender, textbirthday, textplace;
            textgender = dialog.findViewById(R.id.textGender);
            textbirthday = dialog.findViewById(R.id.textBirthday);
            textplace = dialog.findViewById(R.id.textPlace);
            confirmChange = dialog.findViewById(R.id.confirmChange);
            confirmChange.setOnClickListener(vv -> {
                dialog.dismiss();
                binding.gender.setText(getString(R.string.gender) + " " + textgender.getText());
                binding.birthday.setText(getString(R.string.birthday) + " "+  textbirthday.getText());
                binding.place.setText(getString(R.string.place) + " " + textplace.getText());
                Constants.showToast("Cập nhật thành công", getApplicationContext());
                databaseReference.child("users/idUser" + user.getIdUser() + "/gender").setValue(textgender.getText().toString());
                databaseReference.child("users/idUser" + user.getIdUser() + "/birthday").setValue(textbirthday.getText().toString());
                databaseReference.child("users/idUser" + user.getIdUser() + "/place").setValue(textplace.getText().toString());

            });
            dialog.show();
        });
        if (!post.getIdUser().equals(post.getIdPost())) {
            binding.editInforUser.setVisibility(View.GONE);
        } else {
            binding.editInforUser.setVisibility(View.VISIBLE);
        }
    }
}