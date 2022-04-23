package com.shahryar.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
//    Napier.d("Init koin called")
    appDeclaration()
    modules(commonModule, platformModule())
}

// Called by iOS
fun initKoin() = initKoin{}

val commonModule = module {

}