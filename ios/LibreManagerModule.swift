//
//  LibreManager.swift
//  Aspect
//
//  Created by david on 12/15/21.
//

import Foundation

@objc(LibreManagerModule)
class LibreManagerModule: NSObject {
  @objc static func requiresMainQueueSetup() -> Bool {
      return false
  }

  @objc
  func activateSensor(_ callback: @escaping RCTResponseSenderBlock) {
    #if targetEnvironment(simulator)
      callback([["activated" : true]])
    #else
      RNLibreToolsiOS13.shared.activate { result in
         switch result {
         case .success(_):
           callback([["activated" : true]])
         case .failure(_):
           callback([["activated" : false]])
         }
       }
    #endif
  }

  @objc
  func getGlucoseHistory(_ callback: @escaping  RCTResponseSenderBlock) {
    #if targetEnvironment(simulator)
      callback([["glucoseHistory": [], "sensorInfo": [{}]]])
    #else
      let startSession = null
      let sensorInfo = null
      RNLibreToolsiOS13.shared.startSession { result in
        switch result {
        case .success(let response):
          startSession = response
        case .failure(let error):
          print(error)
          callback([["glucoseHistory" : [], "sensorInfo": [{}]]])
        }
      }
     RNLibreToolsiOS13.shared.getSensorInfo { result in
        switch result {
        case .success(let response):
          sensorInfo = response
          callback(["sensorInfo" : response, "glucoseHistory": startSession])
        case .failure(let error):
          print(error)
          callback([["glucoseHistory" : [], "sensorInfo": [{}]]])
        }
      }
    #endif
  }

  @objc
  func getSensorInfo(_ callback: @escaping  RCTResponseSenderBlock) {
    #if targetEnvironment(simulator)
    callback(["sensorInfo", [{}]])
    #else
      RNLibreToolsiOS13.shared.getSensorInfo { result in
        switch result {
        case .success(let response):
          callback(response)
        case .failure(let error):
          print(error)
          callback(["sensorInfo", [{}]])
        }
      }
    #endif
  }


 @objc
 func setLang(_ lang: String){
     RNLibreToolsiOS13.shared.setLang(lang: lang)
 }

}
