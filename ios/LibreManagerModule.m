//
//  LibreManager.m
//  Aspect
//
//  Created by david on 12/15/21.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(LibreManagerModule, NSObject)

RCT_EXTERN_METHOD(activateSensor: (RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(getGlucoseHistory: (RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(getSensorInfo: (RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(setLang: (NSString *)string)

@end

