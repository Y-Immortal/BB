package edu.wschina.b66;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.wschina.b66.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}