//
//  iosAppApp.swift
//  iosApp
//
//  Created by shahryar khosravi on 3/3/22.
//

import SwiftUI
import shared

@main
struct CryptoPriceApp: App {
    
    init() {
        KoinKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
