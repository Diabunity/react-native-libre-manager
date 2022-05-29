package com.reactnativelibre13;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.List;
import java.util.Objects;

import kotlin.Pair;

public class LibreManagerModule extends ReactContextBaseJavaModule {
    public static final String NAME = "LibreManager";

    public LibreManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod(isBlockingSynchronousMethod = true)
    public int multiply(int a, int b) {
        return a * b;
    }

    @ReactMethod
    public void getGlucoseHistoryAndroid(ReadableArray uid, ReadableArray memoryData, Promise promise) {
      Pair<List<GlucoseReading>, List<GlucoseReading>> data = null;
      WritableMap response = Arguments.createMap();
      try{
        Long tagId = RawParser.Companion.bin2long(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(uid)));
        Long now = Time.Companion.now();
        Integer timestamp = RawParser.Companion.timestamp(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)));
        //Dont accept values if timestamp is less than 30 minutes (Values are off in the beginning)
        if(timestamp < 30){
          throw new Exception("Wait for" + (30 - timestamp) + "minutes until reading");
        }else {
          data = NFCReader.Companion.append(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)), now, tagId);
        }
        ReadableMap first = (ReadableMap) data.getFirst();
        ReadableMap second = (ReadableMap) data.getSecond();

        response.putMap("recent", first);
        response.putMap("history", second);

        promise.resolve(response);

      }catch (Exception e){
        promise.reject("E_NFC_ERROR", e);
      }
    }
}
