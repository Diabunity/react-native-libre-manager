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

|  EU Libre 1  |  | EU Libre 2 |               |
| ------------|--------------|--------------|--------------------|
| Activation  | ✅            | Activation   | ✅ |
| Scan glucose | ✅            | Scan glucose | NFC ✅ <br/>Bluetooth ❌ |
| Read FRAM   | ✅            | Read Fram    | ✅                  |
<br>

|  US Libre 1  |  |  US Libre 2  |               |
| ------------ | ----------- | ------------ |------------------|
| Activation  | ❌  |   Activation| ❌                |
| Scan glucose | ✅  |  Scan glucose  | NFC ❌<br/>Bluetooth ❌ |
| Read FRAM   |  ✅ |  Read Fram  | ❌                |

## License
MIT

