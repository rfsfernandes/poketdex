package pt.rfsfernandes.ui.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import pt.rfsfernandes.MyApplication;
import pt.rfsfernandes.R;
import pt.rfsfernandes.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
  private ActivitySplashBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySplashBinding.inflate(getLayoutInflater());
    binding.imageViewOpen.setVisibility(View.INVISIBLE);
    View view = binding.getRoot();
    setContentView(view);

    final ValueAnimator anim = ValueAnimator.ofFloat(1f, 2f);
    anim.setDuration(1000);
    anim.addUpdateListener(animation -> {
      view.setScaleX((Float) animation.getAnimatedValue());
      view.setScaleY((Float) animation.getAnimatedValue());
    });

    binding.imageViewCloseBlink.startAnimation(AnimationUtils.loadAnimation(this,
        R.anim.fade_inout_animation));
    getWindow().setExitTransition(new Explode());

    new Handler(getMainLooper()).postDelayed(() -> {
      ((MyApplication) SplashActivity.this.getApplication()).playPokeballSound();
    }, 2300);

    new Handler(getMainLooper()).postDelayed(() -> {
      binding.imageViewCloseBlink.clearAnimation();

      binding.imageViewOpen.setVisibility(View.VISIBLE);
      Animation fadeIn = AnimationUtils.loadAnimation(SplashActivity.this,
          R.anim.fade_in);

      fadeIn.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          binding.imageViewCloseBlink.setVisibility(View.INVISIBLE);
          binding.imageViewClose.setVisibility(View.INVISIBLE);
          Animation animationout = AnimationUtils.loadAnimation(SplashActivity.this,
              R.anim.fade_out);

          animationout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              binding.linearLayoutSplash.setVisibility(View.GONE);
              startActivity(new Intent(SplashActivity.this, MainActivity.class));
              finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
          });

          binding.linearLayoutSplash.startAnimation(animationout);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });

      binding.imageViewOpen.startAnimation(fadeIn);

      anim.start();

    }, 3500);

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}