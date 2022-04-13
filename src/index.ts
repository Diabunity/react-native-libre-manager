/**
 * This exposes the native CalendarModule module as a JS module. This has a
 * function 'createCalendarEvent' which takes the following parameters:

 * 1. String name: A string representing the name of the event
 * 2. String location: A string representing the location of the event
 */
import {NativeModules} from 'react-native';

const {LibreManagerModule} = NativeModules;

export interface ILibreManger {
  activateSensor: (callback: (resp: {activated: boolean}) => void) => void;
  getGlucoseHistory: (cb: (data: IGlucoseData) => void) => void;
  getSensorInfo: (cb: (data: {sensorInfo: SensorInfoData}) => void) => void;
}

/**
 * Return response type in libre scan result
 *
 * @param currentGluecose current user glucose last value item with one element. [number]
 * @param history 32 metrics for last 8 hour with interval 15 minutes (8*60min/15min=32). Sorted from NEW to OLD
 * @param trendHistory 15 metrics for each last 15 minutes before. Sorted from NEW to OLD
 *
 */
export interface IGlucoseData {
  currentGluecose: [number];
  history: number[];
  trendHistory: number[];
}

export interface SensorInfoData {
  age: number;
  family: 'Libre';
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
  getGlucoseHistory: LibreNative.getGlucoseHistory,
  activateSensor: (cb) => {
    LibreNative.activateSensor((resp: any) => {
      let respData: any[] = [];
      if (typeof resp === 'string') {
        respData = resp.replace(/[{}]/g, '').split(':');
      }
      if ((respData.length && respData[1]) || resp?.activated) {
        cb({activated: true});
      } else {
        cb({activated: false});
      }
    });
  },
  getSensorInfo: (cb) => {
    LibreNative.getSensorInfo(async (resp: any) => {
      const mm = await resp;
      const data = JSON.parse(mm.sensorInfo);
      cb({sensorInfo: data});
    });
  },
};

export default LibreManagerTool;

