package com.example.smartmarket.introduction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smartmarket.Base;
import com.example.smartmarket.MainActivity;
import com.example.smartmarket.R;
import com.example.smartmarket.login.LoginActivity;

public class IntroductionActivity extends Base {

    private static int INTRO_SCREEN_TIME = 5000;

    LottieAnimationView intro_lottie;
    TextView intro_title, intro_subtitle;
    Animation topAnim, bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introduction);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        intro_lottie = (LottieAnimationView) findViewById(R.id.intro_lottie);
        intro_title = (TextView) findViewById(R.id.intro_title);
        intro_subtitle = (TextView) findViewById(R.id.intro_subtitle);

        intro_lottie.setAnimation(topAnim);
        intro_title.setAnimation(bottomAnim);
        intro_subtitle.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(intro_lottie, "trans_image");
                pairs[1] = new Pair<View, String> (intro_title, "trans_text");
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(IntroductionActivity.this, pairs);
                startActivity(new Intent(IntroductionActivity.this, LoginActivity.class)
                        , options.toBundle());
            }
        }, INTRO_SCREEN_TIME);
    }

}
