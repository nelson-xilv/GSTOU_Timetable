package com.nelsonxilv.gstoutimetable.di

import javax.inject.Qualifier

enum class TimetableDispatchers {
    Default,
    IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher(val dispatcher: TimetableDispatchers)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultCoroutineExceptionHandler
