package com.rs.util.map;

import com.rs.util.map.amap.Tile;

public class LngLat {

    public enum Map {
        amap(System.GCJ_02),
        bmap(System.BD_09),
        gmap(System.GCJ_02),
        tmap(System.GCJ_02),
        gps(System.WGS_84);

        public final System system;
        Map(System system) {
            this.system = system;
        }
    }

    public enum System {
        WGS_84,
        GCJ_02,
        BD_09,
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

    @Override
    public String toString() {
        return String.format("LngLat_%s(%s, %s)", system.name(), lng, lat);
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

    public LngLat convertTo(Map map) {
        return LngLatConversion.convert(this, map);
    }
    public LngLat convertTo(System system) {
        return LngLatConversion.convert(this, system);
    }

    public Tile toAmapTile(int zoom) {
        return Tile.fromLngLat(this, zoom);
    }

    public com.rs.util.map.bmap.Pixel toBmapPixel() {
        return com.rs.util.map.bmap.Pixel.fromLngLat(this);
    }

    public com.rs.util.map.tmap.Pixel toTmapPixel() {
        return com.rs.util.map.tmap.Pixel.fromLngLat(this);
    }
}
