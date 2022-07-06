package com.example.dushanbe.utils.base

import android.util.Log
import com.example.dushanbe.networking.interceptors.InternetUnavailableException

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRemoteRepository {

    companion object {
//        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    /**
     * Function that executes the given function on Dispatchers.IO context and switch to Dispatchers.Main context when an error occurs
     * @param callFunction is the function that is returning the wanted object. It must be a suspend function. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
//    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Resource<T> {
//        return withContext(dispatcher) {
//            try {
//                Resource.Success(apiCall.invoke())
//            } catch (throwable: Throwable) {
//                when (throwable) {
//                    is IOException -> Resource.Error(throwable.message?:"")
//                    is HttpException -> {
//                        val code = throwable.code()
//                        val errorResponse = convertErrorBody(throwable)
//                        Resource.Error(message = errorResponse?:"",code)
//                    }
//                    else -> {
//                        Resource.Empty()
//                    }
//                }
//            }
//        }
//    }
//
//    private fun convertErrorBody(throwable: HttpException): String? {
//        return try {
//            throwable.response()?.errorBody()?.source()?.let {
////                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
////                moshiAdapter.fromJson(it)
//                it.buffer.toString()
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }
//    suspend inline fun <T> safeApiCall(emitter: RemoteErrorEmitter, crossinline callFunction: suspend () -> T): T? {
//        return try{
//            val myObject = withContext(Dispatchers.Main){ callFunction.invoke() }
//            myObject
//        }catch (e: Exception){
//            withContext(Dispatchers.IO){
//                e.printStackTrace()
//                when(e){
//                    is HttpException -> {
//                        if(e.code() == 401) emitter.onError(ErrorType.SESSION_EXPIRED)
//                        else {
//                            val body = e.response()?.errorBody()
//                            emitter.onError(getErrorMessage(body))
//                        }
//                    }
//                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
//                    is IOException -> emitter.onError(ErrorType.NETWORK)
//                    else -> emitter.onError(ErrorType.UNKNOWN)
//                }
//            }
//            null
//        }
//    }

    /**
     * Function that executes the given function in whichever thread is given. Be aware, this is not friendly with Dispatchers.IO,
     * since [RemoteErrorEmitter] is intended to display messages to the user about error from the server/DB.
     * @param callFunction is the function that is returning the wanted object. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
    inline fun <T> safeApiCallNoContext(emitter: RemoteErrorEmitter, callFunction: () -> T): T? {
        return try{
            val myObject = callFunction.invoke()
            myObject
        }catch (e: Exception){
            Log.v("tag","exception $e")
            e.printStackTrace()
            when(e){
                is HttpException -> {
                    val body = e.response()?.errorBody()
                    if(e.code() == 401) {
                        emitter.onError(ErrorType.SESSION_EXPIRED)
                    }
                    else {
                        emitter.onError(getErrorMessage(body))
                    }
                    emitter.onError(ErrorType.BAD_REQUEST)
                }
                is UnknownHostException -> emitter.onError(ErrorType.HOST_EXCEPTION)
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                is InternetUnavailableException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}