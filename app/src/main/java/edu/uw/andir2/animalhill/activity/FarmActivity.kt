package edu.uw.andir2.animalhill.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import edu.uw.andir2.animalhill.R


class FarmActivity : AppCompatActivity() {
  lateinit var farmBoundary : Array<Array<Float>>
  //lateinit var rocket: ImageView
  //lateinit var doge: ImageView
  lateinit var container: ConstraintLayout
  lateinit var btnSpawn: View
  lateinit var btnAnimate: View
  var screenHeight = 0f
  var screenWidth = 0f
  var active = false
  lateinit var animHandler: Handler
  var animals = mutableListOf<ImageView>()
  //val DEFAULT_ANIMATION_DURATION = 500L

  //change this to read a list of locations from data repo-
  var location = mutableListOf(arrayOf(0f,0f))


  private val updateTextTask = object : Runnable {
    override fun run() {
      animals.forEach{
        //animHandler.postDelayed(this, ((0..2).random() * 1000L))
        onStartAnimation(it, location[animals.indexOf(it) + 1], "cat")
      }
      //onStartAnimation(rocket, location[0])
      animHandler.postDelayed(this, ((3..5).random() * 1000L))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    try {
      this.supportActionBar!!.hide()
    } catch (e: NullPointerException) {
    }

    val displayMetrics = DisplayMetrics()
    screenHeight = displayMetrics.heightPixels.toFloat()
    screenWidth = displayMetrics.widthPixels.toFloat()

    setContentView(R.layout.activity_farm)
    //val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
    farmBoundary = arrayOf(arrayOf(convertDpToPixel(25f), convertDpToPixel(365f)),arrayOf(convertDpToPixel(150f), convertDpToPixel(500f)))
    active = true
    btnSpawn = findViewById(R.id.btnSpawn)
    container = findViewById(R.id.container)

    btnSpawn.setOnClickListener {
      println("clicked")

      var newView: ImageView = ImageView(this)
      var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
        ConstraintLayout.LayoutParams.MATCH_PARENT // This will define text view height
      )

      params.width = 100
      params.height = 100
      newView.layoutParams = params
      newView.x = screenWidth / 2
      newView.y = screenHeight / 2
      newView.setImageResource(R.drawable.doge)
      var id = 100
      newView.id = id
      container.addView(newView)

      animals.add(newView)

      var viewpos = IntArray(2)
      newView.getLocationOnScreen(viewpos)
      println("${newView.x} ${newView.y}")
      location.add(arrayOf(newView.x, newView.y))
    }

    btnAnimate = findViewById(R.id.btnAnimate)
    btnAnimate.setOnClickListener {
      //onStartAnimation(animals[0], location[1])
      animHandler.post(updateTextTask)
    }

    animHandler = Handler(Looper.getMainLooper())
  }

  override fun onPause() {
    super.onPause()

    animHandler.removeCallbacks(updateTextTask)
  }

  override fun onStop() {
    super.onStop()

    active = false
  }

  override fun onResume() {
    super.onResume()

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    screenHeight = displayMetrics.heightPixels.toFloat()
    screenWidth = displayMetrics.widthPixels.toFloat()
    //animHandler.post(updateTextTask)
  }

  fun onStartAnimation(obj: ImageView, location: Array<Float>, animalName: String) {
    var moveCommand = ""
    var axis = (1..2).random()
    val dist = (0..2).random()
    val speed = (2..3).random()
    var animalDirection = "down"
    var direction =  (1 - (0..1).random() * 2) * 100f * dist
    //var startpoint = 0f
    //this is the index used for updating the x or y position of the view
    var resetindex = 0

    if (axis == 2) {
      moveCommand = "translationY"
      resetindex = 1
      if (direction < 0) {
        animalDirection = "up"
      }
      //obj.setImageResource(R.drawable.doge)
    } else {
      //obj.setImageResource(R.drawable.rocket)
      moveCommand = "translationX"
      if (direction < 0) {
        animalDirection = "left"
      } else if (direction > 0){
        animalDirection = "right"
      }
    }



    /*
    direction = convertDpToPixel(-50F)
    moveCommand = "translationX"
    resetindex = 0
    axis = 1
    */

    val drawable = "R.drawable.${animalName}_${animalDirection}"

    println(drawable)
    //the predicted new location of the animal
    var newPosition = location[resetindex] + direction

    //The animal will move out of range
    if (axis == 1 && newPosition !in farmBoundary[0][0]..farmBoundary[0][1]) {
        //move in the opposite direction by half of the original distance
        println("X out of bound ${newPosition} not in ${farmBoundary[0][0]} and ${farmBoundary[0][1]}")
        newPosition = location[resetindex] - (direction / 2)
    } else if (axis == 2 && newPosition !in farmBoundary[1][0]..farmBoundary[1][1]) {
        //move in the opposite direction by half of the original distance
        newPosition = location[resetindex] - (direction / 2)
        println("Y out of bound")
    } else {
      println("in bound")

    }
    println("X:${convertDpToPixel(25f)} to ${screenWidth - convertDpToPixel(25f)}, Y: ${convertDpToPixel(150f)} to ${convertDpToPixel(450f)}")


    //println("${location[0]}${location[1]}")

    //val objectAnimator = ObjectAnimator.ofFloat(obj, moveCommand, convertPixelToDp(newPosition + direction))
    val objectAnimator = ObjectAnimator.ofFloat(obj, moveCommand, newPosition)
    objectAnimator.setInterpolator(AccelerateDecelerateInterpolator())
    objectAnimator.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator) {
      }

      override fun onAnimationEnd(animation: Animator) {
        println("Current position:${location[0]}, ${location[1]}, moving: $moveCommand $direction")
      }

      override fun onAnimationCancel(animation: Animator) {}

      override fun onAnimationRepeat(animation: Animator) {}
    })

    objectAnimator.duration = 3000L / speed
    objectAnimator.start()
    location[resetindex] = newPosition
  }

  fun Context.convertDpToPixel(dp: Number) = dp.toFloat() * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
  fun Context.convertPixelToDp(px: Number) = px.toFloat() / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}
