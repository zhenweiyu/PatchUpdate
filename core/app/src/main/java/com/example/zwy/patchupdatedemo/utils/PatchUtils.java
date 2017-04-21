package com.example.zwy.patchupdatedemo.utils;

/**
 * Created by Zhen Weiyu on 2017/4/20.
 */

public class PatchUtils {

    static{
        System.loadLibrary("patcher");
    }

    public static native int patch(String oldVersionPath,String newVersionPath,String patchPath);


}
