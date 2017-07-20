package com.fubang.video.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by android on 2016/5/4.
 */
public class FileUtils {
    private static final String Files = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/yaoyao/";
    private static final String Files_Music = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/yaoyao/music/";
    private static final String Files_Lrc = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/yaoyao/lrc/";
    private static final String Files_Temp = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/yaoyao/" + "temp/";
    private static String map_icons = FileUtils.getFiles() + "icons/";
    public static final String offlinedata = FileUtils.getFiles() + "offlinedata/";

    public static String getHeadFiles() {
        File file = new File(Files);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Files;
    }

    public static String getTempFiles() {
        File file = new File(Files_Temp);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Files_Temp;
    }

    public static String getMusicFiles() {
        File file = new File(Files_Music);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Files_Music;
    }

    public static String getLrcFiles() {
        File file = new File(Files_Lrc);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Files_Lrc;
    }

    /**
     * 加载本地map图片
     *
     * @param name,typeid
     * @return
     */
    public static Bitmap getLoaclMapIconBitmap(String name, String typeid) {
        try {
            FileInputStream fis = new FileInputStream(map_icons + name + typeid + ".png1");
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public static File getFiles() {
//
//    }

    public static String getFiles() {
        File file = new File(Files);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Files;
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public boolean copyFolder(String oldPath, String newPath) {
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            isok = false;
        }
        return isok;
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static double getFolderSize(File file) {

        double size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }

    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除指定文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);

                if (file.exists()) {
                    file.delete();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;

    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    public static boolean String2File(String res, String filepath) {
        return String2File(res, filepath, false);
    }

    public static boolean String2File(String res, String filepath, boolean inLast) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filepath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile, inLast));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param filepath 文本文件
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String File2String(String filepath) {
        String encoding = "utf-8";
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(filepath),
                        encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(filepath));
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        // 返回转换结果
        if (writer != null)
            return writer.toString();
        else
            return null;
    }

    public static boolean FileIsExists(String file) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件解析为Bitmap
     *
     * @param mContext,fileName
     * @return 文件的Bitmap
     */
    public static Bitmap getBitmapFromAssets(Context mContext, String fileName) {
        AssetManager am = mContext.getAssets();
        InputStream is = null;
        Bitmap bm;

        try {
            is = am.open(fileName);
            bm = BitmapFactory.decodeStream(is);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件解析为String
     *
     * @param mContext,fileName
     * @return 文件的String
     */
    public static String getStrFromAssets(Context mContext, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line + "\n";
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //扫描实现mp3
    static ArrayList<String> list = new ArrayList<>();

    public static ArrayList<String> searchMp3Infos(File file) {
        if (file != null) {
            list.clear();
            File[] list_file = file.listFiles();
            return getMusicFileName(list_file);
        }
        return list;
    }

    public static ArrayList<String> getMusicFileName(File[] files) {
        if (files != null)// 先判断目录是否为空，否则会报空指针
        {
            for (File file : files) {
                if (file.length() > 0) {
                    if (file.isDirectory()) {
                        getMusicFileName(file.listFiles());
                    } else {
                        String fileName = file.getName();
                        if (fileName.endsWith(".mp3")) {
                            String s = fileName;
                            list.add(s);
                        }
                    }
                }
            }
        }
        return list;
    }


    //扫描实现lrc
    static ArrayList<String> list_lrc = new ArrayList<>();

    public static ArrayList<String> searchLrcInfos(File file) {
        if (file != null) {
            list_lrc.clear();
            File[] list_file = file.listFiles();
            return getLrcFileName(list_file);
        }
        return list_lrc;
    }

    public static ArrayList<String> getLrcFileName(File[] files) {
        if (files != null)// 先判断目录是否为空，否则会报空指针
        {
            for (File file : files) {
                if (file.length() > 0) {
                    if (file.isDirectory()) {
                        getLrcFileName(file.listFiles());
                    } else {
                        String fileName = file.getName();
                        if (fileName.endsWith(".lrc")) {
                            String s = fileName;
                            list_lrc.add(s);
                        }
                    }
                }
            }
        }
        return list_lrc;
    }

    /**
     * 将bitmap转化成图片
     */
    public static String saveImg(Bitmap b) throws Exception {

        String path = getFiles()+"videoimag/";
        File mediaFile = new File(path + "temp"+ ".jpg");
        if (mediaFile.exists()) {
            mediaFile.delete();
        }
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        mediaFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(mediaFile);
        b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        b.recycle();
        b = null;
        System.gc();
        return mediaFile.getPath();
    }
}
