package co.daniel.moviegasm.domain

sealed class ReturnResult<out T> {
    class ErrorResult<T>(var errorMsg: Any, var data: T? = null) : ReturnResult<T>()
    class PositiveResult<T>(var data: T) : ReturnResult<T>()
    class ValidationErrorResult<T>(var msg: Any) : ReturnResult<T>()
    object EmptyResult : ReturnResult<Nothing>()
    object PaymentOverdue : ReturnResult<Nothing>()
    object ServerMaintenance : ReturnResult<Nothing>()
    object SessionExpired : ReturnResult<Nothing>()
    object NewVersion : ReturnResult<Nothing>()
    object NetworkErrorResult : ReturnResult<Nothing>()


    override fun toString(): String {
        return getReturnResultType(this)
    }

    fun getContent(): String {
        return when (this) {
            is PositiveResult -> {
                "${this.data ?: "Success"}"
            }
            is ErrorResult -> {
                "${this.errorMsg}"
            }
            is NetworkErrorResult -> {
                "Network error occurred "
            }
            is EmptyResult -> {
                "No data to display"
            }
            is ValidationErrorResult -> {
                "validation failed :: ${this.msg}"
            }

            is NewVersion -> {
                "NewUpdate"
            }
            is PaymentOverdue -> {
                "MembershipExpired"
            }
            is SessionExpired -> {
                "Session expired"
            }
            else -> {
                "Something went wrong"
            }
        }
    }
}

fun <T> ReturnResult<T>.compare(other: ReturnResult<T>, inTestMode: Boolean = false): Boolean {
    return if (this.javaClass != other.javaClass)
        false
    else {
        showInLog(this, other, inTestMode)
        when (this) {
            is ReturnResult.PositiveResult -> {
                this.data != null && (other as ReturnResult.PositiveResult).data != null && (this as ReturnResult.PositiveResult).data!! == other.data
            }
            is ReturnResult.ErrorResult -> {
                this.data == (other as ReturnResult.ErrorResult).data && this.errorMsg == other.errorMsg
            }

            is ReturnResult.ValidationErrorResult -> {
                this.msg == (other as ReturnResult.ValidationErrorResult).msg
            }
            else -> {
                true
            }
        }
    }
}

private fun <T> showInLog(`this`: ReturnResult<T>, that: ReturnResult<T>, inTestMode: Boolean) {
    if (inTestMode) {
        println("this : ${getReturnResultType(`this`)}")
        println("that : ${getReturnResultType(that)}")
    } else {
        //do nothing
    }
}


fun getReturnResultType(returnResult: ReturnResult<*>): String {
    return when (returnResult) {
        is ReturnResult.PositiveResult -> {
            "PositiveResult(${returnResult.data ?: "no data content"})"
        }
        is ReturnResult.ErrorResult -> {
            "ErrorResult(${returnResult.errorMsg},${returnResult.data ?: "no data content"})"
        }
        is ReturnResult.NetworkErrorResult -> {
            "NetworkError"
        }
        is ReturnResult.EmptyResult -> {
            "EmptyResult"
        }
        is ReturnResult.ValidationErrorResult -> {
            "ValidationErrorResult(${returnResult.msg})"
        }

        is ReturnResult.NewVersion -> {
            "NewUpdate"
        }
        is ReturnResult.PaymentOverdue -> {
            "MembershipExpired"
        }
        is ReturnResult.SessionExpired -> {
            "SessionExpired"
        }
        else -> {
            "SomethingWentWrong"
        }
    }
}