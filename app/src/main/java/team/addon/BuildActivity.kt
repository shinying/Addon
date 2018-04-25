package team.addon

import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Range
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_build.*
import org.json.JSONObject


const val MAX_TAG = 3

class BuildActivity : AppCompatActivity() {

    private var tagCnt = 0

    private var tagBool = booleanArrayOf(false, false, false, false, false, false)

    private var wallName = ""

    private var wallPrivacy = -1

    private var builderName = ""

    private var wallPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        dot1.alpha = 0.8F


        // build the first three post-it
        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(5)
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingLeft(20)
                        .setRelativeScale(0.01f))

        val p1 = AddWall(swipeView)
        val p2 = AddWall(swipeView)
        val p3 = AddWall(swipeView)

        swipeView.addView(p1)
        swipeView.addView(p2)
        swipeView.addView(p3)

        swipeView.disableTouchSwipe()

        val consSet = ConstraintSet()
        consSet.clone(p1.consLayout)
        consSet.centerVertically(R.id.input, R.id.post_it, ConstraintSet.TOP, 8,
                R.id.post_it, ConstraintSet.BOTTOM, 8, 0.1F)
        consSet.centerHorizontallyRtl(R.id.input, R.id.post_it, ConstraintSet.START, 8, R.id.post_it, ConstraintSet.END, 8, 0.3F)
        consSet.applyTo(p1.consLayout)


        p1.postContent?.visibility = View.GONE
        p1.input?.hint = getString(R.string.hint_wall_name).toString()
        p1.input?.textSize = 30F

        var textChanged = false

        p1.input?.textWatcher {
            afterTextChanged {
                wallName = p1.input?.text.toString()
                textChanged = true
            }
        }

        p1.label?.text = getString(R.string.select)
        p1.label?.visibility = View.VISIBLE
        p1.privacy?.visibility = View.VISIBLE

        val pubTypeface = Typeface.create(p1.pub?.typeface, Typeface.NORMAL)
        val priTypeface = Typeface.create(p1.pri?.typeface, Typeface.NORMAL)

        p1.pub?.setOnClickListener {
            p1.pub?.setTypeface(pubTypeface, Typeface.BOLD)
            p1.pri?.setTypeface(priTypeface, Typeface.NORMAL)
            wallPrivacy = 0

            if(textChanged)
                swipeView.enableTouchSwipe()
        }

        p1.pri?.setOnClickListener {
            p1.pri?.setTypeface(priTypeface, Typeface.BOLD)
            p1.pub?.setTypeface(pubTypeface, Typeface.NORMAL)
            wallPrivacy = 1

            if(textChanged)
                swipeView.enableTouchSwipe()
        }



        swipeView.addItemRemoveListener {
            when (it) {
                2 -> {
                    swipeView.disableTouchSwipe()

                    switchDot(dot1, dot2)

                    p2.postContent?.text = String.format(getString(R.string.build_wall_name), wallName)
                    p2.input?.visibility = View.GONE

                    val consSet2 = ConstraintSet()
                    consSet2.clone(p2.consLayout)
                    consSet.centerHorizontallyRtl(R.id.post_content, R.id.post_it, ConstraintSet.START, 8, R.id.post_it, ConstraintSet.END, 8, 0.3F)
                    consSet2.applyTo(p2.consLayout)

                    p2.label?.visibility = View.VISIBLE
                    p2.label?.text = getString(R.string.put_tag)

                    p2.tags?.visibility = View.VISIBLE
                    p2.tag1?.setOnClickListener {
                        selectTag(p2.tag1!!, 0, swipeView)
                    }
                    p2.tag2?.setOnClickListener {
                        selectTag(p2.tag2!!, 1, swipeView)
                    }
                    p2.tag3?.setOnClickListener {
                        selectTag(p2.tag3!!, 2, swipeView)
                    }
                    p2.tag4?.setOnClickListener {
                        selectTag(p2.tag4!!, 3, swipeView)
                    }
                    p2.tag5?.setOnClickListener {
                        selectTag(p2.tag5!!, 4, swipeView)
                    }
                    p2.tag6?.setOnClickListener {
                        selectTag(p2.tag6!!, 5, swipeView)
                    }
                }

                1 -> {
                    switchDot(dot2, dot3)
                    swipeView.disableTouchSwipe()
                    p3.postContent?.text = String.format(getString(R.string.build_wall_name), wallName)
                    p3.input?.hint = getString(R.string.hint_member_name).toString()
                    p3.input?.textWatcher {
                        afterTextChanged {
                            builderName = p3.input?.text.toString()
                            if(!builderName.isBlank())
                                swipeView.enableTouchSwipe()
                        }
                    }

                }

                0 -> {

                    val wallInfo = JSONObject()
                    wallInfo.put("wallName", wallName)
                    wallInfo.put("builder", builderName)
                    wallInfo.put("#product", tagBool[0])
                    wallInfo.put("#issue", tagBool[1])
                    wallInfo.put("#knowledge", tagBool[2])
                    wallInfo.put("#process", tagBool[3])
                    wallInfo.put("#activity", tagBool[4])
                    wallInfo.put("#others", tagBool[5])


                    getWallPin(EndPoints.buildWall, wallInfo)

                    dot1.visibility = View.GONE
                    dot2.visibility = View.GONE
                    dot3.visibility = View.GONE
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

    private fun selectTag(tag: TextView, i: Int, swipeView: SwipePlaceHolderView) {

        if(tagBool[i]) {
            tag.setTypeface(Typeface.create(tag.typeface, Typeface.NORMAL), Typeface.NORMAL)
            tagBool[i] = false
            tagCnt--
        }

        else if(tagCnt < team.addon.MAX_TAG) {
            tag.setTypeface(Typeface.create(tag.typeface, Typeface.NORMAL), Typeface.BOLD)
            tagBool[i] = true
            tagCnt++
        }

        if(Range(1, team.addon.MAX_TAG).contains(tagCnt))
            swipeView.enableTouchSwipe()

        else
            swipeView.disableTouchSwipe()
    }

    private fun getWallPin(url: String, params: JSONObject) {

        //creating volley string request
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, url, params,

                Response.Listener<JSONObject> { response ->

                    wallPin = response.optString("wallPin", "0000")
                },

                Response.ErrorListener { volleyError ->
                    Log.e("err", volleyError.toString())
                }) { }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(jsonObjectRequest)
    }
}




