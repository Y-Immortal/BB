package edu.wschina.b66;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import edu.wschina.b66.Fragment.MainFragment;
import edu.wschina.b66.Fragment.MyFragment;
import edu.wschina.b66.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setView();

    }
    private void setView() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new MyFragment());

        binding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        binding.viewPager.setCurrentItem(0, false);

        binding.viewPager.setOffscreenPageLimit(fragmentList.size());
        binding.table.setupWithViewPager(binding.viewPager);
        binding.table.getTabAt(0).setIcon(R.drawable.homepage).setText("主页");
        binding.table.getTabAt(1).setIcon(R.drawable.my).setText("我的");

    }

}