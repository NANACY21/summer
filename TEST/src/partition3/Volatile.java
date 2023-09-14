package partition3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Volatile {
    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("胡万里家", "哈尔滨南岗区");
        hashMap.put("李篪家", "香坊区");
        System.out.println(hashMap.get("胡万里家"));

        HashSet<String> aa = new HashSet<>();
        aa.add("ssas");
        List<Object> bb = new ArrayList<>(aa);
        System.out.println(bb);

    }
}
