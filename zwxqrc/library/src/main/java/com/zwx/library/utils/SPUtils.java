package com.zwx.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about shared preference
 * </pre>
 */
@SuppressLint("ApplySharedPref")
public final class SPUtils {

    private static final SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();
    private SharedPreferences sp;

    /**
     * Return the single {@link SPUtils} instance
     *
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance() {
        return getInstance("", Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link SPUtils} instance
     *
     * @param mode Operating mode.
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance(final int mode) {
        return getInstance("", mode);
    }

    /**
     * Return the single {@link SPUtils} instance
     *
     * @param spName The name of sp.
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link SPUtils} instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance(String spName, final int mode) {
        if (isSpace(spName)) spName = "spUtils";
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName, mode);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }

    private SPUtils(final String spName) {
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SPUtils(final String spName, final int mode) {
        sp = Utils.getApp().getSharedPreferences(spName, mode);
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final String value) {
        put(key, value, false);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final int value) {
        put(key, value, false);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final long value) {
        put(key, value, false);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final float value) {
        put(key, value, false);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final boolean value) {
        put(key, value, false);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final Set<String> value) {
        put(key, value, false);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key,
                    final Set<String> value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * Remove all preferences in sp.
     */
    public void clear() {
        clear(false);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    //------------------保存对象

    /**
     * 保存数据到SharedPreferences
     *
     * @param key   键
     * @param value 需要保存的数据
     * @return 保存结果
     */
    public  boolean putData(String key, Object value) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        String type = value.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    editor.putBoolean(key, (Boolean) value);
                    break;
                case "Long":
                    editor.putLong(key, (Long) value);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) value);
                    break;
                case "String":
                    editor.putString(key, (String) value);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) value);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = gson.toJson(value);
                    editor.putString(key, json);
                    break;
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取SharedPreferences中保存的数据
     *
     * @param key          键
     * @param defaultValue 获取失败默认值
     * @return 从SharedPreferences读取的数据
     */
    public  Object getData(String key, Object defaultValue) {
        Object result;
        String type = defaultValue.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    result = sp.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Long":
                    result = sp.getLong(key, (Long) defaultValue);
                    break;
                case "Float":
                    result = sp.getFloat(key, (Float) defaultValue);
                    break;
                case "String":
                    result = sp.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    result = sp.getInt(key, (Integer) defaultValue);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = sp.getString(key, "");
                    if (!json.equals("") && json.length() > 0) {
                        result = gson.fromJson(json, defaultValue.getClass());
                    } else {
                        result = null;
                    }
                    break;
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public  <T> boolean putListData(String key, List<T> list) {
        boolean result;
        String type = list.get(0).getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public  <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = sp.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public  <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public  <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = sp.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        Log.e("SharedPreferencesUtil", obj.toString());
        return map;
    }





}
