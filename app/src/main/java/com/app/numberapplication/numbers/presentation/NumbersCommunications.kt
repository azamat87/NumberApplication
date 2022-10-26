package com.app.numberapplication.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers{

    fun showProgress(show: Boolean)

    fun showState(uiState: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val progressCommunication : ProgressCommunication,
        private val numberStateCommunication : NumberStateCommunication,
        private val numberListCommunication : NumberListCommunication
    ) : NumbersCommunications{

        override fun showProgress(show: Boolean) = progressCommunication.map(show)

        override fun showState(uiState: UiState) = numberStateCommunication.map(uiState)

        override fun showList(list: List<NumberUi>) = numberListCommunication.map(list)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
            progressCommunication.observe(owner, observer)
        }

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
            numberStateCommunication.observe(owner, observer)
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            numberListCommunication.observe(owner, observer)
        }

    }
}

interface ObserveNumbers {

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observeState(owner: LifecycleOwner, observer: Observer<UiState>)
    fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication : Communication.Mutable<Boolean> {
    class Base : Communication.Post<Boolean>(), ProgressCommunication
}

interface NumberStateCommunication : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(), NumberStateCommunication
}

interface NumberListCommunication : Communication.Mutable<List<NumberUi>> {
    class Base : Communication.Post<List<NumberUi>>(), NumberListCommunication
}