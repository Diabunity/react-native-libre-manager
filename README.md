# React Native Libre Manager

React native library to work with abbot freestyle libre sensors by NFC

## Issues

not fully covered process with sensors form US/CA/AU regions

## Installation

```sh
npm install react-native-libre-manager
```
or
```sh
yard add react-native-libre-manager
```

## Usage

```js
import { LibreManagerTool } from "react-native-libre-manager";

const getGlucoseData = () => {
    LibreManagerTool.getGlucoseHistory((resp) => {
        console.log(resp);
    });
}
/*
 * available methods
    - getGlucoseHistory
    - activateSensor
    - getSensorInfo
*/
```

## State of completion

| EU Libre 1  | Status | EU Libre 2  | Status              |
| ------------|------|-------------|---------------------|
| Activation  | ✅    | Activation  | ✅ |
| Scan glucose | NFC ✅ | Scan glucose | NFC ✅ / Bluetooth ❌ |  
| Read FRAM   | ✅    | Read Fram   | ✅                   |

| US Libre 1  | Status  | US Libre 2  | Status  |
| ------------ | ------------ | ------------ | ------------ |
| Activation  | ❌  |   Activation|  ❌|
| Scan glucose | NFC ✅  |  Scan glucose  |NFC ❌ Bluetooth ❌ |  
| Read FRAM   |  ✅ |  Read Fram  | ❌|

## License
MIT

