package com.analysis.calculate.bitmap;

import org.roaringbitmap.RoaringBitmap;

/**
 * Created by crazyy on 15/4/29.
 */
public class BitmapBean {

    private String data;
    private String module;
    private String moduleName;
    private RoaringBitmap bitmap;

    public BitmapBean(String data, String module, String moduleName, RoaringBitmap bitmap) {
        this.data = data;
        this.module = module;
        this.moduleName = moduleName;
        this.bitmap = bitmap;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public RoaringBitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(RoaringBitmap bitmap) {
        this.bitmap = bitmap;
    }
}
