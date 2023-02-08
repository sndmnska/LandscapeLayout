package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

const val EXTRA_TAPPED_INSIDE_SQUARE = "com.example.landscapelayout.tap_the_square.TAPPED_INSIDE_SQUARE"
class SquareActivity : AppCompatActivity() {

    private lateinit var squareImage: ImageView
    private lateinit var container: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareImage = findViewById(R.id.square)
        container = findViewById(R.id.container)

        // "intent" references the intent that brought us to this Activity in the first place.
        var squareSize = intent.getIntExtra(EXTRA_SQUARE_SIZE, 100)
        if (squareSize == 0) { squareSize = 1}

        val easyMode = intent.getBooleanExtra(EXTRA_EASY_MODE, false)
        if (easyMode) { squareSize *= 3 }

        squareImage.layoutParams.width = squareSize
        squareImage.layoutParams.height = squareSize

        squareImage.setOnClickListener{
            squareTapped(true)
        }

        container.setOnClickListener{
            squareTapped(false)
        }



    }

    private fun squareTapped(didTapSquare: Boolean) {
        // end square activity
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
        setResult(RESULT_OK, resultIntent) // user attempted to tap square, did not "cancel"
        finish() // end this activity
    }
}