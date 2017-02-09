package com.rs.util.map;

public class LngLat {

    public enum Map {
        amap(System.GCJ02),
        bmap(System.BD09),
        gmap(System.GCJ02),
        tmap(System.GCJ02),
        gps(System.WGS84);

        public final System system;
        Map(System system) {
            this.system = system;
        }
    }

    public enum System {
        WGS84,
        GCJ02,
        BD09,
    }

    public final System system;
    public final double lng;
    public final double lat;

    public LngLat(Map map, double lng, double lat) {
        if (map == null) throw new NullPointerException();
        this.system = map.system;
        this.lng = lng;
        this.lat = lat;
    }
    public LngLat(System system, double lng, double lat) {
        if (system == null) throw new NullPointerException();
        this.system = system;
        this.lng = lng;
        this.lat = lat;
    }

    public LngLat(double lng, double lat, Map map) {
        if (map == null) throw new NullPointerException();
        this.system = map.system;
        this.lng = lng;
        this.lat = lat;
    }
    public LngLat(double lng, double lat, System system) {
        if (system == null) throw new NullPointerException();
        this.system = system;
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s) @ %s", lng, lat, system.name());
    }

    //parsing

    public static LngLat parse(String text, Map map) {
        return parse(text, map.system);
    }
    public static LngLat parse(String text, System system) {
        try {
            String[] split = text.split(",");
            if (split.length != 2) return null;
            double lng = Double.parseDouble(split[0]);
            double lat = Double.parseDouble(split[1]);
            if (Math.abs(lat) > 90 || lng < -180 || lng > 180) return null;
            LngLat lngLat = new LngLat(system, lng, lat);
            return lngLat;
        } catch (Exception ignored) {
            return null;
        }
    }
    public static LngLat parse(String lngString, String latString, Map map) {
        return parse(lngString, latString, map.system);
    }
    public static LngLat parse(String lngString, String latString, System system) {
        try {
            double lng = Double.parseDouble(lngString);
            double lat = Double.parseDouble(latString);
            if (Math.abs(lat) > 90 || lng < -180 || lng > 180) return null;
            LngLat lngLat = new LngLat(system, lng, lat);
            return lngLat;
        } catch (Exception ignored) {
            return null;
        }
    }

    //conversion

    public LngLat convertTo_fast(Map map) {
        return ConvertLocal.convert(this, map);
    }
    public LngLat convertTo_fast(System system) {
        return ConvertLocal.convert(this, system);
    }

    public LngLat convertTo(Map map) {
        return ConvertRemote.convert(this, map);
    }
    public LngLat convertTo(System system) {
        return ConvertRemote.convert(this, system);
    }
}
