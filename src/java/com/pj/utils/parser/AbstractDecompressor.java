/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.utils.parser;

import java.io.File;

/**
 *
 * @author luzhenwen
 */
public abstract class AbstractDecompressor {
    protected File file;
    
    public AbstractDecompressor(File file){
        this.file = file;
    }
    
    public abstract PackageInfo decompress();
    
    public static class PackageInfo{
        public String versionName;
        public Long   versionCode;
        public String name;
        public String description ;

        @Override
        public String toString() {
            return String.format("name=%s,version=[%s,%d],%s", name,versionName,versionCode,description);
        }
    }
}
