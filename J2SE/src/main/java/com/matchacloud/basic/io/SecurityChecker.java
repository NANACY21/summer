package com.matchacloud.basic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全检查者
 */
public class SecurityChecker {

    /**
     * 计算文件的 MD5 哈希值
     *
     * @param filePath 文件的路径
     * @return 文件的 MD5 哈希值的十六进制字符串表示
     * @throws IOException              读取文件时可能出现的 I/O 异常
     * @throws NoSuchAlgorithmException 若不支持 MD5 算法抛出此异常
     */
    public static String calculateMD5(String filePath) throws IOException, NoSuchAlgorithmException {
        // 检查文件是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("指定的文件不存在: " + filePath);
        }
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
