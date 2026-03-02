package com.web3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class ProofOfWork {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String nickname = "海阔天空";
        int nonce = 0;
        String targetPrefix = "0000"; // 设定难度目标：4个零

        long startTime = System.currentTimeMillis();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        System.out.println("开始挖矿...");

        while (true) {
            String input = nickname + nonce;
            // 执行 SHA-256 哈希运算
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String hexHash = bytesToHex(hashBytes);

            // 检查是否满足条件
            if (hexHash.startsWith(targetPrefix)) {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                System.out.println("--- 挖矿成功！ ---");
                System.out.println("花费时间: " + duration + " 毫秒");
                System.out.println("Hash 内容: " + input);
                System.out.println("Hash 值: " + hexHash);
                break;
            }

            nonce++; // 不满足条件，修改随机数继续
        }
    }

    // 将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
