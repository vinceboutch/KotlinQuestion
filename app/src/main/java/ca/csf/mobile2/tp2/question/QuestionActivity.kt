package ca.csf.mobile2.tp2.question

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import ca.csf.mobile2.tp2.R
import ca.csf.mobile2.tp2.databinding.ActivityQuestionBinding
import org.androidannotations.annotations.*
import java.util.*

@SuppressLint("Registered") //Reason : Generated by Android Annotations
@DataBound
@EActivity(R.layout.activity_question)
class QuestionActivity : AppCompatActivity() {

    @Bean
    protected lateinit var questionService: QuestionService

    @InstanceState
    protected lateinit var question: Question

    @InstanceState
    protected lateinit var viewModel: QuestionActivityViewModel

    protected fun onCreate(@BindingObject dataBinder: ActivityQuestionBinding) {
        if (!this::question.isInitialized)
            question = Question()
        if (!this::viewModel.isInitialized)
            viewModel = QuestionActivityViewModel(question)

        dataBinder.viewModel = viewModel

        if (question.id == null)
            getRandomQuestion()
    }

    @Click(R.id.retry_button)
    protected fun onRetryButtonClicked() {
        getRandomQuestion()
    }

    @Click(R.id.choice1_button)
    protected fun onChoice1ButtonClicked() {
        chooseQuestion1(viewModel.question.id!!)
    }

    @Click(R.id.choice2_button)
    protected fun onChoice2ButtonClicked() {
        chooseQuestion2(viewModel.question.id!!)
    }

    protected fun getRandomQuestion() {
        viewModel.isLoading = true
        viewModel.currentErrorCode = QuestionActivityErrorCode.NONE

        questionService.getRandomQuestion(
            this::onRandomQuestionFound,
            this::onConnectivityError,
            this::onServerError
        )
    }

    protected fun chooseQuestion1(id: UUID) {
        viewModel.userHasAnswered = true
        questionService.postQuestion1(
            id.toString(),
            this::onQuestionChoose,
            this::onConnectivityError,
            this::onServerError
        )
    }

    protected fun chooseQuestion2(id: UUID) {
        viewModel.userHasAnswered = true
        questionService.postQuestion2(
            id.toString(),
            this::onQuestionChoose,
            this::onConnectivityError,
            this::onServerError
        )
    }

    @UiThread
    protected fun onRandomQuestionFound(question: Question) {
        this.question = question
        viewModel.question = question
        viewModel.isLoading = false
        viewModel.userHasAnswered = false
    }

    @UiThread
    protected fun onQuestionChoose(question: Question) {
        this.question = question
        viewModel.question = question
    }

    @UiThread
    protected fun onConnectivityError() {
        viewModel.currentErrorCode = QuestionActivityErrorCode.CONNECTIVITY
        viewModel.isLoading = false
    }

    @UiThread
    protected fun onServerError() {
        viewModel.isLoading = false
        viewModel.currentErrorCode = QuestionActivityErrorCode.SERVER
    }
}
