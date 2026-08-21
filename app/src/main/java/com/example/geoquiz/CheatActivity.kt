package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val KEY_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private lateinit var answerText: String

    // Not a plain var - kept as a property so it can be re-applied to the
    // result Intent every time the Activity is (re)created, closing the
    // "rotate CheatActivity after cheating" loophole (feature #10): a
    // config change destroys and recreates the Activity, which throws away
    // any earlier setResult() call unless it's redone with the restored value.
    private var isAnswerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerText = if (answerIsTrue) {
            getString(R.string.true_button)
        } else {
            getString(R.string.false_button)
        }

        isAnswerShown = savedInstanceState?.getBoolean(KEY_ANSWER_SHOWN, false) ?: false
        if (isAnswerShown) {
            showAnswer()
        }
        setAnswerShownResult(isAnswerShown)

        binding.showAnswerButton.setOnClickListener {
            showAnswer()
            setAnswerShownResult(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, isAnswerShown)
    }

    private fun showAnswer() {
        binding.answerTextView.text = answerText
        isAnswerShown = true
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

        fun wasAnswerShown(result: Intent?): Boolean {
            return result?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}
