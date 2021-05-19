package co.daniel.moviegasm.viewmodel

import androidx.lifecycle.ViewModel
import co.daniel.moviegasm.di.GenericErrorMessageFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    @Inject
    lateinit var genericErrorMessageFactory: GenericErrorMessageFactory


    fun Disposable.addToCompositeDisposable(): Disposable = apply { compositeDisposable.add(this) }

}