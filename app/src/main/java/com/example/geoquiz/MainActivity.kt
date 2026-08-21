package com.example.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.geoquiz.databinding.ActivityMainBinding

// Controller in the MVC split (Week 2 Q3): binds the View (activity_main.xml)
// to the Model (QuizViewModel) and handles the business logic.
class MainActivity : AppCompatActivity() {

    // "by" property delegation (Week 3 Kotlin note) - lazily inflates the
    // view binding the first time it's accessed.
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // ViewModel survives configuration changes (rotation) - feature #5/#6.
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    // Modern Activity Result API (Week 3 Q7) replaces the deprecated
    // startActivityForResult()/onActivityResult() pair.
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val cheated = CheatActivity.wasAnswerShown(result.data)
            quizViewModel.setCurrentQuestionCheated(cheated)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { checkAnswer(true) }
        binding.falseButton.setOnClickListener { checkAnswer(false) }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (quizViewModel.currentQuestionCheated) {
            R.string.judgment_toast
        } else if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
