const fakeResponse = {
  currentGluecose: [155],
  history: [
    172, 123, 61, 82, 153, 76, 82, 128, 84, 166, 109, 124, 118, 92, 100, 127, 136, 80, 109, 102,
    155, 147, 59, 135, 155, 56, 68, 115, 142, 139, 158, 173,
  ],
  trendHistory: [100, 152, 129, 138, 107, 110, 100, 89, 143, 85, 159, 142, 125, 75, 139],
};

class MockLibreManager {
  activateSensor(cb) {
    cb({activated: true});
  }
  getSensorInfo(cb) {
    cb({sensorInfo: {age: 20}});
  }
  getGlucoseHistory(cb) {
    cb(fakeResponse);
  }
  setLang(lang){

  }
}

module.exports = new MockLibreManager();
