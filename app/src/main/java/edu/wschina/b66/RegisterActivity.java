package edu.wschina.b66;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.wschina.b66.API.RegisterApi;
import edu.wschina.b66.Util.SPUtil;
import edu.wschina.b66.databinding.ActivityResgiterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityResgiterBinding binding;

    List<Integer> integerList;

    Timer timer;
    int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResgiterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setViewPager();
        startAutoScroll();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = binding.username.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                String passwordTow = binding.passwordTwo.getText().toString().trim();
                String email = binding.email.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty() && !passwordTow.isEmpty()) {

                    if (!password.equals(passwordTow)) {

                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();

                    } else {

                        if (email.indexOf('@') != -1 && email.indexOf('.') != -1) {

                        } else {
                            Toast.makeText(RegisterActivity.this, "不是一个有效的邮箱", Toast.LENGTH_SHORT).show();
                        }

                        RegisterApi.register(RegisterActivity.this, email, username, password, data -> {
                            if (data.code == 0) {
                                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                SPUtil.putString(RegisterActivity.this, "token", data.token);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败：" + data.msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init() {

        integerList = new ArrayList<>();
        integerList.add(R.drawable.banner1);
        integerList.add(R.drawable.banner2);
        integerList.add(R.drawable.banner3);

    }

    private void setViewPager() {

        binding.viewPager.setAdapter(new PagerAdapter() {
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
                ImageView imageView = new ImageView(RegisterActivity.this);
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

    private void startAutoScroll() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (integerList != null && !integerList.isEmpty()) {

                    currentItem = (currentItem + 1) % integerList.size();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            binding.viewPager.setCurrentItem(currentItem, true);

                        }
                    });

                }
            }
        }, 3000, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoScroll();
        binding = null;
    }

    private void stopAutoScroll() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}