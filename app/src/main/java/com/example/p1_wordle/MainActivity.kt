package com.example.p1_wordle

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editTextUserGuess: EditText
    private lateinit var buttonSubmitGuess: Button
    private lateinit var textViewAnswer: TextView
    private lateinit var mainLayout: ConstraintLayout
    private val textViewGuessLabels = arrayOfNulls<TextView>(3)
    private val textViewGuessWords = arrayOfNulls<TextView>(3)
    private val textViewGuessChecks = arrayOfNulls<TextView>(3)
    private var wordToGuess = ""
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assign views to variables
        editTextUserGuess = findViewById(R.id.editTextUserGuess)
        buttonSubmitGuess = findViewById(R.id.buttonSubmitGuess)
        mainLayout = findViewById(R.id.main)
        textViewAnswer = findViewById(R.id.textViewAnswer)

        // Initialize TextView arrays
        textViewGuessLabels[0] = findViewById(R.id.textViewGuess1)
        textViewGuessWords[0] = findViewById(R.id.textViewGuess1Word)
        textViewGuessChecks[0] = findViewById(R.id.textViewGuess1Check)

        textViewGuessLabels[1] = findViewById(R.id.textViewGuess2)
        textViewGuessWords[1] = findViewById(R.id.textViewGuess2Word)
        textViewGuessChecks[1] = findViewById(R.id.textViewGuess2Check)

        textViewGuessLabels[2] = findViewById(R.id.textViewGuess3)
        textViewGuessWords[2] = findViewById(R.id.textViewGuess3Word)
        textViewGuessChecks[2] = findViewById(R.id.textViewGuess3Check)

        // Get a random word to guess
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        // Set up the button click listener
        buttonSubmitGuess.setOnClickListener {
            onSubmitGuess()
        }
    }

    private fun onSubmitGuess() {
        val userGuess = editTextUserGuess.text.toString().uppercase()
        if (userGuess.length != 4) {
            Toast.makeText(this, "Please enter a 4-letter word", Toast.LENGTH_SHORT).show()
            return
        }

        val guessResult = checkGuess(userGuess, wordToGuess)

        // Show the user's guess and the result
        textViewGuessLabels[numberOfGuesses]?.visibility = View.VISIBLE
        textViewGuessWords[numberOfGuesses]?.apply {
            text = userGuess
            visibility = View.VISIBLE
        }
        textViewGuessChecks[numberOfGuesses]?.apply {
            text = guessResult
            visibility = View.VISIBLE
        }

        // Check if the user has guessed the correct word or if they've run out of guesses
        if (guessResult == "OOOO") {
            showWinEffect()
            revealAnswer()
            disableGuessing()
            Toast.makeText(this, "Congratulations! You've guessed the word!", Toast.LENGTH_LONG).show()
        } else if (numberOfGuesses == 2) {
            revealAnswer()
            disableGuessing()
            Toast.makeText(this, "The word was $wordToGuess. Try again!", Toast.LENGTH_LONG).show()
        }

        numberOfGuesses++
        editTextUserGuess.text.clear()
    }

    private fun showWinEffect() {
        // Change the background color of the main layout to indicate a correct guess
        val colorFrom = ContextCompat.getColor(this, android.R.color.white)
        val colorTo = ContextCompat.getColor(this, android.R.color.holo_purple)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
            duration = 250 // duration of the flash
            repeatCount = 3
            repeatMode = ValueAnimator.REVERSE
        }
        colorAnimation.addUpdateListener { animator -> mainLayout.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.start()
    }

    private fun revealAnswer() {
        textViewAnswer.text = wordToGuess
        textViewAnswer.visibility = View.VISIBLE
    }

    private fun disableGuessing() {
        buttonSubmitGuess.isEnabled = false
        editTextUserGuess.isEnabled = false
    }

    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in guess.indices) {
            result += when {
                guess[i] == wordToGuess[i] -> "O"
                guess[i] in wordToGuess -> "+"
                else -> "X"
            }
        }
        return result
    }
}


//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var editTextUserGuess: EditText
//    private lateinit var buttonSubmitGuess: Button
//    private val textViewGuessLabels = arrayOfNulls<TextView>(3)
//    private val textViewGuessWords = arrayOfNulls<TextView>(3)
//    private val textViewGuessChecks = arrayOfNulls<TextView>(3)
//    private var wordToGuess = ""
//    private var numberOfGuesses = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        editTextUserGuess = findViewById(R.id.editTextUserGuess)
//        buttonSubmitGuess = findViewById(R.id.buttonSubmitGuess)
//
//        // Initialize TextViews for guesses and checks
//        textViewGuessLabels[0] = findViewById(R.id.textViewGuess1)
//        textViewGuessWords[0] = findViewById(R.id.textViewGuess1Word)
//        textViewGuessChecks[0] = findViewById(R.id.textViewGuess1Check)
//
//        textViewGuessLabels[1] = findViewById(R.id.textViewGuess2)
//        textViewGuessWords[1] = findViewById(R.id.textViewGuess2Word)
//        textViewGuessChecks[1] = findViewById(R.id.textViewGuess2Check)
//
//        textViewGuessLabels[2] = findViewById(R.id.textViewGuess3)
//        textViewGuessWords[2] = findViewById(R.id.textViewGuess3Word)
//        textViewGuessChecks[2] = findViewById(R.id.textViewGuess3Check)
//
//        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
//        buttonSubmitGuess.setOnClickListener {
//            onSubmitGuess()
//        }
//    }
//
//    private fun onSubmitGuess() {
//        val userGuess = editTextUserGuess.text.toString().uppercase()
//        if (userGuess.length != 4) {
//            Toast.makeText(this, "Please enter a 4-letter word", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (numberOfGuesses < 3) {
//            val result = checkGuess(userGuess, wordToGuess)
//            textViewGuessLabels[numberOfGuesses]?.visibility = View.VISIBLE
//            textViewGuessWords[numberOfGuesses]?.apply {
//                text = userGuess
//                visibility = View.VISIBLE
//            }
//            textViewGuessChecks[numberOfGuesses]?.apply {
//                text = result
//                visibility = View.VISIBLE
//            }
//            numberOfGuesses++
//        }
//        if (numberOfGuesses == 3) {
//            buttonSubmitGuess.isEnabled = false
//            Toast.makeText(this, "The word was $wordToGuess", Toast.LENGTH_LONG).show()
//            // Reveal the answer
//            findViewById<TextView>(R.id.textViewAnswer).apply {
//                text = wordToGuess
//                visibility = View.VISIBLE
//            }
//        }
//        editTextUserGuess.text.clear()
//    }
//
//    private fun checkGuess(guess: String, wordToGuess: String): String {
//        var result = ""
//        for (i in guess.indices) {
//            result += when {
//                guess[i] == wordToGuess[i] -> "O"
//                guess[i] in wordToGuess -> "+"
//                else -> "X"
//            }
//        }
//        return result
//    }
//}

