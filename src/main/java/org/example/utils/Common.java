/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author A.Khalaev
 */
public class Common
{
    private static Logger log = LoggerFactory.getLogger(Common.class);
    public static Integer A_MINUTE = 60;
    public static Integer A_HOUR = 60*A_MINUTE;
    public static Integer A_DAY = 24*A_HOUR;
    public static Integer A_WEEK = 7*A_DAY;
    
    private static GsonBuilder builder = new GsonBuilder();
    private static GsonBuilder prettyBuilder = new GsonBuilder();
    static{    
        builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        prettyBuilder.setPrettyPrinting();
        prettyBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    private static Gson gsonUCC = builder.serializeNulls().create();
    private static Gson prettyGson = prettyBuilder.create();

    /**
     * 
     * @return Gson object, names in upper camel case 
     */
    public static Gson getGsonUCC() {
        return gsonUCC;
    }

    public static Gson getSimpleGson() {
        return prettyGson;
    }

    public static Gson getPrettyGson() {
        return prettyGson;
    }
    
    public static Integer currentTimeSec() {
            return (int)(System.currentTimeMillis()/1000);
    }
    
    public static <A, B> void addToMap(Map<A, Set<B>> map, A a, B b){
        Set<B> set = map.get(a);
        if(set == null){
            set = new HashSet<B>();
            map.put(a, set);
        }
        set.add(b);
    }

    public static <A, B> void addToMapList(Map<A, List<B>> map, A a, B b){
        List<B> list = map.get(a);
        if(list == null){
            list = new ArrayList<B>();
            map.put(a, list);
        }
        list.add(b);
    }

    public static Random random = new Random();
    public static int randonInRange(int t1, int t2) {
        if (t2<t1) {
            throw new IllegalArgumentException();
        }
        if (t1==t2) {
            return t1;
        }
        return t1 + random.nextInt(t2-t1);
    }
    
    public static String join(Iterable<?> list, String delim){
        return Joiner.on(delim).skipNulls().join(list);
    }

    public static List<String> split(String input, String pattern){
        return    Splitter.onPattern(pattern) //"[.|,]"
                                  .omitEmptyStrings()
                                  .splitToList(input);
    }
    public static <T> T castOrNull(Object wut){
        T out = null;

        if(wut != null){
            try {
                out = (T)wut;
            } catch (Throwable t) {
                log.error("Cant cast object", t);
            }
        }

        return out;
    }

    
    public static void configure() {
        try {
            String f = PropertyManager.propsPath;
            Properties p = new Properties();
            p.load(new FileInputStream(f));
            for(String name: p.stringPropertyNames()){
                System.setProperty(name, p.getProperty(name));
            }
            PropertyConfigurator.configure(p);

            
        } catch (Exception e) {
            //Выводим в out, т.к. возможно логер еще не инициализирован
            e.printStackTrace();
        }
    }

}
