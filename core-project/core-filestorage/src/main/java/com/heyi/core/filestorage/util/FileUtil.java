package com.heyi.core.filestorage.util;

import com.heyi.core.filestorage.entity.FsFile;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FileUtil
 * @Description: 获取文件的信息
 * @Author: lidong
 * @CreateDate: 2018/8/21$ 11:19 AM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/21$ 11:19 AM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public class FileUtil {

    private static String rootPath = "";

    /**
     * 获取文件后缀名
     *
     * @param file
     * @return
     */
    public static String getFileSuffix(MultipartFile file) {
        try {
            return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取文件名(不含扩展名)
     *
     * @param file
     * @return
     */
    public static String getFileName(MultipartFile file) {
        try {
            String oFileName = file.getOriginalFilename();
            if ((oFileName != null) && (oFileName.length() > 0)) {
                int dot = oFileName.lastIndexOf('.');
                if ((dot > -1) && (dot < (oFileName.length()))) {
                    return oFileName.substring(0, dot);
                }
            }
            return oFileName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取文件的大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(MultipartFile file) {
        try {
            return file.getSize();
        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }

    /**
     * 文件的唯一编码
     *
     * @return
     */
    public static String getFileCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * 文件的加密信息
     *
     * @return
     */
    public static String getFileEncryption(MultipartFile file) {
        try {
            return MD5Util.encode(file.getInputStream());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取文件的版本
     *
     * @param fsFile
     * @return
     */
    public static String getFileVersion(FsFile fsFile) {
        // 如果上传的文件和已知的文件的相同，则版本的信息提升（+1）
        int version = Integer.valueOf(fsFile.getVersion()) + 1;
        return String.valueOf(version);
    }

    /**
     * 将文件保存
     *
     * @param file 上传的文件
     * @param path 服务器端保存的文件绝对路径
     */
    public static void saveFile(MultipartFile file, String path) throws IOException {
        File uploadFile = new File(path);
        if (!uploadFile.isDirectory()) {
            File dir = uploadFile.getParentFile();
            // 如果目录不存在，自动创建文件夹
            if (dir != null && !dir.exists()) {
                // 创建多级目录
                dir.mkdirs();
            }
        }
        file.transferTo(uploadFile);
    }

    /**
     * 文件的删除
     *
     * @param path
     */
    public static void deleteFile(String path) {
        if (ObjectUtils.isEmpty(path)) return;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 改方法只是兼容处理开发阶段不同操作系统的路径问题
     *
     * @return
     */
    public static String getRootPath() {
        if (!StringUtils.isEmpty(rootPath)) return rootPath;
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
        }
        if (!path.exists()) path = new File("");
        rootPath = (path.getAbsolutePath() + File.separator);
        return rootPath;
    }

    /**
     * 获取文件夹路径
     *
     * @param folderId 文件夹的编码
     * @return
     */
    public static String getFolderPath(String folderId) {
        int sum = 0;
        for (byte b : folderId.getBytes()) {
            sum += b;
        }
        int hash = sum / 100 + 1;
        String folderPath = String.format("%08d%s", hash, "_folder");
        return String.format("%s%s", getRootPath(), folderPath);
    }

    /**
     * 获取文件路径
     *
     * @param file
     * @return
     */
    public static String getFilePath(String rootPaths, FsFile file) {
        rootPath = rootPaths;
        int fileId;
        String fileName;
        String folderId;
        if (file == null || (fileId = file.getId()) <= 0
                || StringUtils.isEmpty(fileName = file.getName())
                || StringUtils.isEmpty(folderId = file.getFolder().getId()))
            throw new IllegalArgumentException("invalid argument `file` ");
        String newFileName = fileId + "_" + fileName + file.getExtName();
        String filePath = String.format("%s%s%s", getFolderPath(folderId), File.separator, newFileName);
        return filePath;
    }
}
