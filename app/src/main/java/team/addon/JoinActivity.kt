package team.addon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.pawegio.kandroid.animListener
import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_join.*
import org.json.JSONObject


class JoinActivity : AppCompatActivity() {

    private var wallPin = ""

    private var wallName = ""

    private var memberName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        dot1.alpha = 0.8F

        // Build the first three post-it
        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setIsUndoEnabled(true)
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(25)
                        .setRelativeScale(0.02f))

        val p1 = AddPostIt(swipeView)
        val p2 = AddPostIt(swipeView)
        val p3 = AddPostIt(swipeView)

        swipeView.addView(p1)
        swipeView.addView(p2)
        swipeView.addView(p3)

        swipeView.disableTouchSwipe()

        // Set the layout for post 1
        p1.setText(getString(R.string.join_welcome), getString(R.string.hint_wall_pin))

        p1.input?.inputType = InputType.TYPE_CLASS_NUMBER
        p1.input?.setOnEditorActionListener { textView, i, event ->
            if(i != 0 && EditorInfo.IME_MASK_ACTION != 0){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(p1.input?.windowToken, 0)
                wallPin = p1.input?.text.toString()
//                swipeView.doSwipe(false)

                return@setOnEditorActionListener true
            }
            else
                return@setOnEditorActionListener false
        }

        // Add listener to input: EditText
        p1.input?.textWatcher {
            afterTextChanged {
                wallPin = p1.input?.text.toString()
                swipeView.enableTouchSwipe()
            }
        }


        build_own.setOnClickListener {
            startActivity(Intent(this, BuildActivity::class.java))
        }

        // Add listener to swipeView
        swipeView.addItemRemoveListener {
            when (it) {
                2 -> {

                    switchDot(dot1, dot2)
                    swipeView.disableTouchSwipe()

                    // Let build_own fade out
                    val anim = AlphaAnimation(1.0f, 0.0f)
                    anim.duration = 200
                    anim.animListener {
                        onAnimationEnd {
                            build_own.visibility = View.GONE
                        }
                    }
                    build_own.startAnimation(anim)


                    // connect server to get wall pin
                    val params = JSONObject()
                    params.put("wallPin", wallPin)

                    getWallName(EndPoints.joinWall, params, p2)


                    // set p2 layout
                    p2.input?.setOnEditorActionListener { textView, i, event ->
                        if(i != 0 && EditorInfo.IME_MASK_ACTION != 0){
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(p2.input?.windowToken, 0)
                            memberName = p2.input?.text.toString()
//                            swipeView.doSwipe(false)

                            return@setOnEditorActionListener true
                        }
                        else
                            return@setOnEditorActionListener false
                    }
                    p2.input?.textWatcher {
                        afterTextChanged {
                            memberName = p2.input?.text.toString()
                            swipeView.enableTouchSwipe()
                        }
                    }
                }

                1 -> {
                    switchDot(dot2, dot3)
                    if(!memberName.isBlank()){
                        val p3Welcome = String.format(getString(R.string.join_success), memberName, wallName)
                        p3.setText(p3Welcome, "")
                        p3.input?.visibility = View.GONE
                    }
                }
                0 -> {
                    dot1.visibility = View.GONE
                    dot2.visibility = View.GONE
                    dot3.visibility = View.GONE

                    val intent = Intent(this, DrawActivity::class.java)
                    intent.putExtra("wallPin", wallPin)
                    intent.putExtra("name", memberName)

                    startActivity(intent)
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

    // fetch data from server
    private fun getWallName(url: String, params: JSONObject, post: AddPostIt) {

        //creating volley string request
        val jsonObjectRequest = object : JsonObjectRequest (Request.Method.POST, url, params,

                Response.Listener<JSONObject> { response ->

                    Log.e("response", response.toString())

                    wallName = response.optString("wallPin", "none")

                    val p2Welcome = String.format(getString(R.string.join_wall_name), wallName)

                    post.setText(p2Welcome, getString(R.string.hint_member_name))
                },

                Response.ErrorListener { volleyError ->
                    Log.e("err", volleyError.toString())

                }) { }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(jsonObjectRequest)
    }
}