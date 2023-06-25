package com.shahryar.shared.ui
//
//import com.arkivanov.decompose.ComponentContext
//import com.arkivanov.decompose.router.stack.ChildStack
//import com.arkivanov.decompose.router.stack.StackNavigation
//import com.arkivanov.decompose.router.stack.childStack
//import com.arkivanov.decompose.router.stack.pop
//import com.arkivanov.decompose.router.stack.push
//import com.arkivanov.decompose.value.Value
//import com.arkivanov.essenty.parcelable.Parcelable
//import com.arkivanov.essenty.parcelable.Parcelize
//import com.shahryar.shared.ui.prices.PricesComponent
//import com.shahryar.shared.ui.settings.SettingsComponent
//
//class DefaultRootComponent(
//    componentContext: ComponentContext,
//) : RootComponent, ComponentContext by componentContext {
//
//    private val navigation = StackNavigation<Config>()
//
////    private val _stack =
////        childStack(
////            source = navigation,
////            initialConfiguration = Config.Prices, // The initial child component is List
////            handleBackButton = true, // Automatically pop from the stack on back button presses
////            childFactory = ::child,
////        )
//
//    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack
//
////    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
////        when (config) {
////            is Config.Prices -> RootComponent.Child.PricesComponent(pricesComponent(componentContext))
////            is Config.Settings -> RootComponent.Child.SettingsComponent(
////                settingsComponent(
////                    componentContext,
////                    config
////                )
////            )
////        }
//
////    private fun pricesComponent(componentContext: ComponentContext): PricesComponent =
////        PricesComponent(
////            componentContext = componentContext,
////            onNavigateToSettings = {
////                navigation.push(Config.Settings)
////            },
////        )
//
//    private fun settingsComponent(
//        componentContext: ComponentContext,
//        config: Config.Settings
//    ): SettingsComponent =
//        SettingsComponent(
//            componentContext = componentContext,
//        )
//
//    @Parcelize // The `kotlin-parcelize` plugin must be applied if you are targeting Android
//    private sealed interface Config : Parcelable {
//        object Prices : Config
//        object Settings : Config
//    }
//}