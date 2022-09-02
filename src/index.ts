/**
 * This exposes the native CalendarModule module as a JS module. This has a
 * function 'createCalendarEvent' which takes the following parameters:

 * 1. String name: A string representing the name of the event
 * 2. String location: A string representing the location of the event
 */
import { NativeModules } from "react-native";

const { LibreManagerModule } = NativeModules;

export interface ILibreManger {
  activateSensor: (callback: (resp: { activated: boolean }) => void) => void;
  getGlucoseHistory: (cb: (data: IGlucoseData) => void) => void;
  getGlucoseHistoryAndroid: (
    tagId?: string,
    memoryData?: number[] | null
  ) => Promise<any>;
  getSensorInfo: (
    cb: (data: { sensorLife: Pick<SensorInfoData, "age"> }) => void
  ) => void;
  getSensorInfoAndroid: (memoryData?: number[] | null) => Promise<any>;
  setLang: (lang: string) => void;
}

/**
 * Return response type in libre scan result
 *
 * @param current_glucose current user glucose last value item with one element. [number]
 * @param history 32 metrics for last 8 hour with interval 15 minutes (8*60min/15min=32). Sorted from NEW to OLD
 * @param trend_history 15 metrics for each last 15 minutes before. Sorted from NEW to OLD
 *
 */
export interface IGlucoseData {
  current_glucose: number[];
  history: number[];
  trend_history: number[];
}

export interface SensorInfoData {
  age: number;
  family: "Libre";
  initializations: number;
  lastReadingDate: number;
  maxLife: number;
  region: number;
  serial: string;
  state: string;
  type: string;
}

const LibreNative: ILibreManger = LibreManagerModule;

const LibreManagerTool: ILibreManger = {
  getGlucoseHistory: (cb) => {
    LibreNative.getGlucoseHistory(cb);
  },
  getGlucoseHistoryAndroid: async (tagId, memoryData) => {
    if (!tagId || !memoryData) return null;
    return await LibreNative.getGlucoseHistoryAndroid(tagId, memoryData);
  },
  activateSensor: (cb) => {
    LibreNative.activateSensor((resp: any) => {
      let respData: any[] = [];
      if (typeof resp === "string") {
        respData = resp.replace(/[{}]/g, "").split(":");
      }
      if ((respData.length && respData[1]) || resp?.activated) {
        cb({ activated: true });
      } else {
        cb({ activated: false });
      }
    });
  },
  getSensorInfo: (cb) => {
    LibreNative.getSensorInfo(async (resp: any) => {
      const mm = await resp;
      const data = JSON.parse(mm.sensorInfo);
      cb({ sensorLife: data.age });
    });
  },
  getSensorInfoAndroid: async (memoryData) => {
    if (!memoryData) return null;
    return await LibreNative.getSensorInfoAndroid(memoryData);
  },
  setLang: (lang: string) => {
    LibreNative.setLang(lang);
  },
};

export default LibreManagerTool;
