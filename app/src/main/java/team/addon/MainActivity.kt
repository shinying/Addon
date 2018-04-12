package team.addon

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView

import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.pawegio.kandroid.animListener
import com.pawegio.kandroid.textWatcher

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

        val p1 = PostIt(swipeView)
        val p2 = PostIt(swipeView)
        val p3 = PostIt(swipeView)

        swipeView.addView(p1)
        swipeView.addView(p2)
        swipeView.addView(p3)

        swipeView.lockViews()

        p1.setText(getString(R.string.join1), getString(R.string.join_hint_1))

        p1.input?.inputType = InputType.TYPE_CLASS_NUMBER
        p1.input?.textWatcher {
            afterTextChanged {
                swipeView.unlockViews()
            }
        }
        var p2Input = ""

        swipeView.addItemRemoveListener {
            when (it) {
                2 -> {
                    swipeView.lockViews()

                    switchDot(dot1, dot2)
                    val anim = AlphaAnimation(1.0f, 0.0f)
                    anim.duration = 200
                    anim.animListener {
                        onAnimationEnd {
                            build_own.visibility = View.GONE
                        }
                    }
                    build_own.startAnimation(anim)


                    p2.setText(getString(R.string.join2), getString(R.string.join_hint_2))
                    p2.input?.inputType = InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
                    p2.input?.textWatcher {
                        afterTextChanged {
                            p2Input = p2.input?.text.toString()
                            swipeView.unlockViews()
                        }
                    }
                }

                1 -> {
                    switchDot(dot2, dot3)
                    if(!p2Input.isNullOrBlank()){
                        val p3Welcome: String = getString(R.string.join3_for) + " " + p2Input + getString(R.string.join3_mid) + getString(R.string.join3_late)
                        p3.setText(p3Welcome, "")
                        p3.input?.visibility = View.GONE
                    }


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