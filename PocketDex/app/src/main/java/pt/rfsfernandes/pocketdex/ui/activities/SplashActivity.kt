package pt.rfsfernandes.pocketdex.ui.activities

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.imageViewOpen.visibility = View.INVISIBLE
        val view: View = binding.root
        setContentView(view)
        val anim = ValueAnimator.ofFloat(1f, 2f)
        anim.duration = 1000
        anim.addUpdateListener { animation: ValueAnimator ->
            view.scaleX = (animation.animatedValue as Float)
            view.scaleY = (animation.animatedValue as Float)
        }
        binding.imageViewCloseBlink.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.fade_inout_animation))
        window.exitTransition = Explode()
        Handler(mainLooper).postDelayed({ (this@SplashActivity.application as MyCustomApplication).playPokeballSound() }, 2300)
        Handler(mainLooper).postDelayed({
            binding.imageViewCloseBlink.clearAnimation()
            binding.imageViewOpen.visibility = View.VISIBLE
            val fadeIn = AnimationUtils.loadAnimation(this@SplashActivity,
                    R.anim.fade_in)
            fadeIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    binding.imageViewCloseBlink.visibility = View.INVISIBLE
                    binding.imageViewClose.visibility = View.INVISIBLE
                    val animationout = AnimationUtils.loadAnimation(this@SplashActivity,
                            R.anim.fade_out)
                    animationout.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            binding.linearLayoutSplash.visibility = View.GONE
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                    binding.linearLayoutSplash.startAnimation(animationout)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            binding.imageViewOpen.startAnimation(fadeIn)
            anim.start()
        }, 3500)
    }

}