//
//  PricesScreen.swift
//  iosApp
//
//  Created by shahryar khosravi on 3/4/22.
//

import SwiftUI
import shared

struct PricesScreen: View {
    
    @ObservedObject var viewModel = PricesViewModel()
    
    var body: some View {
        if viewModel.isLoading {
            ProgressView()
        } else {
            if !viewModel.currencies.isEmpty {
                PricesList(currencies: viewModel.currencies)
            } else {
                Text("Prices not found")
            }
        }
        
    }
}

private struct PricesList: View {
    
    var currencies: [CurrencyDto]
    @State private var showSheet: Bool = false
    @State private var selectedCurrency: CurrencyDto?
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())]) {
                ForEach(currencies, id: \.id) { item in
                    CurrencyItem(currency: item)
                        .onTapGesture {
                            selectedCurrency = item
                            showSheet = true
                        }
                        .sheet(isPresented: $showSheet, content: {
                            if selectedCurrency != nil {
                                SheetView(currency: selectedCurrency!)
                            }
                        })
                }
            }.padding(10)
        }
    }
    
    
    struct SheetView: View {
        @Environment(\.dismiss) var dismiss
        let currency: CurrencyDto

        var body: some View {
            PriceSheet(currency: currency)
        }
    }
    
    struct CardModifier: ViewModifier {
        func body(content: Content) -> some View {
            content
                .cornerRadius(20)
                .shadow(color: Color.black.opacity(0.2), radius: 20, x: 0, y: 0)
        }
        
    }
    
    struct CurrencyItem_Previews: PreviewProvider {
        static var previews: some View {
            CurrencyItem(currency: CurrencyDto(id: 1, circulating_supply: 6847310.349, cmc_rank: 1, date_added: "", last_updated: "", max_supply: 150000000, name: "Bitcoin", symbol: "BTC", total_supply: 15000000, market_cap: "1236438124", percent_change_1h: 12.34, percent_change_24h: 2.3, percent_change_30d: 55.23, percent_change_7d: -23.32, price: 42342.45, volume_24h: 12353085))
        }
    }
    
    struct PricesScreen_Previews: PreviewProvider {
        static var previews: some View {
            PricesScreen()
        }
    }
    
}