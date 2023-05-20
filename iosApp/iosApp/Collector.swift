//
//  Collector.swift
//  iosApp
//
//  Created by Shahryar on 5/2/22.
//

import Foundation
import shared

class Collector<T>: Kotlinx_coroutines_coreFlowCollector {
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        callback(value as! T)
        completionHandler(KotlinUnit() as? Error)
    }
    
    
    let callback: (T) -> Void
    
    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }
    
}
