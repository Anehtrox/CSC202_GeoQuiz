package com.example.geoquiz

import androidx.lifecycle.ViewModel

// Model: holds the question bank and current position. Survives Activity
// recreation on rotation (Week 3 Q3/Q2 - avoids losing state on config change).
class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true)
    )

    // Per-question cheat flag, keyed by question index. Using a map (rather
    // than one shared boolean) means moving to the next/previous question
    // automatically shows the correct cheat status for THAT question -
    // no separate reset step needed, and it can't leak from one question
    // to the next (Task 1 feature #9).
    private val cheatedQuestions = mutableMapOf<Int, Boolean>()

    var currentIndex = 0
        private set

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionCheated: Boolean
        get() = cheatedQuestions[currentIndex] ?: false

    fun setCurrentQuestionCheated(cheated: Boolean) {
        cheatedQuestions[currentIndex] = cheated
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }
}
