package com.app.numberapplication.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.numberapplication.numbers.domain.NumberFact
import com.app.numberapplication.numbers.domain.NumberUiMapper
import com.app.numberapplication.numbers.domain.NumbersInteractor
import com.app.numberapplication.numbers.domain.NumbersResult
import kotlinx.coroutines.launch

class NumbersViewModel (
    private val communication: NumbersCommunications,
    private val numbersInteractor: NumbersInteractor,
    private val numbersResultMapper: NumbersResult.Mapper<Unit>
) : ViewModel(), ObserveNumbers, FetchNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
        communication.observeProgress(owner, observer)
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observeState(owner, observer)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
        communication.observeList(owner, observer)
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communication.showProgress(true)
            viewModelScope.launch {
                val result = numbersInteractor.init()
                communication.showProgress(false)
                result.map(numbersResultMapper)
            }
        }

    }

    override fun fetchRandomNumberFact() {
        TODO("Not yet implemented")
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }
}

interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}