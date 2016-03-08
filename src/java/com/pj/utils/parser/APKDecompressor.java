/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.utils.parser;

import java.io.File;
import apk.parser.ApkParser;
import apk.parser.bean.ApkMeta;

/**
 *
 * @author luzhenwen
 */
public class APKDecompressor extends AbstractDecompressor{

    public APKDecompressor(File file) {
        super(file);
    }

    @Override
    public PackageInfo decompress() {
        try {
            ApkParser parser = new ApkParser(file);
            
            ApkMeta meta = parser.getApkMeta();
            
            PackageInfo info = new PackageInfo();
            
            info.name = meta.getLabel();
            info.description = meta.getName();
            info.versionCode = meta.getVersionCode();
            info.versionName = meta.getVersionName();
            
            return info;
        } catch (Exception e) {
        }
        
        return null;
    }
    
}
