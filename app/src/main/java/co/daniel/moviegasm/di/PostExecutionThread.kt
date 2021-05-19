package co.daniel.moviegasm.di

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler

}