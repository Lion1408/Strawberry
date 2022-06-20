package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityInforUserBinding;

public class InforUserActivity extends AppCompatActivity {
    ActivityInforUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInforUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backProfileUser.setOnClickListener(v -> {
            finish();
        });
        binding.editInforUser.setOnClickListener(v -> {
            Dialog dialog = new Dialog(InforUserActivity.this);
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
            });
            dialog.show();
        });

    }
}