package com.app.numberapplication.numbers.presentation

import com.app.numberapplication.numbers.domain.NumberFact
import com.app.numberapplication.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) = communications.showState(
        if (errorMessage.isEmpty()) {
            val numberList = list.map { it.map(mapper) }
            if (numberList.isNotEmpty())
                communications.showList(numberList)
            UiState.Success()
        } else
            UiState.Error(errorMessage)
    )

}
