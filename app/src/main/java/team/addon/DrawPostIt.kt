package team.addon

import android.widget.ImageView
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeView


@NonReusable
@Layout(R.layout.view_draw_post_it)
class DrawPostIt(swipeView: SwipePlaceHolderView){

    @View(R.id.frame)
    lateinit var frame: ImageView

    @SwipeView
    var view: android.view.View? = null



}