package co.daniel.moviegasm.network.datasources.network.impls.helpers

import android.content.Context
import co.daniel.moviegasm.R

import co.daniel.moviegasm.network.datasources.network.exception.NetworkException
import co.daniel.moviegasm.network.datasources.network.exception.NetworkExceptionMessageFactory
import okhttp3.ResponseBody
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkExceptionMessageFactoryImpl @Inject constructor(
    private val context: Context
) : NetworkExceptionMessageFactory {

    override fun getErrorMessage(networkException: NetworkException): CharSequence {
        Timber.e("error code in string : ${networkException.errorCode}")
        return when (networkException.errorCode) {
            400 -> parseMessageFromErrorBody(networkException.errorBody)
            401 -> parseMessageFromErrorBody(networkException.errorBody)
            403 -> parseMessageFromErrorBody(networkException.errorBody)
            404 -> context.getString(R.string.error_server_404)
            408 -> parseMessageFromErrorBody(networkException.errorBody)
            409 -> parseMessageFromErrorBody(networkException.errorBody)
            422 -> parseMessageFromErrorBody(networkException.errorBody)
            500 -> context.getString(R.string.error_server_500)
            503 -> context.getString(R.string.error_server_503)
            else -> context.getString(R.string.error_generic)
        }
    }

    private fun parseMessageFromErrorBody(errorBody: ResponseBody?): CharSequence {

        if (errorBody == null) {
            return context.getString(R.string.error_generic)
        }

        val errorBodyInString =
            try {
                errorBody.string()
            } catch (e: Exception) {
                Timber.wtf(e)
                ""
            }

        Timber.e("error body in string : $errorBodyInString")


//        try {
//            val errorBodyJson = JSONObject(errorBodyInString)
//            return errorBodyJson.getString("message")
//        } catch (exception: Exception) {
//            this.javaClass.showLogE("Error in parsing error message body ${exception.message}")
//        }

        return errorBodyInString
    }

    override fun getErrorMessage(unknownHostException: UnknownHostException): CharSequence {
        return context.getString(R.string.error_no_internet)
    }

    override fun getErrorMessage(socketTimeoutException: SocketTimeoutException): CharSequence {
        return context.getString(R.string.error_socket_timeout)
    }

    override fun getErrorMessage(connectException: ConnectException): CharSequence {
        return context.getString(R.string.error_no_internet)
    }


}