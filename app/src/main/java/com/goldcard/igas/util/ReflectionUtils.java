package com.goldcard.igas.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 反射工具类
 * @author yanshengli
 * @since 2015-3-31
 */
public class ReflectionUtils {

    public static final SimpleDateFormat SDF = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

    public static Method getFieldGetMethod(Class<?> clazz,Field f){
        String fn = f.getName ();
        Method m = null;
        if (f.getType () == boolean.class) {
            m = getBooleanFieldGetMethod (clazz, fn);
        }
        if (m == null) {
            m = getFieldGetMethod (clazz, fn);
        }
        return m;
    }

    public static Method getBooleanFieldGetMethod(Class<?> clazz,String fieldName){
        String mn = "is" + fieldName.substring (0, 1).toUpperCase () + fieldName.substring (1);
        if (isISStart (fieldName)) {
            mn = fieldName;
        }
        try {
            return clazz.getDeclaredMethod (mn);
        } catch (NoSuchMethodException e) {
            e.printStackTrace ();
            return null;
        }
    }

    private static boolean isISStart(String fieldName){
        if (fieldName == null || fieldName.trim ().length () == 0) return false;
        // is开头，并且is之后第一个字母是大写 比如 isAdmin
        return fieldName.startsWith ("is") && !Character.isLowerCase (fieldName.charAt (2));
    }

    public static Method getBooleanFieldSetMethod(Class<?> clazz,Field f){
        String fn = f.getName ();
        String mn = "set" + fn.substring (0, 1).toUpperCase () + fn.substring (1);
        if (isISStart (f.getName ())) {
            mn = "set" + fn.substring (2, 3).toUpperCase () + fn.substring (3);
        }
        try {
            return clazz.getDeclaredMethod (mn, f.getType ());
        } catch (NoSuchMethodException e) {
            e.printStackTrace ();
            return null;
        }
    }

    public static Method getFieldGetMethod(Class<?> clazz,String fieldName){
        String mn = "get" + fieldName.substring (0, 1).toUpperCase () + fieldName.substring (1);
        try {
            return clazz.getDeclaredMethod (mn);
        } catch (NoSuchMethodException e) {
            e.printStackTrace ();
            return null;
        }
    }

    public static Method getFieldSetMethod(Class<?> clazz,Field f){
        String fn = f.getName ();
        String mn = "set" + fn.substring (0, 1).toUpperCase () + fn.substring (1);
        try {
            return clazz.getDeclaredMethod (mn, f.getType ());
        } catch (NoSuchMethodException e) {
            if (f.getType () == boolean.class) { return getBooleanFieldSetMethod (clazz, f); }
        }
        return null;
    }

    public static Method getFieldSetMethod(Class<?> clazz,String fieldName){
        try {
            return getFieldSetMethod (clazz, clazz.getDeclaredField (fieldName));
        } catch (SecurityException e) {
            e.printStackTrace ();
        } catch (NoSuchFieldException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 获取某个字段的值
     * @param entity
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object entity,Field field){
        Method method = getFieldGetMethod (entity.getClass (), field);
        return invoke (entity, method);
    }

    /**
     * 获取某个字段的值
     * @param entity
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object entity,String fieldName){
        Method method = getFieldGetMethod (entity.getClass (), fieldName);
        return invoke (entity, method);
    }

    /**
     * 设置某个字段的值
     * @param entity
     * @param fieldName
     * @return
     */
    @SuppressWarnings("null")
    public static void setFieldValue(Object entity,Field field,Object value){
        try {
            Method set = getFieldSetMethod (entity.getClass (), field);
            if (set != null) {
                set.setAccessible (true);
                Class<?> type = field.getType ();
                if (type == String.class) {
                    set.invoke (entity, value.toString ());
                } else if (type == int.class || type == Integer.class) {
                    set.invoke (entity, value == null ? (Integer) null : Integer.parseInt (value.toString ()));
                } else if (type == float.class || type == Float.class) {
                    set.invoke (entity, value == null ? (Float) null : Float.parseFloat (value.toString ()));
                } else if (type == long.class || type == Long.class) {
                    set.invoke (entity, value == null ? (Long) null : Long.parseLong (value.toString ()));
                } else if (type == Date.class) {
                    set.invoke (entity, value == null ? (Date) null : stringToDateTime (value.toString ()));
                } else {
                    set.invoke (entity, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static Date stringToDateTime(String strDate){
        if (strDate != null) {
            try {
                return SDF.parse (strDate);
            } catch (ParseException e) {
                e.printStackTrace ();
            }
        }
        return null;
    }

    /**
     * 获取某个字段的值
     * @param entity
     * @param fieldName
     * @return
     */
    public static Field getFieldByName(Class<?> clazz,String fieldName){
        Field field = null;
        if (fieldName != null) {
            try {
                field = clazz.getDeclaredField (fieldName);
            } catch (SecurityException e) {
                e.printStackTrace ();
            } catch (NoSuchFieldException e) {
                e.printStackTrace ();
            }
        }
        return field;
    }

    /**
     * 获取某个实体执行某个方法的结果
     * @param obj
     * @param method
     * @return
     */
    public static Object invoke(Object obj,Method method){
        if (obj == null || method == null) return null;
        try {
            return method.invoke (obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace ();
        } catch (IllegalAccessException e) {
            e.printStackTrace ();
        } catch (InvocationTargetException e) {
            e.printStackTrace ();
        }
        return null;
    }
}
