package edu.wschina.b66.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.wschina.b66.R;
import edu.wschina.b66.databinding.FragmentMainBinding;
import edu.wschina.b66.databinding.FragmentMyBinding;
import edu.wschina.b66.databinding.ItemDialogBinding;

public class MyFragment extends Fragment {

    FragmentMyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(getLayoutInflater());

        binding.shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.item_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        return binding.getRoot();
    }
}