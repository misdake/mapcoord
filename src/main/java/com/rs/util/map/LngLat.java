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

    public System system;
    public double lng;
    public double lat;

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
        return String.format("(%.06f, %.06f) @ %s", lng, lat, system.name());
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
        return Convert.convert(this, map);
    }
    public LngLat convertTo(System system) {
        return Convert.convert(this, system);
    }

    //util

    public LngLat move(double eastMeter, double southMeter) {
        double lng = this.lng + eastMeter / (6378137 * Math.cos(Math.toRadians(lat)) * 2 * Math.PI / 360);
        double lat = this.lat - southMeter / (6378137 * 2 * Math.PI / 360);
        return new LngLat(this.system, lng, lat);
    }

    public static double eastDiffMeter(LngLat reference, LngLat current) {
        return (current.lng - reference.lng) * (6378137 * Math.cos(Math.toRadians(reference.lat)) * 2 * Math.PI / 360);
    }
    public static double southDiffMeter(LngLat reference, LngLat current) {
        return -(current.lat - reference.lat) * (6378137 * 2 * Math.PI / 360);
    }

    public static double distanceMeter(LngLat ll1, LngLat ll2) {
        if (ll1.system != ll2.system) {
            ll2 = ll2.convertTo(ll1.system);
        }
        double radLat1 = Math.toRadians(ll1.lat);
        double radLat2 = Math.toRadians(ll2.lat);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(ll1.lng) - Math.toRadians(ll2.lng);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137;
        return s;
    }
    public double distanceMeter(LngLat other) {
        return distanceMeter(this, other);
    }
}
