import com.rs.util.map.LngLat;
import com.rs.util.map.amap.AmapGlPoint;
import com.rs.util.map.amap.AmapTile;

public class Main {

    public static void main(String[] args) {
        LngLat ll = new LngLat(114.17, 22.31, LngLat.Map.amap);
        AmapGlPoint p = AmapGlPoint.fromLngLat(ll, 20);
        System.out.println(p);

        LngLat ll1 = new LngLat(121.582319, 31.199717, LngLat.Map.amap);
        LngLat ll2 = ll1.move(1000, 0);
        System.out.println(ll2);

        AmapTile amapTile = new AmapTile(24395, 16551, 15);
        System.out.println(amapTile.toLngLat());
    }

}
