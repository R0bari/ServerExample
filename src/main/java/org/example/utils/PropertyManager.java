/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author A.Khalaev
 */
public class PropertyManager
{
    private static Logger log = LoggerFactory.getLogger(PropertyManager.class.getSimpleName());
    private static Properties properties = new Properties();

    public static final String propsPath = "./main.props";
    public static Map<String, String> getPropertiesByPrefix(String prefix){
        Map<String, String> map = new HashMap<>();
        
        for(String name: properties.stringPropertyNames()){
            if(name.startsWith(prefix)){
                map.put(name.substring(prefix.length()), properties.getProperty(name));
            }
        }
        
        return map;
    }

    public static void load() throws Exception
    {
        properties.load(new FileInputStream(propsPath));
        //PropertyConfigurator.configure(properties);
    }
    public static Integer getPropertyAsInteger(String name, Integer defaultVal) throws PropertyNotFindException {
        return Integer.decode(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    public static Boolean getPropertyAsBoolean(String name, Boolean defaultVal) throws PropertyNotFindException {
        return toBoolean(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    private static boolean toBoolean(String val) {
	return ((val != null) && val.equalsIgnoreCase("true"));
    }

    public static Long getPropertyAsLong(String name, Long defaultVal) throws PropertyNotFindException {
        return Long.decode(getPropertyAsString(name, String.valueOf(defaultVal)));
    }

    public static String getPropertyAsString(String name, String defaultVal) throws PropertyNotFindException {
        String value = properties.getProperty(name);
        if (value == null) {
            log.error("Can't find property {}, use default value {}", name, defaultVal);
            return defaultVal;
        }
        return value;
    }

    public static boolean hasPropertyt(String name) {
        return properties.containsKey(name);
    }

    public final class PropertyNotFindException extends RuntimeException
    {
        public PropertyNotFindException(String message) {
            super(message);
        }
    }
}
