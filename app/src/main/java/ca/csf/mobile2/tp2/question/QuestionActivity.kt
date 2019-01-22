package ca.csf.mobile2.tp2.question

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import ca.csf.mobile2.tp2.R
import ca.csf.mobile2.tp2.databinding.ActivityQuestionBinding
import org.androidannotations.annotations.*
import java.util.*


@SuppressLint("Registered") //Reason : Generated by Android Annotations
@DataBound
@EActivity(R.layout.activity_question)
class QuestionActivity : AppCompatActivity() {

    @ViewById(R.id.choice1_textview)
    protected lateinit var choice1TextView : TextView
    @ViewById(R.id.choice2_textview)
    protected lateinit var choice2TextView: TextView
    @ViewById(R.id.choice1_result_textview)
    protected lateinit var choice1ResultTextView : TextView
    @ViewById(R.id.choice2_result_textview)
    protected lateinit var choice2ResultTextView: TextView
    @ViewById(R.id.error_textview)
    protected lateinit var errorTextView : TextView

    @ViewById(R.id.error_imageview)
    protected lateinit var errorImageView : ImageView

    @ViewById(R.id.choice1_result_background)
    protected lateinit var  choice1ResultBackground : View
    @ViewById(R.id.choice2_result_background)
    protected lateinit var choice2ResultBackground : View

    @ViewById(R.id.choice1_button)
    protected lateinit var choice1Button : Button
    @ViewById(R.id.choice2_button)
    protected lateinit var choice2Button : Button
    @ViewById(R.id.retry_button)
    protected lateinit var  retryButton : Button

    @ViewById(R.id.progressBar)
    protected lateinit var progressBar : ProgressBar

    @Bean
    protected  lateinit var questionService : QuestionService

    @InstanceState
    protected lateinit var question : Question

    @InstanceState
    protected lateinit var viewModel : QuestionActivityViewModel

    protected fun onCreate(@BindingObject dataBinder : ActivityQuestionBinding) {
        question = Question(UUID.fromString("00000000-0000-0000-0000-000000000000"), "", "", "", 0, 0)
        viewModel = QuestionActivityViewModel(question)

        dataBinder.viewModel = viewModel
    }

    @AfterViews
    protected fun afterViews(){
    }

    @Click(R.id.retry_button)
    protected fun onRetryButtonClicked(){
        getRandomQuestion()
    }

    @Click(R.id.choice1_button)
    protected fun onChoice1ButtonClicked(){
        chooseQuestion1(0)
    }

    @Click(R.id.choice2_button)
    protected fun onChoice2ButtonClicked(){
        chooseQuestion2(0)
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    protected  fun getRandomQuestion(){
        questionService.getRandomQuestion(
            this::onRandomQuestionFound,
            this::onConnectivityError,
            this::onServerError)
    }
    protected fun chooseQuestion1(id : Int) {
        questionService.getQuestion1( id,this::onQuestion1Chose,
            this::onConnectivityError,
            this::onServerError)

    }

    protected fun chooseQuestion2(id: Int) {
        questionService.getQuestion2(  id, this::onQuestion2Chose,
            this::onConnectivityError,
            this::onServerError)
    }
    @UiThread
    protected fun onRandomQuestionFound(question : Question){
        //TODO : ShowRandomQuestion
        this.question = question
        Log.v("bob", "Id: " + question.id + " Text: " + question.text + " Choice 1 " + question.choice1 +
                " Choice 2 " + question.choice2 + " NbChoice1 " + question.nbChoice1 + " NbChoice2 " +
                question.nbChoice2)

    }
    @UiThread
    protected fun onQuestion1Chose(question: Question){
        //TODO : ShowQuestion1
        this.question = question
    }

    @UiThread
    protected fun onQuestion2Chose(question : Question){
        //TODO : ShowQuestion2
        this.question = question
    }

    @UiThread
    protected fun onConnectivityError(){
        //TODO : ShowErrorMessage
        hideProgressBar()
        Log.v("bob", "connectivity error")
    }

    @UiThread
    protected  fun onServerError(){
        //TODO : ShowErrorMessage
        hideProgressBar()
        Log.v("bob", "server error")
    }

    private fun hideAll(){
        hideProgressBar()
        hideChoiceTextViews()
        hideChoiceResultTextViews()
        hideErrorVisuals()
    }

    private fun hideChoiceTextViews(){
        choice1TextView.visibility = View.INVISIBLE
        choice2TextView.visibility = View.INVISIBLE
    }
    private fun hideChoiceResultTextViews(){
        choice1ResultTextView.visibility = View.INVISIBLE
        choice2ResultTextView.visibility = View.INVISIBLE
    }
    private fun hideErrorVisuals(){
        errorImageView.visibility = View.INVISIBLE
        errorTextView.visibility = View.INVISIBLE
    }








}
