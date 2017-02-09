package com.rs.util.map;


class ConvertRemote {

    static LngLat convert(LngLat src, LngLat.Map targetMap) {
        return convert(src, targetMap.system);
    }

    static LngLat convert(LngLat src, LngLat.System targetSystem) {
        if (src == null) return null;
        if (src.system == targetSystem) return src;
        switch (src.system) {
            case WGS84:
                switch (targetSystem) {
                    case GCJ02:
                        return wgs84_To_Gcj02(src.lng, src.lat);
                    case BD09:
                        return wgs84_To_Bd09(src.lng, src.lat);
                }
                break;

            case GCJ02:
                switch (targetSystem) {
                    case WGS84:
                        return gcj02_To_Wgs84(src.lng, src.lat);
                    case BD09:
                        return gcj02_To_Bd09(src.lng, src.lat);
                }
                break;

            case BD09:
                switch (targetSystem) {
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

    private static LngLat wgs84_To_Gcj02(double lng, double lat) {
        double[] d = HttpClient.run("transgps", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.GCJ02, d[0], d[1]);
    }

    private static LngLat gcj02_To_Wgs84(double lng, double lat) {
        double[] d = HttpClient.run("gcj2wgs", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.WGS84, d[0], d[1]);
    }

    //GCJ_02 <> BD_09

    private static LngLat gcj02_To_Bd09(double lng, double lat) {
        double[] d = HttpClient.run("gcj2bd", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.BD09, d[0], d[1]);
    }

    private static LngLat bd09_To_Gcj02(double lng, double lat) {
        double[] d = HttpClient.run("bd2gcj", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.GCJ02, d[0], d[1]);
    }

    //WGS_84 <> BD_09 via GCJ_02

    private static LngLat bd09_To_Wgs84(double lng, double lat) {
        double[] d = HttpClient.run("bd2wgs", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.WGS84, d[0], d[1]);
    }

    private static LngLat wgs84_To_Bd09(double lng, double lat) {
        double[] d = HttpClient.run("transgpsbd", lng, lat);
        if (d == null) return null;
        return new LngLat(LngLat.System.BD09, d[0], d[1]);
    }

}
