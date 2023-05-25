package com.shahryar.shared.ui

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    // Defines all possible child components
    sealed class Child {
        class PricesComponent(val component: com.shahryar.shared.ui.prices.PricesComponent) : Child()
        class SettingsComponent(val component: com.shahryar.shared.ui.settings.SettingsComponent) : Child()
    }
}