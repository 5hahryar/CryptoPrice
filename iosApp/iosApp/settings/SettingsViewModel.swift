//
//  SettingsViewModel.swift
//  iosApp
//
//  Created by Shahryar on 5/3/22.
//

import Foundation
import shared

class SettingsViewModel: ObservableObject {
    
    @Published var apiKey: String = CryptoPriceSettings().getSetting(key: "token") ?? ""
}
