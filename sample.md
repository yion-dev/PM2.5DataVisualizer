
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
  "data": [
    {
      "uid": 9587,
      "aqi": "171",
      "time": "2026-04-16 15:00:00",
      "station": {
        "name": "Chiangrai - Gaia Station 07, Thailand",
        "geo": [19.858547, 99.90148]
      }
    },
    {
      "uid": 9450,
      "aqi": "176",
      "time": "2026-04-16 15:00:00",
      "station": {
        "name": "Chiangrai - Gaia Station 01, Thailand",
        "geo": [19.687157, 100.19024]
      }
    },
    {
      "uid": 1832,
      "aqi": "225",
      "time": "2026-04-16 16:00:00",
      "station": {
        "name": "Maesai Health Office, Chiangrai, Thailand",
        "geo": [20.439944, 99.913667]
      }
    },
    {
      "uid": 1828,
      "aqi": "187",
      "time": "2026-04-16 16:00:00",
      "station": {
        "name": "Natural Resources and Environment Office, Chiangrai, Thailand",
        "geo": [19.909217, 99.823467]
      }
    },
    {
      "uid": 5776,
      "aqi": "187",
      "time": "2026-04-16 16:00:00",
      "station": {
        "name": "Mueang Chiang Rai",
        "geo": [19.91048, 99.840576]
      }
    }
  ]
}
```
