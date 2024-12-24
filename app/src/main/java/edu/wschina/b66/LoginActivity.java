package edu.wschina.b66;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.wschina.b66.API.LoginApi;
import edu.wschina.b66.Util.SPUtil;
import edu.wschina.b66.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    List<Integer> integerList;

    Timer timer;
    int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setViewPager();
        startAutoScroll();

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.closeEyes.setOnTouchListener(new View.OnTouchListener() {
            boolean isShowingPassword = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isShowingPassword = true;
                        binding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.closeEyes.setImageResource(R.drawable.eye);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isShowingPassword = false;
                        binding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        binding.closeEyes.setImageResource(R.drawable.close_eyes);
                        break;
                }
                return true;
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.account.getText().toString().trim();
                String password = binding.password.getText().toString().trim();

                if(!username.isEmpty() && !password.isEmpty()){

                    LoginApi.login(LoginActivity.this,username,password, data -> {
                        if (data.code == 0){
                            SPUtil.putString(LoginActivity.this,"token",data.data);

                            SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,data.msg.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(LoginActivity.this,"用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init(){

        integerList = new ArrayList<>();
        integerList.add(R.drawable.banner1);
        integerList.add(R.drawable.banner2);
        integerList.add(R.drawable.banner3);

    }

    private void setViewPager(){

        binding.viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return integerList.size();
            }
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(LoginActivity.this);
                imageView.setImageResource(integerList.get(position));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

    }

    private void startAutoScroll(){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (integerList != null && !integerList.isEmpty()){

                    currentItem = (currentItem + 1) % integerList.size();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            binding.viewpager.setCurrentItem(currentItem,true);

                        }
                    });

                }
            }
        },3000,3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoScroll();
        binding = null;
    }

    private void stopAutoScroll(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

}