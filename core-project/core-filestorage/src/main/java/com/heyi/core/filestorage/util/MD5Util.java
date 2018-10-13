package com.heyi.core.filestorage.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5Util
 * @Description: 用md5加密
 * @Author: lidong
 * @CreateDate: 2018/8/23$ 11:56 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/23$ 11:56 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public class MD5Util {

    /**
     * 可以把一段文字转换为MD
     * Can convert a file to MD5
     *
     * @param text
     * @return md5
     */
    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance( "MD5" );
            byte[] buffer = digest.digest( text.getBytes() );
            return getResult( buffer );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 任意文件转换成Md5
     * Can convert a text to MD5
     * @param in
     * @return md5
     */

    public static String encode(InputStream in) {
        try {
            MessageDigest digester = MessageDigest.getInstance( "MD5" );
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = in.read( bytes )) > 0) {
                digester.update( bytes, 0, byteCount );
            }
            return getResult( digester.digest() );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return null;
    }

    /**
     * 获取密文
     *
     * @param digest byte -128 ---- 127
     * @return
     */
    private static String getResult(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            // & 0XFF操作，可以把高24位置0以避免这样错误
            int a = b & 0xff;
            String hex = Integer.toHexString( a );
            if (hex.length() == 1) {
                hex = 0 + hex;
            }
            sb.append( hex );
        }
        return sb.toString();
    }
}
