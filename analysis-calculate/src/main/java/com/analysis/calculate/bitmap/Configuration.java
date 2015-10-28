package com.analysis.calculate.bitmap;

import com.analysis.common.config.ImmutableConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by crazyy on 15/5/7.
 */
class Configuration implements ImmutableConfiguration {

    private static PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

    private PropertiesConfiguration properties = new PropertiesConfiguration();

    protected Configuration(String... resourcesPaths) {
        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                properties.load(is);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ConfigurationException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if(is!=null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    @Override
    public Object getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Iterator<String> getKeys(String prefix) {
        return properties.getKeys(prefix);
    }

    @Override
    public Iterator<String> getKeys() {
        return properties.getKeys();
    }

    @Override
    public boolean getBoolean(String key) {
        return properties.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return properties.getBoolean(key, defaultValue);
    }

    @Override
    public double getDouble(String key) {
        return properties.getDouble(key);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return properties.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return properties.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return properties.getInt(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return properties.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return properties.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key) {
        return properties.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return properties.getString(key, defaultValue);
    }

    @Override
    public String[] getStringArray(String key) {
        return properties.getStringArray(key);
    }

    @Override
    public List<Object> getList(String key) {
        return properties.getList(key);
    }

    @Override
    public List<Object> getList(String key, List<Object> defaultValue) {
        return properties.getList(key, defaultValue);
    }
}
