//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Shahryar on 5/3/22.
//

import SwiftUI
import shared

struct SettingsScreen: View {
    
    @ObservedObject var viewModel: SettingsViewModel = SettingsViewModel()
    
    var body: some View {
        Form {
            Section(header: Text("Api Key"), content: {
                TextField("Api key", text: $viewModel.apiKey)
            })
        }
        .navigationTitle("Settings")
        .toolbar(content: {
            Button(action: {
                CryptoPriceSettings().saveSetting(key: "token", value: viewModel.apiKey)
            }, label: {
                Image(systemName: "checkmark.circle")
            })
        })
    }
}

struct SettingsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingsScreen()
    }
}
