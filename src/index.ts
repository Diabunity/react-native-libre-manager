/**
 * This exposes the native CalendarModule module as a JS module. This has a
 * function 'createCalendarEvent' which takes the following parameters:

 * 1. String name: A string representing the name of the event
 * 2. String location: A string representing the location of the event
 */
import {NativeModules} from 'react-native';
import type {ILibreManger} from './types'

const {LibreManagerModule} = NativeModules;

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

