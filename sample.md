
# Sample Data 
### from https://api.waqi.info
#### Query Information
 - /feed/:city/?token=:token 
 - /feed/geo::lat;:lng/?token=:token
 - /search/?keyword=:keyword&token=:token
 - for info https://aqicn.org/json-api/doc/

```json
{
  "status": "ok",
  "data": {
    "aqi": 176,
    "idx": 5776,
    "attributions": [
      {
        "url": "https://aqicn.org/gaia/",
        "name": "Gaia Air Quality Monitoring Network",
        "logo": "Gaia.png"
      },
      {
        "url": "http://aqmthai.com/",
        "name": "Division of Air Quality Data, Air Quality and Noise Management Bureau, Pollution Control Department.",
        "logo": "Thailand-PCD.png"
      },
      {
        "url": "https://waqi.info/",
        "name": "World Air Quality Index Project"
      }
    ],
    "city": {
      "geo": [19.9104798, 99.840576],
      "name": "Mueang Chiang Rai",
      "url": "https://aqicn.org/city/mueang-chiang-rai",
      "location": ""
    },
    "dominentpol": "pm25",
    "iaqi": {
      "co": { "v": 0.1 },
      "h": { "v": 32 },
      "no2": { "v": 0.6 },
      "o3": { "v": 12.7 },
      "p": { "v": 1006 },
      "pm10": { "v": 83 },
      "pm25": { "v": 176 },
      "t": { "v": 36 },
      "w": { "v": 2 }
    },
    "time": {
      "s": "2026-04-16 17:00:00",
      "tz": "+07:00",
      "v": 1776358800,
      "iso": "2026-04-16T17:00:00+07:00"
    },
    "forecast": {
      "daily": {
        "o3": [
          { "avg": 6, "day": "2025-04-05", "max": 10, "min": 3 }
        ],
        "pm10": [
          { "avg": 55, "day": "2026-04-14", "max": 58, "min": 52 },
          { "avg": 47, "day": "2026-04-15", "max": 52, "min": 46 },
          { "avg": 46, "day": "2026-04-16", "max": 46, "min": 46 },
          { "avg": 46, "day": "2026-04-17", "max": 46, "min": 46 },
          { "avg": 52, "day": "2026-04-18", "max": 57, "min": 46 },
          { "avg": 46, "day": "2026-04-19", "max": 46, "min": 46 },
          { "avg": 46, "day": "2026-04-20", "max": 46, "min": 46 },
          { "avg": 46, "day": "2026-04-21", "max": 46, "min": 44 },
          { "avg": 46, "day": "2026-04-22", "max": 46, "min": 45 }
        ],
        "pm25": [
          { "avg": 152, "day": "2026-04-14", "max": 157, "min": 138 },
          { "avg": 133, "day": "2026-04-15", "max": 151, "min": 105 },
          { "avg": 123, "day": "2026-04-16", "max": 138, "min": 105 },
          { "avg": 136, "day": "2026-04-17", "max": 138, "min": 116 },
          { "avg": 147, "day": "2026-04-18", "max": 156, "min": 138 },
          { "avg": 138, "day": "2026-04-19", "max": 138, "min": 138 },
          { "avg": 138, "day": "2026-04-20", "max": 138, "min": 138 },
          { "avg": 133, "day": "2026-04-21", "max": 138, "min": 105 },
          { "avg": 136, "day": "2026-04-22", "max": 138, "min": 116 }
        ],
        "uvi": [
          { "avg": 2, "day": "2026-04-14", "max": 10, "min": 0 },
          { "avg": 2, "day": "2026-04-15", "max": 10, "min": 0 },
          { "avg": 2, "day": "2026-04-16", "max": 9, "min": 0 },
          { "avg": 2, "day": "2026-04-17", "max": 9, "min": 0 },
          { "avg": 2, "day": "2026-04-18", "max": 8, "min": 0 },
          { "avg": 1, "day": "2026-04-19", "max": 9, "min": 0 },
          { "avg": 2, "day": "2026-04-20", "max": 9, "min": 0 }
        ]
      }
    }
  },
  "debug": {
    "sync": "2026-04-16T18:13:57+09:00"
  }
}
```

