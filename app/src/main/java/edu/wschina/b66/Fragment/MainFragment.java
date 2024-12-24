package edu.wschina.b66.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import coil.ImageLoader;
import coil.request.ImageRequest;
import edu.wschina.b66.API.PictureApi;
import edu.wschina.b66.R;
import edu.wschina.b66.ShowActivity;
import edu.wschina.b66.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(getLayoutInflater());

        PictureApi.picture(getContext(), data -> {

            if (data.code == 0) {

                binding.recycler.setAdapter(new RecyclerView.Adapter<PictureViewHolder>() {

                    @NonNull
                    @Override
                    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new PictureViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_image, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {

                        PictureApi.ResultPicture item = data.data.list.get(position);

                        ImageRequest request = new ImageRequest.Builder(holder.itemView.getContext())
                                .data(item.url)
                                .target(holder.imageView)
                                .build();

                        ImageLoader imageLoader = new ImageLoader.Builder(holder.itemView.getContext()).build();
                        imageLoader.enqueue(request);

                        holder.imageView.setOnClickListener(view -> {
                            Intent intent = new Intent(view.getContext(), ShowActivity.class);
                            intent.putExtra("ImageId", item.id);
                            view.getContext().startActivity(intent);
                        });

                    }

                    @Override
                    public int getItemCount() {
                        return data.data.list.size();
                    }

                });
            } else {

            }
        });


        binding.recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    public static class PictureViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

}