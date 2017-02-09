package com.rs.util.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class HttpClient {

    static double[] run(String method, double lng, double lat) {
        int count = 0;
        double[] r;
        while ((r = send(method, lng, lat)) == null) {
            sleep(100);
            count++;
            if (count > 3) return null;
        }
        return r;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    private static final long MIN_INTERVAL = 50;
    private static       long last         = -1;
    private static synchronized void checkTime() {
        for (; ; ) {
            long l = System.currentTimeMillis();
            if (l - last >= MIN_INTERVAL) {
                last = l;
                return;
            }
            sleep(1);
        }
    }

    private static final String TEMPLATE = "http://api.zdoz.net/%s.aspx?lng=%f&lat=%f";
    private static double[] send(String method, double lng, double lat) {
        checkTime();
        String req = String.format(TEMPLATE, method, lng, lat);
        String res = send(req);
        if (res == null) return null;
        int f1 = res.indexOf("{\"Lng\":");
        int f2 = res.indexOf(",\"Lat\":");
        int f3 = res.indexOf("}");
        if (f1 >= 0 && f2 >= 0 && f3 >= 0) {
            double[] ll = new double[2];
            ll[0] = Double.parseDouble(res.substring(f1 + 7, f2));
            ll[1] = Double.parseDouble(res.substring(f2 + 7, f3));
            return ll;
        } else {
            return null;
        }
    }

    private static String send(String url) {
        try {
            InputStream response = new URL(url).openStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                return responseBody;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
