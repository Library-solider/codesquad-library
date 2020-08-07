package kr.codesquad.library.global.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encrypt = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(System.getenv("JASYPT_PASSWORD")); // 암호화 키 값<서버의 환경변수로 설정할 것>
        config.setAlgorithm("PBEWithMD5AndDES"); // 사용할 알고리즘
        config.setKeyObtentionIterations("1000"); // 해싱을 반복할 횟수
        config.setPoolSize("1");
        config.setProviderName("SunJCE"); // Default 값
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64"); // 인코딩 될 값 hexadecimal도 사용가능
        encrypt.setConfig(config);
        return encrypt;
    }
}

