import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapGlPoint;
import com.rs.util.map.amap.AmapTile;

public class Main {

    public static void main(String[] args) {
        LngLat ll = new LngLat(114.17, 22.31, LngLat.Map.amap);
        System.out.println(ll);
        System.out.println(ll.convertTo(LngLat.System.WGS84));
        System.out.println(ll.convertTo(LngLat.System.GCJ02));
        System.out.println(ll.convertTo(LngLat.System.BD09));
    }

}
