
export interface ILibreManger{
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

declare module LibreManger{
  export default LibreManger = {} as  ILibreManger
}
