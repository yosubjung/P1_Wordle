package com.example.p1_wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextUserGuess: EditText
    private lateinit var buttonSubmitGuess: Button
    private val textViewGuessLabels = arrayOfNulls<TextView>(3)
    private val textViewGuessWords = arrayOfNulls<TextView>(3)
    private val textViewGuessChecks = arrayOfNulls<TextView>(3)
    private var wordToGuess = ""
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUserGuess = findViewById(R.id.editTextUserGuess)
        buttonSubmitGuess = findViewById(R.id.buttonSubmitGuess)

        // Initialize TextViews for guesses and checks
        textViewGuessLabels[0] = findViewById(R.id.textViewGuess1)
        textViewGuessWords[0] = findViewById(R.id.textViewGuess1Word)
        textViewGuessChecks[0] = findViewById(R.id.textViewGuess1Check)

        textViewGuessLabels[1] = findViewById(R.id.textViewGuess2)
        textViewGuessWords[1] = findViewById(R.id.textViewGuess2Word)
        textViewGuessChecks[1] = findViewById(R.id.textViewGuess2Check)

        textViewGuessLabels[2] = findViewById(R.id.textViewGuess3)
        textViewGuessWords[2] = findViewById(R.id.textViewGuess3Word)
        textViewGuessChecks[2] = findViewById(R.id.textViewGuess3Check)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
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
        if (numberOfGuesses < 3) {
            val result = checkGuess(userGuess, wordToGuess)
            textViewGuessLabels[numberOfGuesses]?.visibility = View.VISIBLE
            textViewGuessWords[numberOfGuesses]?.apply {
                text = userGuess
                visibility = View.VISIBLE
            }
            textViewGuessChecks[numberOfGuesses]?.apply {
                text = result
                visibility = View.VISIBLE
            }
            numberOfGuesses++
        }
        if (numberOfGuesses == 3) {
            buttonSubmitGuess.isEnabled = false
            Toast.makeText(this, "The word was $wordToGuess", Toast.LENGTH_LONG).show()
            // Reveal the answer
            findViewById<TextView>(R.id.textViewAnswer).apply {
                text = wordToGuess
                visibility = View.VISIBLE
            }
        }
        editTextUserGuess.text.clear()
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

