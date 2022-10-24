package com.app.numberapplication.numbers.presentation

import com.app.numberapplication.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val communication: NumbersCommunications,
    private val numbersInteractor: NumbersInteractor
) {
}