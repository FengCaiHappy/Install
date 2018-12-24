package com.neusoft.installapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by johan on 6/13/18.
 */

public class SharePreferencesUtil {
    public static final String FILE_NAME = "share_data";

    public SharePreferencesUtil() {
    }

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        if(object instanceof String) {
            editor.putString(key, (String)object);
        } else if(object instanceof Integer) {
            editor.putInt(key, ((Integer)object).intValue());
        } else if(object instanceof Boolean) {
            editor.putBoolean(key, ((Boolean)object).booleanValue());
        } else if(object instanceof Float) {
            editor.putFloat(key, ((Float)object).floatValue());
        } else if(object instanceof Long) {
            editor.putLong(key, ((Long)object).longValue());
        } else {
            editor.putString(key, object.toString());
        }

        SharePreferencesUtil.SharedPreferencesCompat.apply(editor);
    }

    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        return defaultObject instanceof String ?sp.getString(key, (String)defaultObject):(defaultObject instanceof Integer ? Integer.valueOf(sp.getInt(key, ((Integer)defaultObject).intValue())):(defaultObject instanceof Boolean ? Boolean.valueOf(sp.getBoolean(key, ((Boolean)defaultObject).booleanValue())):(defaultObject instanceof Float ? Float.valueOf(sp.getFloat(key, ((Float)defaultObject).floatValue())):(defaultObject instanceof Long ? Long.valueOf(sp.getLong(key, ((Long)defaultObject).longValue())):null))));
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharePreferencesUtil.SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharePreferencesUtil.SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("share_data", 0);
        return sp.getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply", new Class[0]);
            } catch (NoSuchMethodException var1) {
                return null;
            }
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if(sApplyMethod != null) {
                    sApplyMethod.invoke(editor, new Object[0]);
                    return;
                }
            } catch (IllegalArgumentException var2) {
                ;
            } catch (IllegalAccessException var3) {
                ;
            } catch (InvocationTargetException var4) {
                ;
            }

            editor.commit();
        }
    }
}
