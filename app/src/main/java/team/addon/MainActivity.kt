package team.addon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

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

        swipeView.addItemRemoveListener {
            when (it) {
                2 -> {
                    dot1.alpha = 0.2F
                    dot2.alpha = 0.8F
                    build_own.visibility = View.GONE
                }
                1 -> {
                    dot2.alpha = 0.2F
                    dot3.alpha = 0.8F
                }
            }
        }
    }
}