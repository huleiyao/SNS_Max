package com.bytegem.snsmax.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsFileUtils {
    private static String DB_PATH = Environment.getExternalStorageDirectory().toString() + "/maxsns/";

    /**
     * 复制assets下的文件时用这个方法
     *
     * @throws IOException
     */
    public static String copyBigDataBase(Context context, String fileName) throws IOException {
        InputStream myInput;
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbf = new File(DB_PATH + fileName);
        if (dbf.exists()) {
            dbf.delete();
        }
        String outFileName = DB_PATH + fileName;
        OutputStream myOutput = new FileOutputStream(outFileName);
        myInput = context.getAssets().open(fileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
        boolean is = dbf.exists();
        return outFileName;
    }
}
