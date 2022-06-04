package com.bignerdranch.android.geoquizv5_test_upload2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy() {
        ViewModelProvider(this).get(QuizViewModel::class.java)

    }



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0)?:0
        quizViewModel.currentIndex = currentIndex


        //val provider: ViewModelProvider = ViewModelProvider(this)
        //val quizViewModel = provider.get(QuizViewModel::class.java)
        //Log.d(TAG,"Got a QuizViewModel: $quizViewModel")

        questionTextView = findViewById(R.id.question_text_view)
        nextButton= findViewById(R.id.next_button)
        prevButton= findViewById(R.id.prev_button)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            //if (questionBank[currentIndex].state == 0) checkAnswer(true)
            //else
            Toast.makeText(this, "already answered", Toast.LENGTH_SHORT)
                .show()
        }

        falseButton.setOnClickListener { view: View ->
            //if (questionBank[currentIndex].state == 0) checkAnswer(false)
            //else
            Toast.makeText(this, "already answered", Toast.LENGTH_SHORT)
                .show()
        }

        nextButton.setOnClickListener { //view: View ->
            quizViewModel.moveToNext()
            //currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        /*prevButton.setOnClickListener { //view: View ->
            currentIndex = (currentIndex - 1) % questionBank.size
            if (currentIndex <= 0)
               currentIndex = questionBank.size - 1
            updateQuestion()
        }

        questionTextView.setOnClickListener { //view: View ->

            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        */

        updateQuestion()

    }

    override fun onStart()
    {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onResume()
    {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause()
    {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop()
    {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }


    private fun updateQuestion()
    {
        val questionTextResId = quizViewModel.currentQuestionText
        // val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean)
    {

        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId =
            if (userAnswer == correctAnswer)
            {
                quizViewModel.numCorrectAnswered += 1
                R.string.correct_toast
            }
            else
            {
                R.string.incorrect_toast
            }

        quizViewModel.numAnswered += 1
        quizViewModel.questionBank[quizViewModel.currentIndex].state = 1
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if (quizViewModel.numAnswered == quizViewModel.questionBank.size)
            Toast.makeText(this, "correctly answered: $quizViewModel.numCorrectAnswered", Toast.LENGTH_SHORT)
                .show()

    }

}