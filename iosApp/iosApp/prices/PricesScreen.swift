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
    
    var body: some View {
        
        ScrollView {
            ForEach(currencies, id: \.id) { item in
                CurrencyItem(currency: item)
            }
        }
    }
    
    struct CurrencyItem: View {
        
        let currency: CurrencyDto
        
        var body: some View {
            ZStack(alignment: .topLeading) {
                RoundedRectangle(cornerRadius: 20, style: .continuous)
                    .fill(Color(red: 255, green: 245, blue: 158))
                    .shadow(radius: 1)
                
                
                HStack {
                    VStack(alignment: .leading) {
                        Text(currency.symbol).font(.subheadline
                        ).foregroundColor(.secondary)
                            .lineLimit(1)
                            .minimumScaleFactor(0.01)
                        Text(currency.name).font(.headline)
                            .lineLimit(1)
                            .minimumScaleFactor(0.01)
                        Spacer().frame(height: 10)
                        
                        Text("\(currency.percent_change_24h, specifier: "%.2f")%").font(.subheadline).foregroundColor(currency.percent_change_24h > 0 ? .green : .red)
                            .lineLimit(1)
                            .minimumScaleFactor(0.01)
                        Text("$\(currency.price, specifier: "%.2f")").font(.headline)
                            .lineLimit(1)
                            .minimumScaleFactor(0.01)
                    }.padding(15)
                    Spacer()
                    VStack(alignment: .leading) {
                        Text("Market cap").foregroundColor(.secondary)
                            .lineLimit(1)
                        let markCap = Double(currency.market_cap) ?? 0.0
                        Text("$\(markCap, specifier: "%.2f")").font(.headline)
                            .lineLimit(1)
                            .minimumScaleFactor(0.01)
                        Spacer().frame(height: 10)
                        
                        HStack {
                            VStack(alignment: .leading) {
                                Text("30 Day").font(.subheadline).foregroundColor(.secondary)
                                    .lineLimit(1)
                                Text("\(currency.percent_change_30d, specifier: "%.2f")%"
                                ).lineLimit(1).foregroundColor(currency.percent_change_30d > 0 ? .green : .red).font(.headline)
                                    .lineLimit(1)
                                    .minimumScaleFactor(0.01)
                            }
                            Spacer().frame(width: 20)
                            VStack(alignment: .leading) {
                                Text("7 Day").font(.subheadline).foregroundColor(.secondary)
                                Text("\(currency.percent_change_7d, specifier: "%.2f")%").lineLimit(1).foregroundColor(currency.percent_change_7d > 0 ? .green : .red).font(.headline)
                                    .minimumScaleFactor(0.01)
                            }
                        }
                    }.padding(20)
                }
                
            }
            .frame(width: .infinity)
            .padding()
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
