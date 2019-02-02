package com.rs.util.map;


class Convert {

    static LngLat convert(LngLat src, LngLat.Map targetMap) {
        return convert(src, targetMap.system);
    }

    static LngLat convert(LngLat src, LngLat.System targetMap) {
        if (src == null) return null;
        if (src.system == targetMap) return src;
        switch (src.system) {
            case WGS84:
                switch (targetMap) {
                    case GCJ02:
                        return wgs84_To_Gcj02(src.lng, src.lat);
                    case BD09:
                        return wgs84_To_Bd09(src.lng, src.lat);
                }
                break;

            case GCJ02:
                switch (targetMap) {
                    case WGS84:
                        return gcj02_To_Wgs84(src.lng, src.lat);
                    case BD09:
                        return gcj02_To_Bd09(src.lng, src.lat);
                }
                break;

            case BD09:
                switch (targetMap) {
                    case WGS84:
                        return bd09_To_Wgs84(src.lng, src.lat);
                    case GCJ02:
                        return bd09_To_Gcj02(src.lng, src.lat);
                }
                break;
        }

        throw new UnsupportedOperationException();
    }

    //WGS_84 <> GCJ_02

    private static LngLat wgs84_To_Gcj02(double lon, double lat) {
        if (outOfChina(lon, lat)) {
            return new LngLat(LngLat.System.GCJ02, lon, lat);
        }
        LngLat r = transform(lon, lat);
        return r;
    }

    private static LngLat gcj02_To_Wgs84(double lng_gcj02, double lat_gcj02) {
        if (outOfChina(lng_gcj02, lat_gcj02)) {
            return new LngLat(LngLat.System.WGS84, lng_gcj02, lat_gcj02);
        }
        LngLat wgs = new LngLat(LngLat.System.WGS84, lng_gcj02, lat_gcj02);
        double wgslng = wgs.lng;
        double wgslat = wgs.lat;
        LngLat temp = transform(wgslng, wgslat);
        double dlng = temp.lng - wgslng;
        double dlat = temp.lat - wgslat;
        while (Math.abs(dlng) > 1e-6 || Math.abs(dlat) > 1e-6) {
            wgslng -= dlng;
            wgslat -= dlat;
            temp = transform(wgslng, wgslat);
            dlng = temp.lng - lng_gcj02;
            dlat = temp.lat - lat_gcj02;
        }
        return new LngLat(LngLat.System.WGS84, wgslng, wgslat);
    }

    //GCJ_02 <> BD_09

    private static LngLat gcj02_To_Bd09(double lng_gcj02, double lat_gcj02) {
        double x = lng_gcj02, y = lat_gcj02;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double lng_bd09 = z * Math.cos(theta) + 0.0065;
        double lat_bd09 = z * Math.sin(theta) + 0.006;
        return new LngLat(LngLat.System.BD09, lng_bd09, lat_bd09);
    }

    private static LngLat bd09_To_Gcj02(double lng_bd09, double lat_bd09) {
        double x = lng_bd09 - 0.0065, y = lat_bd09 - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new LngLat(LngLat.System.GCJ02, gg_lon, gg_lat);
    }

    //WGS_84 <> BD_09 via GCJ_02

    private static LngLat bd09_To_Wgs84(double lng_bd09, double lat_bd09) {
        LngLat gcj02 = bd09_To_Gcj02(lng_bd09, lat_bd09);
        LngLat map84 = gcj02_To_Wgs84(gcj02.lng, gcj02.lat);
        return map84;
    }

    private static LngLat wgs84_To_Bd09(double lng_wgs84, double lat_wgs84) {
        LngLat gcj02 = wgs84_To_Gcj02(lng_wgs84, lat_wgs84);
        if (gcj02 == null) return null;
        LngLat bd09 = gcj02_To_Bd09(gcj02.lng, gcj02.lat);
        return bd09;
    }

    //util

    private final static double pi = 3.1415926535897932384626;
    private final static double a  = 6378245.0;
    private final static double ee = 0.00669342162296594323;

    private static boolean outOfChina(double lon, double lat) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    private static LngLat transform(double lon, double lat) {
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new LngLat(LngLat.System.GCJ02, mgLon, mgLat);
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

}
