package team.addon

import android.support.constraint.ConstraintLayout
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeView


@NonReusable
@Layout(R.layout.view_draw_post_it)
class DrawPostIt(swipeView2: SwipePlaceHolderView){

    @View(R.id.cons_layout2)
    var consLayout2: ConstraintLayout? = ConstraintLayout(swipeView2.context)

    @SwipeView
    var view: android.view.View? = null

}