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

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Sueccess(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.timesShowList)


        // get data
        interactor.changeExpectedResult(NumbersResult.Failure())
        viewModel.fetchRandomNumberData()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error(), communications.stateCalledList[0])
        assertEquals(1, communications.timesShowList)

        viewModel.init(isFirstRun = false)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.timesShowList)
    }

    @Test
    fun `fact about empty number`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        // init
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.fetchFact("")
        assertEquals(0, interactor.fetchAboutNumberList.size)

        assertEquals(0, communications.progressCalledList.size)

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)

    }

    @Test
    fun `fact about some number`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        // init
        val viewModel = NumbersViewModel(communications, interactor)

        interactor.changeExpectedResult(NumbersResult.Success(listOf(Number("1", "number fact info"))))
        viewModel.fetchFact("1")
        //check
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberList.size)
        assertEquals(Number("1", "number fact info"), interactor.fetchAboutNumberList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("1", "number fact info"), communications.numbersList[0])
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
        val fetchAboutNumberList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberList = mutableListOf<NumbersResult>()


        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init() : NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(number: String): NumbersResult {
            fetchAboutRandomNumberList.add(result)
            return result
        }

    }

}