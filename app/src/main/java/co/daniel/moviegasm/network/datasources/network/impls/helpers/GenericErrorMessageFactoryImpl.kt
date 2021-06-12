package co.daniel.moviegasm.network.datasources.network.impls.helpers

import android.content.Context
import co.daniel.moviegasm.R
import co.daniel.moviegasm.network.datasources.network.exception.NetworkException
import co.daniel.moviegasm.network.datasources.network.exception.NetworkExceptionMessageFactory
import co.daniel.moviegasm.di.GenericErrorMessageFactory
import co.daniel.moviegasm.domain.ReturnResult
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GenericErrorMessageFactoryImpl @Inject constructor(
    private val context: Context,
    private val networkExceptionMessageFactory: NetworkExceptionMessageFactory
) : GenericErrorMessageFactory {
    override fun getErrorMessage(throwable: Throwable): CharSequence {
        return getErrorMessage(throwable, 0)
    }

    override fun <T> getLoginError(throwable: Throwable, defaultText: Int): ReturnResult<T> {
        return when (throwable) {
            is UnknownHostException -> ReturnResult.NetworkErrorResult
            is SocketTimeoutException -> ReturnResult.NetworkErrorResult
            is ConnectException -> ReturnResult.NetworkErrorResult
            is NetworkException -> ReturnResult.ErrorResult(getErrorMessage(throwable))
            else -> ReturnResult.ErrorResult(getErrorMessage(throwable, defaultText))
        }
    }

    override fun getErrorMessage(throwable: Throwable, defaultText: Int): CharSequence {
        return when (throwable) {
            is UnknownHostException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is SocketTimeoutException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is ConnectException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is NetworkException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            else -> {
                return try {
                    context.getString(defaultText)
                } catch (e: Exception) {
                    throwable.message ?: context.getString(R.string.error_generic)
                }
            }
        }
    }

    override fun <T> getSpecificType(throwable: Throwable, defaultText: Int): ReturnResult<T> {
        return when (throwable) {
            is UnknownHostException -> ReturnResult.NetworkErrorResult
            is SocketTimeoutException -> ReturnResult.NetworkErrorResult
            is ConnectException -> ReturnResult.NetworkErrorResult
            is NetworkException -> {
                return when (throwable.errorCode) {
                    401 -> ReturnResult.SessionExpired
                    else -> ReturnResult.ErrorResult(getErrorMessage(throwable, defaultText))
                }
            }
            else -> ReturnResult.ErrorResult(getErrorMessage(throwable, defaultText))
        }
    }

    override fun <T> getError(throwable: Throwable, defaultText: Int): ReturnResult<T> {
        return when (throwable) {
            is UnknownHostException -> ReturnResult.NetworkErrorResult
            is SocketTimeoutException -> ReturnResult.NetworkErrorResult
            is ConnectException -> ReturnResult.NetworkErrorResult
            else -> getNetworkErrorType(throwable as NetworkException, defaultText)
        }
    }

    private fun <T> getNetworkErrorType(
        networkException: NetworkException,
        defaultText: Int
    ): ReturnResult<T> {
        return when (networkException.errorCode) {
            301 -> ReturnResult.NewVersion
            401 -> ReturnResult.SessionExpired
            402 -> ReturnResult.PaymentOverdue
            503 -> ReturnResult.ServerMaintenance
            else -> ReturnResult.ErrorResult(getErrorMessage(networkException, defaultText))
        }
    }

}