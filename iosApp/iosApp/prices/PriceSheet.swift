//
//  PriceSheet.swift
//  iosApp
//
//  Created by Shahryar on 5/6/22.
//

import SwiftUI
import shared

struct PriceSheet: View {
    
    let currency: CurrencyDto
    
    var body: some View {
        VStack {
            VStack {
            Text(currency.name)
                .font(
                    .system(size: 20)
                    .weight(.semibold)
                ).foregroundColor(.primary)
            
            Text("$\(currency.price, specifier: "%.2f")").font(.system(size: 20).weight(.medium))
                .lineLimit(1)
                .minimumScaleFactor(0.01)
            }.padding()
            
            ZStack(alignment: .leading) {
                RoundedRectangle(cornerRadius: 20, style: .continuous)
                    .fill(Color(red: 245/255, green: 245/255, blue: 245/255))
                
                HStack {
                    VStack(alignment: .leading) {
                        Text("1 Hour")
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.secondary)
                        
                        Text("\(currency.percent_change_1h, specifier: "%.2f")%").foregroundColor(currency.percent_change_24h > 0 ? .green : .red)
                            .lineLimit(1)
                            .font(.system(size: 20))
                    }
                    Spacer()
                    VStack(alignment: .leading) {
                        Text("24 Hour")
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.secondary)
                        
                        Text("\(currency.percent_change_1h, specifier: "%.2f")%").foregroundColor(currency.percent_change_24h > 0 ? .green : .red)
                            .lineLimit(1)
                            .font(.system(size: 20))
                    }
                    Spacer()
                    VStack(alignment: .leading) {
                        Text("7 Days")
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.secondary)
                        
                        Text("\(currency.percent_change_1h, specifier: "%.2f")%").foregroundColor(currency.percent_change_24h > 0 ? .green : .red)
                            .lineLimit(1)
                            .font(.system(size: 20))
                    }
                }.padding()
                
            }.padding()
            
            GeometryReader { geo in
            HStack {
                VStack(alignment: .leading) {
                    Text("Total Supply")
                        .font(
                            .system(size: 20)
                        ).foregroundColor(.secondary)
                    Spacer()
                    Text("$\(currency.total_supply, specifier: "%.2f")").font(.system(size: 20).weight(.medium))
                        .lineLimit(1)
                        .minimumScaleFactor(0.01)
                }.frame(width: geo.size.width * 0.5)
                VStack(alignment: .leading) {
                    Text("Circulating Supply")
                        .font(
                            .system(size: 20)
                        ).foregroundColor(.secondary)
                    Spacer()
                    Text("$\(currency.circulating_supply, specifier: "%.2f")").font(.system(size: 20).weight(.medium))
                        .lineLimit(1)
                        .minimumScaleFactor(0.01)
                }.frame(width: geo.size.width * 0.5)
            }
            }.padding().frame(height: 90)
            
            GeometryReader { geo in
            HStack {
                VStack(alignment: .leading) {
                    Text("Max Supply")
                        .font(
                            .system(size: 20)
                        ).foregroundColor(.secondary)
                    Spacer()
                    Text("$\(Double(currency.max_supply ?? 0), specifier: "%.2f")").font(.system(size: 20).weight(.medium))
                        .lineLimit(1)
                        .minimumScaleFactor(0.01)
                }.frame(width: geo.size.width * 0.5)
                VStack(alignment: .leading) {
                    Text("24 Hour Volume")
                        .font(
                            .system(size: 20)
                        ).foregroundColor(.secondary)
                    Spacer()
                    Text("$\(currency.volume_24h, specifier: "%.2f")").font(.system(size: 20).weight(.medium))
                        .lineLimit(1)
                        .minimumScaleFactor(0.01)
                }.frame(width: geo.size.width * 0.5)
            }
            }.padding().frame(height: 90)
            
            GeometryReader { geo in
            HStack {
                VStack(alignment: .leading) {
                        Text("Market Cap")
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.secondary)
                        
                        Text(currency.market_cap.description)
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.primary)
                    }.frame(width: geo.size.width * 0.5)
                    
                    VStack(alignment: .leading) {
                        Text("CMC Rank")
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.secondary)
                        Spacer()
                        Text(currency.cmc_rank.description)
                            .font(
                                .system(size: 20)
                            ).foregroundColor(.primary)
                    }.frame(width: geo.size.width * 0.5)
                
            }
            }.padding().frame(height: 80)
        }
    }
}

struct PriceSheet_Previews: PreviewProvider {
    static var previews: some View {
        PriceSheet(currency: getFakeCurrency())
    }
}
