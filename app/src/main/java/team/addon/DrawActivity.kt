package team.addon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.activity_draw.*


class DrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        // build the first three post-it
        swipeView2.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(5)
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingLeft(20)
                        .setRelativeScale(0.01f))
        val d1 = DrawPostIt(swipeView2)

        swipeView2.addView(d1)
        swipeView2.lockViews();



        //val d2 = DrawPostIt(swipeView2)
        //val d3 = DrawPostIt(swipeView2)
//        val consSet = ConstraintSet()
//        consSet.clone(d1.consLayout2)
//        consSet2.centerVertically(R.id.input, R.id.post_it_board, ConstraintSet.TOP, 8,
//                R.id.post_it_board, ConstraintSet.BOTTOM, 8, 0.1F)
//        consSet2.centerHorizontallyRtl(R.id.input, R.id.post_it_board, ConstraintSet.START, 8, R.id.post_it_board, ConstraintSet.END, 8, 0.3F)
//        consSet.applyTo(d1.consLayout2)


    }


}
