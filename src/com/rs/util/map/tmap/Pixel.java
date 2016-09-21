package com.rs.util.map.tmap;

import com.rs.util.map.LngLat;

public class Pixel {
    public final double x;
    public final double y;

    public Pixel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public LngLat toLngLat() {
        double lng = x / 111319.49077777778;
        double lat = Math.atan(Math.exp(y / 111319.49077777778 * 0.017453292519943295)) * 114.59155902616465 - 90;
        return new LngLat(LngLat.Map.tmap, lng, lat);
    }

    public static Pixel fromLngLat(LngLat lngLat) {
        LngLat ll = lngLat.convertTo(LngLat.Map.tmap);
        double y = Math.log(Math.tan((90 + ll.lat) * 0.008726646259971648)) / 0.017453292519943295;
        double x = ll.lng * 111319.49077777778;
        return new Pixel(x, y);
    }
}
