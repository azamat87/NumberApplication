package com.app.numberapplication.numbers.presentation

import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {

    @Test
    fun `test init`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.init(isFirstRun = true)

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])


    }

    private class TestNumberCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()

        val initCalledList = mutableListOf<NumbersResult>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {

        }

        override fun showList(list: List<NumberUi>) {

        }

    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result : NumbersResult = NumbersResult.Sueccess()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init() : NumbersResult {
            return result
        }

    }

}