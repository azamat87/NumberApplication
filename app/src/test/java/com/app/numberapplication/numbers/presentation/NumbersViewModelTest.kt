package com.app.numberapplication.numbers.presentation

import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {

    @Test
    fun `test init`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        // init
        val viewModel = NumbersViewModel(communications, interactor)
        interactor.changeExpectedResult(NumbersResult.Success())
        //action
        viewModel.init(isFirstRun = true)

        //check
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Sueccess(), communications.stateCalledList[0])

        assertEquals(emptyList<NumberUi>(), communications.numbersList)
        assertEquals(1, communications.timesShowList)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])


        // get data
        interactor.changeExpectedResult(NumbersResult.Failure())
        viewModel.fetchRandomNumberData()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error(), communications.stateCalledList[0])
        assertEquals(1, communications.timesShowList)

        viewModel.init(isFirstRun = false)



    }

    private class TestNumberCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()

        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result : NumbersResult = NumbersResult.Sueccess()
        val initCalledList = mutableListOf<NumbersResult>()


        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init() : NumbersResult {
            initCalledList.add(result)
            return result
        }

    }

}