package vcmsa.ci.historyknowldgeapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import vcmsa.ci.historyknowldgeapp.ui.theme.HistoryKnowldgeAppTheme

class MainActivity : ComponentActivity() {
   private val questions = arrayOf(
       "The largest diamond was found in SouthAfrica",
       "Akani Simbine was deemed the fastest man in south Africa 2016",
       "South africa hosted the world cup in 2010",
       "South Africa has 11 languaqes",
       "Wayde Van Niekerk has the 200m world Record"
   )

    private val answers = arrayOf(true,true,true,false,false)
    private var currentQuestion = 0
    private var score = 0
    private val feedbackList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    private fun showWelcomeScreen() {
        setContentView(R.layout.activity_welcome)
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            currentQuestion = 0
            score = 0
            feedbackList.clear()
            showQuestionScreen()
        }

    }

private fun showQuestionScreen() {
    setContentView(R.layout.activity_question)

    val questionText = findViewById<TextView>(R.id.questionText)
    val trueButton = findViewById<Button>(R.id.trueButton)
    val falseButton = findViewById<Button>(R.id.falseButton)
    val feedbackText = findViewById<TextView>(R.id.feedbackText)
    val nextButton = findViewById<Button>(R.id.nextButton)

    questionText.text = questions[currentQuestion]
    feedbackText.text = ""
    var answered = false

    trueButton.setOnClickListener {
        if (!answered) {
            checkAnswer(true, feedbackText)
            answered = true
        }
    }
    falseButton.setOnClickListener {
        if (!answered) {
            checkAnswer(false, feedbackText)
            answered = true
        }
    }
    nextButton.setOnClickListener {
        currentQuestion++
        if (currentQuestion < questions.size) {
            showQuestionScreen()
        } else {
            showScoreScreen()
        }
    }
}
private fun checkAnswer(userAnswer: Boolean, feedbackText: TextView ){
    val correct = answers[currentQuestion]
    if (userAnswer== correct){
        feedbackText.text = "correct!"
        score++
        feedbackList.add("Q${currentQuestion + 1}:")
    }else {
        feedbackText.text = "Incorrect!"
        feedbackList.add("Q${currentQuestion + 1}:")
    }

    }
    private fun showScoreScreen() {
        setContentView(R.layout.activity_score)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val finalFeedback = findViewById<TextView>(R.id.finalFeedback)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        scoreText.text = "You scored $score out of ${questions.size}"
        finalFeedback.text = if (score >= 3 ) "Awesome you know your history!" else "Keep learning more history!"

        reviewButton.setOnClickListener{
            val facts = questions.mapIndexed(){index,q ->
                "${index + 1}. $q\nAnswer: ${answers[index]}"
            }.joinToString ("\n\n")
            Toast.makeText(this,facts,Toast.LENGTH_LONG).show()

        }
        exitButton.setOnClickListener{
            finish()
        }

     }
}
