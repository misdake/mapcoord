# mapcoord

mapcoord is a Java library to convert geographic coordinates and tiles.

### Features

- convert { WGS84 | GCJ02 | BD09 } coordinates to another system.
- convert between GCJ02 coordinates <> AMap tile coordinates.
- convert between BD09 coordinates <> BaiduMap tile coordinates.

### Usage

Create:

> ```java
> LngLat coordinate = new LngLat(114.17, 22.31, LngLat.Map.amap);
> coordinate.system // => GCJ02
> coordinate.lng // => 114.17
> coordinate.lat // => 22.31
> LngLat parsed = LngLat.parse("114.17, 22.31", LngLat.System.BD09); // => (114.17, 22.31) @ BD09
> ```

Convert:

> ```java
> coordinate.convertTo(LngLat.System.WGS84) // => (114.165022, 22.312739) @ WGS84
> coordinate.convertTo(LngLat.Map.bmap) // => (114.176459, 22.316298) @ BD09
> ```

Specify target coordinate system either by system { WGS84 | GCJ02 | BD09 } or by map product { amap(GCJ02) | bmap(BD09) | tmap(GCJ02) | gmap(GCJ02) | gps(WGS84) }.