package team.addon

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeView

/**
 * Created by shinying.lee on 2018/3/29.
 */
@NonReusable
@Layout(R.layout.post_it_view)
class PostIt(swipeView: SwipePlaceHolderView) {

    @View(R.id.post_it)
    var post_it: ImageView? = null

    @View(R.id.post_content)
    var post_content: TextView? = null

    @View(R.id.input)
    var input: EditText? = null

    @SwipeView
    var view: android.view.View? = null

    protected var mSwipeView: SwipePlaceHolderView = swipeView

    fun setText(join: String, join_hint: String) {
        post_content?.text = join
        input?.hint = join_hint
    }

}