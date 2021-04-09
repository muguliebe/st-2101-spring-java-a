package com.example.demo.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EncryptUtil {

    @Autowired ApplicationContext ctx;
    @Value("${app.secret}") String key;
    static EncryptUtil instance;
    PooledPBEStringEncryptor encryptor;

    @PostConstruct
    void init() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        instance = this;
        this.encryptor = encryptor;
    }

    public static String encrypt(String input) {
        EncryptUtil serviceEncrypt = instance.ctx.getBean(EncryptUtil.class);
        return serviceEncrypt.encryptor.encrypt(input);
    }

    public static String decrypt(String input) {
        EncryptUtil serviceEncrypt = instance.ctx.getBean(EncryptUtil.class);
        return serviceEncrypt.encryptor.decrypt(input);
    }
}
