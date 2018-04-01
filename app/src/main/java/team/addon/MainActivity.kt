package team.addon

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView

import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        dot1.alpha = 0.8F

        // build the first three post-it
        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(5)
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingLeft(20)
                        .setRelativeScale(0.01f))

        swipeView.addView(PostIt(swipeView))
        swipeView.addView(PostIt(swipeView))
        swipeView.addView(PostIt(swipeView))

        // Initial setting for the step hints



        swipeView.addItemRemoveListener {
            when (it) {
                2 -> {
                    switchDot(dot1, dot2)
                    val anim = AlphaAnimation(1.0f, 0.0f)
                    anim.duration = 200

                    anim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationEnd(animation: Animation?) {
                            build_own.visibility = View.GONE
                        }
                        override fun onAnimationStart(animation: Animation?) { }
                        override fun onAnimationRepeat(animation: Animation?) { }
                    })

                    build_own.startAnimation(anim)

                }
                1 -> {
                    switchDot(dot2, dot3)
                }
            }
        }
    }

    private fun switchDot(curDot: ImageView, nextDot: ImageView) {
        curDot.animate()
                .alpha(0.2F)
                .duration = 200

        nextDot.animate()
                .alpha(0.8F)
                .duration = 200
    }
}