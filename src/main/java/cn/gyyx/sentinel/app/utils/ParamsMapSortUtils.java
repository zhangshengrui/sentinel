package cn.gyyx.sentinel.app.utils;

import java.util.*;

public class ParamsMapSortUtils {
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new Comparator<String>(){
                    public int compare(String str1, String str2) {

                        return str1.compareTo(str2);
                    }
        });

        sortMap.putAll(map);

        return sortMap;
    }


    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("1111","2222");
        map.put("1111","2222");
        map.put("aa","2222");
        map.put("a","2222");
        map.put("b","2222");
        map.put("你好","2222");
        map.put("","2222");
        map.put("","333");
        map.put("2222","2222");
        map.put("我是一个很长很长的中文","2222");
        map.put("排序排序","2222");
        map.put("啊","2222");
        map.put("吧","2222");
        map.put("吃","2222");
        map.put("在在","2222");
        Map<String,String> map2 = ParamsMapSortUtils.sortMapByKey(map);
        Set<String> keySet = map2.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map2.get(key));
        }
    }

}
