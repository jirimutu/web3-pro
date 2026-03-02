package com.web3;

import java.security.*;
import java.util.Base64;

public class RSAPowSignature {
    public static void main(String[] args) throws Exception {
        // 1. 模拟你之前的 POW 结果
        // 假设这是你挖掘出的符合 4 个 0 条件的内容
        String powContent = "海阔天空64007";

        // 2. 生成 RSA 密钥对 (2048位)
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        System.out.println("--- 密钥对已生成 ---");

        // 3. 使用私钥对 POW 内容进行签名
        // 我们使用 SHA256withRSA 算法，它会先对内容做 SHA256，再用 RSA 私钥加密哈希值
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(powContent.getBytes());
        byte[] signature = privateSignature.sign();

        String signatureBase64 = Base64.getEncoder().encodeToString(signature);
        System.out.println("数据内容: " + powContent);
        System.out.println("生成签名 (Base64): " + signatureBase64);

        // 4. 使用公钥进行验证
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(powContent.getBytes());

        boolean isCorrect = publicSignature.verify(signature);

        System.out.println("--- 验证结果 ---");
        System.out.println("签名是否有效: " + (isCorrect ? "✅ 成功" : "❌ 失败"));

        // 5. 篡改测试
        String tamperedContent = "海阔天空64007"; // 仅仅修改了一位数字
        publicSignature.update(tamperedContent.getBytes());
        boolean isTamperedValid = publicSignature.verify(signature);
        System.out.println("篡改数据后验证: " + (isTamperedValid ? "✅ 有效" : "❌ 无效（检测到篡改）"));
    }
}
