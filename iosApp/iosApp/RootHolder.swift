//
//  RootHolder.swift
//  iosApp
//
//  Created by Shahryar on 5/25/23.
//

import Foundation
import shared

//class RootHolder : ObservableObject {
//    let lifecycle: LifecycleRegistry
//    let root: RootComponent
//
//    init() {
//        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
//
//        root = DefaultRootComponent(
//            componentContext: DefaultComponentContext(lifecycle: lifecycle)
//        )
//
//        LifecycleRegistryExtKt.create(lifecycle)
//    }
//
//    deinit {
//        // Destroy the root component before it is deallocated
//        LifecycleRegistryExtKt.destroy(lifecycle)
//    }
//}
