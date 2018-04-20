package team.addon

import android.support.constraint.ConstraintLayout
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
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
@Layout(R.layout.view_add_wall)
class AddWall(swipeView: SwipePlaceHolderView) {

    @View(R.id.cons_layout)
    var consLayout: ConstraintLayout? = ConstraintLayout(swipeView.context)

    @View(R.id.post_content)
    var postContent: TextView? = null

    @View(R.id.input)
    var input: EditText? = null

    @View(R.id.pub)
    var pub: TextView? = null

    @View(R.id.pri)
    var pri: TextView? = null

    @View(R.id.label)
    var label: TextView? = null

    @View(R.id.privacy)
    var privacy: LinearLayout? = LinearLayout(swipeView.context)

    @View(R.id.tags)
    var tags: TableLayout? = null

    @View(R.id.tag1)
    var tag1: TextView? = null

    @View(R.id.tag2)
    var tag2: TextView? = null

    @View(R.id.tag3)
    var tag3: TextView? = null

    @View(R.id.tag4)
    var tag4: TextView? = null

    @View(R.id.tag5)
    var tag5: TextView? = null

    @View(R.id.tag6)
    var tag6: TextView? = null


    @SwipeView
    var view: android.view.View? = null

    fun setText(join: String, join_hint: String) {
        postContent?.text = join
        input?.hint = join_hint
    }
}