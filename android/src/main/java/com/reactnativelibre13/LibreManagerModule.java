package com.reactnativelibre13;

import com.facebook.react.bridge.*;
import kotlin.Pair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.Objects;

import static com.reactnativelibre13.JSONParser.convertJsonToMap;

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

  private WritableArray getGlucoseReadingsAsWritableArray(List<GlucoseReading> glucoseReadings) throws JSONException {
    WritableArray array = new WritableNativeArray();
    for (GlucoseReading glucoseReading : glucoseReadings) {
      WritableMap glucoseMap = convertJsonToMap(new
        JSONObject(Objects.requireNonNull(glucoseReading.toJsonString())));
      array.pushMap(glucoseMap);
    }
    return array;
  }

    @ReactMethod
    public void getGlucoseHistoryAndroid(String tagId, ReadableArray memoryData, Promise promise) {
      Pair<List<GlucoseReading>, List<GlucoseReading>> data = null;
      WritableMap response = new WritableNativeMap();
      try{
        Long now = Time.Companion.now();
        Integer timestamp = RawParser.Companion.timestamp(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)));
        //Dont accept values if timestamp is less than 30 minutes (Values are off in the beginning)
        if(timestamp < 30){
          throw new Exception("Wait for" + (30 - timestamp) + "minutes until reading");
        }else {
          data = NFCReader.Companion.append(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)), now, tagId);
        }

        List<GlucoseReading> recent = data.getFirst();
        List<GlucoseReading> history = data.getSecond();
        response.putArray("trend_history", getGlucoseReadingsAsWritableArray(recent));
        response.putArray("history", getGlucoseReadingsAsWritableArray(history));
        response.putInt("current_glucose", recent.get(0).getValue());

        WritableMap freestyleDevice = new WritableNativeMap();
        freestyleDevice.putBoolean("expired", Time.Companion.timeLeft(timestamp).getSecond());
        freestyleDevice.putDouble("expires_on", Time.Companion.timeLeft(timestamp).getFirst());
        response.putMap("freestyle_device", freestyleDevice);

        promise.resolve(response);

      }catch (Exception e){
        promise.reject("E_NFC_ERROR", e);
      }
    }

  @ReactMethod
  public void getSensorInfo(ReadableArray memoryData, Promise promise) {
    Pair<List<GlucoseReading>, List<GlucoseReading>> data = null;
    WritableMap response = new WritableNativeMap();
    try{
      Long now = Time.Companion.now();
      Integer timestamp = RawParser.Companion.timestamp(Objects.requireNonNull(RawParser.Companion.readableArrayToByteStringArray(memoryData)));

      response.putDouble("sensorLife", Time.Companion.timeLeft(timestamp).getFirst());

      promise.resolve(response);

    }catch (Exception e){
      promise.reject("E_NFC_ERROR", e);
    }
  }
}
