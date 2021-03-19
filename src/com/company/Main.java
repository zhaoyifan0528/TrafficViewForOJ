package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    static String []time = new String[200007];
    static String []ip = new String[200007];
    static String []url = new String[200007];
    static Date []tim = new Date[200007];
    static Map<String,Integer> mpIP = new HashMap<>();
    static Map<String,Integer> mpURL = new HashMap<>();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Map的value值降序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }

    // Map的value值升序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortAscend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }
    static void tip(){
        System.out.println("请输入相关的操作代码");
        System.out.println("1.修改查询开始时间");
        System.out.println("2.修改查询结束时间");
        System.out.println("3.查询概括信息");
        System.out.println("4.查询访问ip详细信息");
        System.out.println("5.查询url访问详细信息");
        System.out.println("6.退出");
    }
    static int number = 0;
    static int now = 0;
    static Date begin = new Date(),end = new Date();
    static void sortit(){
        number = 0;
        for (int i = 1 ;i <= now; i++) {
            if(tim[i].after(begin) && tim[i].before(end))
            {
                number ++;
                if(!mpIP.containsKey(ip[i])) mpIP.put(ip[i],1);
                else {
                    int re = mpIP.get(ip[i]);
                    mpIP.remove(re);
                    mpIP.put(ip[i],re+1);
                }
                if(!mpURL.containsKey(url[i])) mpURL.put(url[i],1);
                else {
                    int re = mpURL.get(url[i]);
                    mpURL.remove(re);
                    mpURL.put(url[i],re+1);
                }
            }
        }
        mpIP = sortDescend(mpIP);
        mpURL = sortDescend(mpURL);
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("src/1.txt"));
        //BufferedReader br = new BufferedReader(new FileReader("D:\\delete\\1.txt"));
        //BufferedWriter  writer = new BufferedWriter(new FileWriter("src/2.txt"));
        String str;now = 0;
        while(true)
        {
            str = br.readLine();
            if(str == null) break;
            String []strs1 = str.split("  INFO");
            String []strs2 = strs1[1].split("Filter From: ");
            String []strs3 = strs2[1].split("Url: ");
            String []strs4 = strs3[1].split("Params");
            //writer.write(strs1[0]+","+strs3[0].trim()+","+strs4[0].trim()+"\n");
            time[++now] = new String();time[now] = strs1[0];
            tim[now] = new Date();tim[now] = simpleDateFormat.parse(time[now]);
            ip[now] = new String();ip[now] = strs3[0].trim();
            url[now] = new String();url[now] = strs4[0].trim();
            //writer.flush();
        }
        while(true)
        {

            System.out.println("--------------------------------------");
            tip();
            int opt = scanner.nextInt();scanner.nextLine();
            System.out.println("--------------------------------------");
            if(opt == 1)
            {
                System.out.println("请输入查询日期开始日期(例2021-03-11 00:03:10)");
                begin = simpleDateFormat.parse(scanner.nextLine());
                if(end != null) sortit();
                continue;
            }
            if(opt == 2)
            {
                System.out.println("请输入查询日期结束日期(例2021-03-18 00:03:10)");
                end =  simpleDateFormat.parse(scanner.nextLine());
                if(begin != null) sortit();
                continue;
            }
            if(opt == 3)
            {
                System.out.println("该时间区间内共有"+number+"次流量访问");
                System.out.println("其中有"+mpIP.size()+"个不同ip访问");
                System.out.println("访问了"+mpURL.size()+"个不同的url");
            }
            int rei = 0;
            if(opt == 4)
            {
                System.out.println("您想查询前多少名ip访问数据呢？");
                int x = scanner.nextInt();
                System.out.println("ip访问前"+Math.min(x,mpIP.size())+"排名如下");
                for (Map.Entry ip:mpIP.entrySet()) {
                    System.out.println("IP"+(++rei)+": "+ip.getKey()+" 访问了"+ip.getValue()+"次");
                    if(rei >= x) break;
                }
            }
            if(opt == 5)
            {
                System.out.println("您想查询前多少名url访问数据呢？");
                int x = scanner.nextInt();
                System.out.println("ip访问前"+Math.min(x,mpURL.size())+"排名如下");
                for (Map.Entry url:mpURL.entrySet()) {
                    System.out.println("url"+(++rei)+": "+url.getKey()+" 被访问了"+url.getValue()+"次");
                    if(rei >= x) break;
                }
            }
            if(opt == 6) break;
        }

    }
}
