package com.rs.util.map;


class ConvertLocal {

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
                        return wgs84_To_Gcj02(src.lat, src.lng);
                    case BD09:
                        return wgs84_To_Bd09(src.lat, src.lng);
                }
                break;

            case GCJ02:
                switch (targetMap) {
                    case WGS84:
                        return gcj02_To_Wgs84(src.lat, src.lng);
                    case BD09:
                        return gcj02_To_Bd09(src.lat, src.lng);
                }
                break;

            case BD09:
                switch (targetMap) {
                    case WGS84:
                        return bd09_To_Wgs84(src.lat, src.lng);
                    case GCJ02:
                        return bd09_To_Gcj02(src.lat, src.lng);
                }
                break;
        }

        throw new UnsupportedOperationException();
    }

    //WGS_84 <> GCJ_02

    private static LngLat wgs84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return null;
        }
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

    private static LngLat gcj02_To_Wgs84(double lat, double lon) {
        LngLat wgs = transform(lat, lon);
        double longitude = lon * 2 - wgs.lng;
        double latitude = lat * 2 - wgs.lat;
        return new LngLat(LngLat.System.WGS84, longitude, latitude);
    }

    //GCJ_02 <> BD_09

    private static LngLat gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new LngLat(LngLat.System.BD09, bd_lon, bd_lat);
    }

    private static LngLat bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new LngLat(LngLat.System.GCJ02, gg_lon, gg_lat);
    }

    //WGS_84 <> BD_09 via GCJ_02

    private static LngLat bd09_To_Wgs84(double bd_lat, double bd_lon) {
        LngLat gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
        LngLat map84 = gcj02_To_Wgs84(gcj02.lat, gcj02.lng);
        return map84;
    }

    private static LngLat wgs84_To_Bd09(double gg_lat, double gg_lon) {
        LngLat gcj02 = wgs84_To_Gcj02(gg_lat, gg_lon);
        if (gcj02 == null) return null;
        LngLat bd09 = gcj02_To_Bd09(gcj02.lat, gcj02.lng);
        return bd09;
    }

    //util

    private static double pi = 3.1415926535897932384626;
    private static double a  = 6378245.0;
    private static double ee = 0.00669342162296594323;

    private static boolean outOfChina(double lat, double lon) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    private static LngLat transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new LngLat(LngLat.System.WGS84, lon, lat);
        }
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
        return new LngLat(LngLat.System.WGS84, mgLon, mgLat);
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
