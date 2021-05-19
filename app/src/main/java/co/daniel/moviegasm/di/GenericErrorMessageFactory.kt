package co.daniel.moviegasm.di

import co.daniel.moviegasm.domain.ReturnResult

interface GenericErrorMessageFactory {
    fun getErrorMessage(throwable: Throwable): CharSequence

    fun getErrorMessage(throwable: Throwable, defaultText: Int): CharSequence

    fun <T> getSpecificType(throwable: Throwable, defaultText: Int): ReturnResult<T>

    fun <T> getError(throwable: Throwable, defaultText: Int): ReturnResult<T>

    fun <T> getLoginError(throwable: Throwable, defaultText: Int): ReturnResult<T>
}