package edu.uw.andir2.animalhill.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.transition.TransitionManager
import coil
import com.google.android.material.transition.MaterialFadeThrough
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.ActivityFarmBinding
import edu.uw.andir2.animalhill.fragment.RecordListFragmentDirections
import edu.uw.andir2.animalhill.fragment.ReminderFragmentDirections
import edu.uw.andir2.animalhill.fragment.TimePickerFragmentDirections



class FarmActivity : AppCompatActivity() {
  lateinit var farmBoundary : Array<Array<Float>>
  //lateinit var rocket: ImageView
  //lateinit var doge: ImageView
  lateinit var container: ConstraintLayout
  var screenHeight = 0f
  var screenWidth = 0f
  lateinit var animHandler: Handler
  var animals = mutableListOf<ImageView>()
  var animalNames = mutableListOf("cat", "fox", "pig", "dog", "wolf")
  //val DEFAULT_ANIMATION_DURATION = 500L

  //change this to read a list of locations from data repo-
  var location = mutableListOf(arrayOf(0f,0f))


  private lateinit var binding: ActivityFarmBinding
  private val navController: NavController by lazy { findNavController(R.id.navHost)}

  private val updateTextTask = object : Runnable {
    override fun run() {
      animals.forEach{
        //animHandler.postDelayed(this, ((0..2).random() * 1000L))
        onStartAnimation(it, location[animals.indexOf(it) + 1], animalNames[animals.indexOf(it)])
      }
      //onStartAnimation(rocket, location[0])
      animHandler.postDelayed(this, ((3..5).random() * 1000L))
    }
  }

  fun populateAnimals() {
    for (animalName in animalNames) {
      var newView: ImageView = ImageView(this)
      var params : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT, // This will define text view width
        ConstraintLayout.LayoutParams.MATCH_PARENT // This will define text view height
      )

      newView.layoutParams = params
      newView.x = (50..700).random().toFloat()
      newView.y = (500..1000).random().toFloat()

      val context: Context = newView.context
      var drawableId = context.resources.getIdentifier(animalName + "_down", "drawable", context.packageName)
      newView.setImageResource(drawableId)

      params.width = 100
      params.height = 100
      //newView.setImageResource(R.drawable.doge)
      var id = 100
      newView.id = id

      container.addView(newView)
      animals.add(newView)

      var viewpos = IntArray(2)
      newView.getLocationOnScreen(viewpos)
      println("${newView.x} ${newView.y}")
      location.add(arrayOf(newView.x, newView.y))
    }
  }

  fun hideAnimals() {
    for (animal in animals) {
      animal.visibility = View.GONE
    }
  }

  fun showAnimals() {
    for (animal in animals) {
      animal.visibility = View.VISIBLE
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    try {
      this.supportActionBar!!.hide()
    } catch (e: NullPointerException) {
    }

    binding = ActivityFarmBinding.inflate(layoutInflater).apply {
      setContentView(root)
    }
    with(binding) {
      navController.setGraph(R.navigation.nav_graph)
      bottomAppBar.setOnMenuItemClickListener { item ->
        handleMenuItemPressed(item)
      }

      fab.setOnClickListener {
        val materialFade = MaterialFadeThrough().apply { duration = 250L }
        TransitionManager.beginDelayedTransition(dial, materialFade)
        dial.visibility = if (dial.isVisible) View.GONE else View.VISIBLE
      }

      fabFarm.setOnClickListener { navigateToFarm() }

      fabTimer.setOnClickListener {
        navController.navigate(TimePickerFragmentDirections.actionGlobalTimePickerFragment())
        navHostContainer.visibility = View.VISIBLE
      }
    }

    val displayMetrics = DisplayMetrics()
    screenHeight = displayMetrics.heightPixels.toFloat()
    screenWidth = displayMetrics.widthPixels.toFloat()

    farmBoundary = arrayOf(arrayOf(convertDpToPixel(25f), convertDpToPixel(365f)),arrayOf(convertDpToPixel(150f), convertDpToPixel(500f)))
    container = findViewById(R.id.container)

    animHandler = Handler(Looper.getMainLooper())
    populateAnimals()
  }

  override fun onPause() {
    super.onPause()

    animHandler.removeCallbacks(updateTextTask)
  }

  override fun onStop() {
    super.onStop()
  }

  override fun onResume() {
    super.onResume()

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    screenHeight = displayMetrics.heightPixels.toFloat()
    screenWidth = displayMetrics.widthPixels.toFloat()
    animHandler.post(updateTextTask)
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

    val context: Context = obj.context
    var id = context.resources.getIdentifier(animalName + "_down", "drawable", context.packageName)
    obj.setImageResource(id)

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

    //the predicted new location of the animal
    var newPosition = location[resetindex] + direction

    //The animal will move out of range
    if (axis == 1 && newPosition !in farmBoundary[0][0]..farmBoundary[0][1]) {
        //move in the opposite direction by half of the original distance
        println("X out of bound ${newPosition} not in ${farmBoundary[0][0]} and ${farmBoundary[0][1]}")
        if (direction < 0) {
          animalDirection = "right"
        } else if (direction > 0){
          animalDirection = "left"
        }
        newPosition = location[resetindex] - (direction / 2)
    } else if (axis == 2 && newPosition !in farmBoundary[1][0]..farmBoundary[1][1]) {
        //move in the opposite direction by half of the original distance
        newPosition = location[resetindex] - (direction / 2)
      if (direction <= 0) {
        animalDirection = "down"
      } else {
        animalDirection = "up"
      }
        println("Y out of bound")
    } else {
      println("in bound")

    }

    val drawable = "${animalName}_${animalDirection}"
    println(drawable)

    id = context.resources.getIdentifier(drawable, "drawable", context.packageName)
    obj.setImageResource(id)


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
  //fun Context.convertPixelToDp(px: Number) = px.toFloat() / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

  private fun handleMenuItemPressed(item: MenuItem): Boolean {
    Log.i("Item Selected", item.toString())

    when (item.itemId) {
      R.id.app_bar_record_list -> {
        navigateExceptFarm(RecordListFragmentDirections.actionGlobalRecordListFragment())
      }
      R.id.app_bar_animal_list -> {
        navigateExceptFarm(AnimalListActivityDirections.actionGlobalAnimalListActivity())
      }
      R.id.app_bar_reminder -> {
        navigateExceptFarm(ReminderFragmentDirections.actionGlobalReminderFragment())
      }
    }
    return true
  }

  private fun navigateToFarm() {
    showAnimals()
    binding.navHostContainer.visibility = View.GONE
  }
  private fun navigateExceptFarm(dest : NavDirections) {
    hideAnimals()
    navController.navigate(dest)
    binding.navHostContainer.visibility = View.VISIBLE
  }
}

