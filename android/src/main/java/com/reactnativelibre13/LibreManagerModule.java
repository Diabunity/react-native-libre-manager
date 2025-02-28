package com.reactnativelibre13;

import static com.reactnativelibre13.JSONParser.convertJsonToMap;

import com.facebook.react.bridge.*;
import kotlin.Pair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.Objects;
import androidx.annotation.NonNull;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.IllegalViewOperationException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.reactnativelibre13.JSONParser.convertJsonToMap;

public class LibreManagerModule extends ReactContextBaseJavaModule {
  public static final String NAME = "LibreManagerModule";

  public LibreManagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private WritableArray getGlucoseReadingsAsWritableArray(List<GlucoseReading> glucoseReadings) throws JSONException {
    WritableArray array = new WritableNativeArray();
    for (GlucoseReading glucoseReading : glucoseReadings) {
      WritableMap glucoseMap = convertJsonToMap(new JSONObject(Objects.requireNonNull(glucoseReading.toJsonString())));
      array.pushMap(glucoseMap);
    }
    return array;
  }

  @ReactMethod
  public void getGlucoseHistoryAndroid(String tagId, ReadableArray memoryData, Promise promise) {
    Pair<List<GlucoseReading>, List<GlucoseReading>> data = null;
    WritableMap response = new WritableNativeMap();
    try {
      Long now = Time.Companion.now();
      Integer timestamp = RawParser.Companion
          .timestamp(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)));
      // Dont accept values if timestamp is less than 30 minutes (Values are off in
      // the beginning)
      if (timestamp < 30) {
        throw new Exception("Espera por" + (30 - timestamp) + "minutos para empezar a medirte por primera vez.");
      } else {
        data = NFCReader.Companion
            .append(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)), now, tagId);
      }

      List<GlucoseReading> recent = data.getFirst();
      List<GlucoseReading> history = data.getSecond();
      response.putArray("trend_history", getGlucoseReadingsAsWritableArray(recent));
      response.putArray("history", getGlucoseReadingsAsWritableArray(history));
      response.putInt("current_glucose", recent.get(0).getValue());

      promise.resolve(response);

    } catch (Exception e) {
      promise.reject("E_NFC_ERROR", e);
    }
  }

  @ReactMethod
  public void getSensorInfoAndroid(ReadableArray memoryData, Promise promise) {
    Pair<List<GlucoseReading>, List<GlucoseReading>> data = null;
    WritableMap response = new WritableNativeMap();
    try {
      Long now = Time.Companion.now();
      Integer timestamp = RawParser.Companion
          .timestamp(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)));

      response.putDouble("sensorLife", Time.Companion.timeLeft(timestamp));

      promise.resolve(response);

    } catch (Exception e) {
      promise.reject("E_NFC_ERROR", e);
    }
  }
}
