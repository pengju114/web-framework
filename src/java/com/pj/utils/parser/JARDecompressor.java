/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.utils.parser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author luzhenwen
 */
public class JARDecompressor extends AbstractDecompressor{
    
    private static final String VERSION_NAME = "version";
    private static final String VERSION_CODE = "versionCode";
    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";

    public JARDecompressor(File file) {
        super(file);
    }
    
    
    @Override
    public PackageInfo decompress(){
        if (file != null && file.exists()) {
            try {
                ZipFile zipFile = new ZipFile(file);
                ZipEntry infoEntry = zipFile.getEntry("info.properties");
                
                InputStream is = zipFile.getInputStream(infoEntry);
                Properties properties = new Properties();
                properties.load(is);
                
                PackageInfo info = new PackageInfo();
                info.description = properties.getProperty(DESCRIPTION);
                info.name = properties.getProperty(NAME);
                try {
                    info.versionCode = Long.parseLong(properties.getProperty(VERSION_CODE,"0"));
                } catch (Exception e) {
                }
                info.versionName = properties.getProperty(VERSION_NAME);
                
                try {
                    is.close();
                } catch (Exception e) {
                }
                
                try {
                    zipFile.close();
                } catch (Exception e) {
                }

                
                return info;
            } catch (Exception e) {
            }
        }
        
        return null;
    }
}
