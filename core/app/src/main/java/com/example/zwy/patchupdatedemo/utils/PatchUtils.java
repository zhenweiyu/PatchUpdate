package com.example.zwy.patchupdatedemo.utils;


public class PatchUtils {

    static{
        System.loadLibrary("patcher");
    }

    public static native int patch(String oldVersionPath,String newVersionPath,String patchPath);


}
