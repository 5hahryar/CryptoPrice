//
//  PriceSheet.swift
//  iosApp
//
//  Created by Shahryar on 5/6/22.
//

import SwiftUI
import shared

struct PriceSheet: View {
    
    let currency: Currency?
    
    var body: some View {
        if let currency = currency {
            VStack {
                VStack {
                    Text(currency.name)
                        .font(
                            .system(size: 20)
                                .weight(.semibold)
                        ).foregroundColor(.primary)
                    
                    Text(currency.price).font(.system(size: 20).weight(.medium))
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
                            
                            Text("\(currency.percentChange1h)%").foregroundColor(Int(currency.percentChange1h) ?? 0 > 0 ? .green : .red)
                                .lineLimit(1)
                                .font(.system(size: 20))
                        }
                        Spacer()
                        VStack(alignment: .leading) {
                            Text("24 Hour")
                                .font(
                                    .system(size: 20)
                                ).foregroundColor(.secondary)
                            
                            Text("\(currency.percentChange24h)%").foregroundColor(Int(currency.percentChange24h) ?? 0 > 0 ? .green : .red)
                                .lineLimit(1)
                                .font(.system(size: 20))
                        }
                        Spacer()
                        VStack(alignment: .leading) {
                            Text("7 Days")
                                .font(
                                    .system(size: 20)
                                ).foregroundColor(.secondary)
                            
                            Text("\(currency.percentChange7d)%").foregroundColor(Int(currency.percentChange7d) ?? 0 > 0 ? .green : .red)
                                .lineLimit(1)
                                .font(.system(size: 20))
                        }
                    }.padding()
                    
                }.padding()
                
                GeometryReader { geo in
                    HStack {
                        LabledText(title: "Total Supply", text: currency.totalSupply)
                            .frame(width: geo.size.width * 0.5)
                        LabledText(title: "Circulating Supply", text: currency.circulatingSupply)
                            .frame(width: geo.size.width * 0.5)
                    }
                }.frame(height: 90)
                
                GeometryReader { geo in
                    HStack {
                        LabledText(title: "Max Supply", text: currency.maxSupply)
                            .frame(width: geo.size.width * 0.5)
                        LabledText(title: "24 Hour Volume", text: currency.volume24h)
                            .frame(width: geo.size.width * 0.5)
                    }
                }.frame(height: 90)
                
                GeometryReader { geo in
                    HStack {
                        LabledText(title: "Market Cap", text: currency.marketCap)
                            .frame(width: geo.size.width * 0.5)
                        LabledText(title: "CMC Rank", text: currency.cmcRank.description)
                            .frame(width: geo.size.width * 0.5)
                    }
                }.frame(height: 80)
            }
        }
    }
}

private struct LabledText: View {
    
    let title: String
    let text: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(
                    .subheadline
                ).foregroundColor(.secondary)
            
            Text(text)
                .font(
                    .body
                ).foregroundColor(.primary)
        }
    }
}

//struct PriceSheet_Previews: PreviewProvider {
//    static var previews: some View {
//        PriceSheet(currency: getFakeCurrency())
//    }
//}
