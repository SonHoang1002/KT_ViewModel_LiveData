import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.R
import com.example.android.unscramble.ui.game.MAX_NO_OF_WORDS
import com.example.android.unscramble.ui.game.SCORE_INCREASE
import com.example.android.unscramble.ui.game.allWordsList

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        getNextWord()
        Log.d("GameFragment", "GameViewModel created!")
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val currentWordToCharArray = currentWord.toCharArray()
        currentWordToCharArray.shuffle()
        while (String(currentWordToCharArray).equals(currentWord, ignoreCase = true)) {
            currentWordToCharArray.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(currentWordToCharArray)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else {
            false
        }

    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun checkAnswer(answer: String): Boolean {
        return if (answer.equals(currentWord, true)) {
            increaseScore()
            true
        } else {
            false
        }
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    fun checkMessage(): String {
        return if (score < 50) {
            "Ban can co gang hon nua !! Diem cua ban la $score"
        } else {
            "Ban lam rat tot !! Diem cua ban la: $score"
        }
    }

    fun checkIcon(): Int {
        return if (score < 50) {
            R.drawable.ic_sad
        } else {
            R.drawable.ic_happy
        }
    }

    fun checkTitle(): String {
        return if (score < 50) {
            "CHIA BUON"
        } else {
            "CHUC MUNG"
        }
    }

}