package pt.rfsfernandes.pocketdex.ui.activities

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.util.TimeUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.NonCancellable.start
import org.koin.androidx.viewmodel.ext.android.viewModel
import pt.rfsfernandes.pocketdex.MyCustomApplication
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.databinding.ActivitySplashBinding
import pt.rfsfernandes.pocketdex.model.Resource
import pt.rfsfernandes.pocketdex.viewmodels.SplashViewModel
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()
    private lateinit var valAnim: ValueAnimator
    private lateinit var binding: ActivitySplashBinding
    private var timeWhenRequested: Long = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.imageViewOpen.visibility = View.INVISIBLE
        val view: View = binding.root
        setContentView(view)
        valAnim = ValueAnimator.ofFloat(1f, 2f)

        valAnim.addUpdateListener { animation: ValueAnimator ->
            view.scaleX = (animation.animatedValue as Float)
            view.scaleY = (animation.animatedValue as Float)
        }
        binding.imageViewCloseBlink.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.fade_inout_animation))
        window.exitTransition = Explode()
        observe()
        timeWhenRequested = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        splashViewModel.getPokemons()

    }

    private fun observe() {
        splashViewModel.pokemonMutableLiveData.observe(this) {
            when(it) {
                is Resource.Success -> {
                    if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timeWhenRequested < 2) {
                        makeOpenAnimation(2000, 3200)
                    } else {
                        makeOpenAnimation(0, 3500-2300)
                    }
                }
                is Resource.NetworkError,
                is Resource.Error -> {
                    it.message?.let { it1 -> Snackbar.make(binding.root, it1, LENGTH_LONG).show() }
                    makeOpenAnimation(2300, 3500)
                }
            }
        }
    }

    private fun makeOpenAnimation(pokeballSoundDelay: Long, pokeballOpenDelay: Long) {
        Handler(mainLooper).postDelayed({ (this@SplashActivity.application as MyCustomApplication).playPokeballSound() }, pokeballSoundDelay) //2300 default
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
            valAnim.start()
        }, pokeballOpenDelay) //3500
    }

}