//
//  PricesViewModel.swift
//  iosApp
//
//  Created by shahryar khosravi on 3/4/22.
//

import Foundation
import shared

class PricesViewModel: ObservableObject {
    
    @Published var currencies: [Currency] = []
    
    let repo = RepositoryImpl(
        remoteDataSource: RemoteDataSourceImpl(priceApi: PriceApi())
    )
    
    init() {
       getPrices()
    }
    
    func getPrices() {
        repo.refresh { result, error in
            if let result = result {
                self.currencies = result.data as! [Currency]
            } else if let error = error {
                print(error.localizedDescription)
            }
        }
    }
}
