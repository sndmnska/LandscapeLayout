package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

// Data we need to send is the Seekbar's progress, using Extras (key:value pair(s))
// Use global constants
const val EXTRA_SQUARE_SIZE = "com.example.landscapelayout.tap_the_square.SQUARE_SIZE" // does not need to match file names..
const val EXTRA_EASY_MODE = "com.example.landscapelayout.tap_the_square.EASY_MODE"
class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var seekBarLabel: TextView
    private lateinit var showSquareButton: Button
    private lateinit var easyModeSwitch: Switch

    private val squareResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleSquareResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seek_bar)
        seekBarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)
        easyModeSwitch = findViewById(R.id.easyModeSwitch)

        val initialProgress = seekBar.progress
        updateLabel(initialProgress)

        // add listener to update label as seekbar changes.

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // notifies about start, live listen, and stop.
            override fun onProgressChanged(
                seekBarComponent: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                updateLabel(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }

    }

    private fun showSquare() {
        /** launch the SquareActivity */
        // telling Kotlin to find the SquareActivity class, an extension of the java class
        // "this" refers to MainActivity object, references what to go back to!
/*
        Intent (this, SquareActivity::class. java).apply {
            putExtra(EXTRA_SQUARE_SIZE, seekBar.progress)
            squareResultLauncher.launch(this)
*/
        val easyMode = easyModeSwitch.isChecked;

        // tell SquareActivity how large the square should be
        // based on the progress (setting) of the Seekbar
        val showSquareIntent = Intent(this, SquareActivity::class.java)
        showSquareIntent.putExtra(EXTRA_EASY_MODE, easyMode)
        showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, seekBar.progress)
//        startActivity(showSquareIntent)
        squareResultLauncher.launch(showSquareIntent)
    }

    private fun updateLabel(progress: Int) {
        seekBarLabel.text = getString(R.string.seekbar_value_message, progress)
    }

    private fun handleSquareResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val tapped = intent?.getBooleanExtra(EXTRA_TAPPED_INSIDE_SQUARE, false) ?: false
            val message = if (tapped) {
                getString(R.string.tapped_square)
            } else {
                getString(R.string.missed_square)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.did_not_try),
                Toast.LENGTH_SHORT).show()
        }
    }
}