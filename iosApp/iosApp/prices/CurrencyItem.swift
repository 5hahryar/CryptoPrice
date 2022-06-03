//
//  CurrencyItem.swift
//  CryptoPrice
//
//  Created by shahryar khosravi on 3/9/22.
//

import Foundation
import SwiftUI
import shared

struct CurrencyItem: View {
    
    let currency: Currency
    
    var body: some View {
        ZStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: 20, style: .continuous)
                .fill(Color(red: 245/255, green: 245/255, blue: 245/255))
            
            VStack(alignment: .leading) {
                Text(currency.symbol).font(
                    .system(size: 20)).foregroundColor(.secondary)
                    .lineLimit(1)
                Text(currency.name).font(.system(size: 25).weight(.medium))
                    .lineLimit(1)
                    .minimumScaleFactor(0.01)
                Spacer()
                
                Text("\(currency.percentChange24h)%").foregroundColor(Int(currency.percentChange24h) ?? 0 > 0 ? .green : .red)
                    .lineLimit(1)
                    .font(.system(size: 20))
                Text("\(currency.price)").font(.system(size: 25).weight(.medium))
                    .lineLimit(1)
                    .minimumScaleFactor(0.01)
            }.padding()
        }
        .frame(width: 140, height: 140).padding(10)
        
    }
}

struct CurrencyItemWide: View {
    
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
                    Text(currency.name).font(.headline)
                        .lineLimit(1)
                    Spacer().frame(height: 10)
                    
                    Text("\(currency.percent_change_24h, specifier: "%.2f")%").font(.subheadline).foregroundColor(currency.percent_change_24h > 0 ? .green : .red)
                        .lineLimit(1)
                    Text("$\(currency.price, specifier: "%.2f")").font(.headline)
                        .lineLimit(1)
                }.padding(15)
                Spacer()
                VStack(alignment: .leading) {
                    Text("Market cap").foregroundColor(.secondary)
                        .lineLimit(1)
                    let markCap = Double(currency.market_cap) ?? 0.0
                    Text("$\(markCap, specifier: "%.2f")").font(.headline)
                        .lineLimit(1)
                    Spacer().frame(height: 10)
                    
                    HStack {
                        VStack(alignment: .leading) {
                            Text("30 Day").font(.subheadline).foregroundColor(.secondary)
                                .lineLimit(1)
                            Text("\(currency.percent_change_30d, specifier: "%.2f")%"
                            ).lineLimit(1).foregroundColor(currency.percent_change_30d > 0 ? .green : .red).font(.headline)
                                .lineLimit(1)
                        }
                        Spacer().frame(width: 20)
                        VStack(alignment: .leading) {
                            Text("7 Day").font(.subheadline).foregroundColor(.secondary)
                            Text("\(currency.percent_change_7d, specifier: "%.2f")%").lineLimit(1).foregroundColor(currency.percent_change_7d > 0 ? .green : .red).font(.headline)
                        }
                    }
                }.padding(20)
            }
            
        }
        .frame(width: .infinity)
        .padding()
    }
}
