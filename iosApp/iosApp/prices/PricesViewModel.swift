//
//  PricesViewModel.swift
//  iosApp
//
//  Created by shahryar khosravi on 3/4/22.
//

import Foundation
import shared

class PricesViewModel: ObservableObject {
    
    @Published var currencies: [CurrencyDto] = []
    @Published private (set) var isLoading = true
    
    let repo = CurrencyRepositoryImpl()
    
    init() {
        getPrices(fetchFromRemote: true)
    }
    
    func getPrices(fetchFromRemote: Bool = false) {
        repo.getCurrencies(fetchFromRemote: fetchFromRemote).collect(collector: Collector<Resource<NSArray>> { result in
            if result.status == ResourceStatus.success && result
                .data != nil {
                self.currencies = result.data as! [CurrencyDto]
                self.isLoading = false
            }
        }, completionHandler: { result, error in
            // Flow completed
            print("Flow completed")
        })
    }
    
    func refresh() {
        getPrices(fetchFromRemote: true)
    }
}
