//
//  ContentView.swift
//  iosApp
//
//  Created by shahryar khosravi on 3/3/22.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        NavigationView {
            PricesScreen()
                .navigationTitle("Prices")
                .toolbar(content: {
                    NavigationLink(destination: {
                        SettingsScreen()
                    }, label: {
                        Image(systemName: "gear")
                    })
                })
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
