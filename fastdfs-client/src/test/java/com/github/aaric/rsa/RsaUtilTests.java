package com.github.aaric.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

/**
 * RsaUtilTests
 *
 * @author Aaric, created on 2020-09-14T09:58.
 * @version 1.2.0-SNAPSHOT
 * @see <a>https://www.chinassl.net/ssltools/decoder-csr.html</a>
 */
@Slf4j
public class RsaUtilTests {

    @Test
    public void testGenRsaP10() throws Exception {
        // 创建密钥键值对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 创建签名
        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        signerBuilder.setProvider(new BouncyCastleProvider());
        ContentSigner signer = signerBuilder.build(keyPair.getPrivate());

        // 创建证书请求
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                new X500Name("C=CN,ST=Hubei,L=Wuhan,O=IT,OU=java,CN=www,E=foo@abc.com"), keyPair.getPublic());
        PKCS10CertificationRequest p10Request = p10Builder.build(signer);

        // 将二进制格式证书转换为CSR格式
        byte[] p10Bytes = p10Request.getEncoded();
        String p10Csr = "-----BEGIN CERTIFICATE REQUEST-----\n";
        p10Csr += Base64.getEncoder().encodeToString(p10Bytes);
        p10Csr += "\n-----END CERTIFICATE REQUEST-----";

        // 打印调试日志
        log.debug(p10Csr);
        Assertions.assertNotNull(p10Csr);
    }

    @Test
    @Disabled
    public void testX509v3() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);

        JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withRSA")
                .setProvider("BC");

        // RootCert
        KeyPair rootKeyPair = keyPairGenerator.generateKeyPair();
        X509v3CertificateBuilder rootCertBuilder = new JcaX509v3CertificateBuilder(
                new X500Name("C=RootCert"),
                BigInteger.valueOf(RandomUtils.nextInt()),
                Date.from(Instant.now()),
                DateUtils.addYears(Date.from(Instant.now()), 10),
                new X500Name("CN=RootCert"),
                rootKeyPair.getPublic()
        );
        rootCertBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));
        rootCertBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));
        X509Certificate rootCert = new JcaX509CertificateConverter()
                .getCertificate(rootCertBuilder.build(signerBuilder.build(rootKeyPair.getPrivate())));
        FileUtils.writeByteArrayToFile(new File("rootCert.cer"), rootCert.getEncoded());

        // SecondCert
        KeyPair secondKeyPair = keyPairGenerator.generateKeyPair();
        X509v3CertificateBuilder secondCertBuilder = new JcaX509v3CertificateBuilder(
                rootCert,
                BigInteger.valueOf(RandomUtils.nextInt()),
                Date.from(Instant.now()),
                DateUtils.addYears(Date.from(Instant.now()), 10),
                new X500Name("CN=SecondCert"),
                secondKeyPair.getPublic()
        );
        secondCertBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));
        secondCertBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));
        X509Certificate secondCert = new JcaX509CertificateConverter()
                .getCertificate(secondCertBuilder.build(signerBuilder.build(secondKeyPair.getPrivate())));
        FileUtils.writeByteArrayToFile(new File("secondCert.cer"), secondCert.getEncoded());

        // ThreeCert
        KeyPair threeKeyPair = keyPairGenerator.generateKeyPair();
        X509v3CertificateBuilder threeCertBuilder = new JcaX509v3CertificateBuilder(
                secondCert,
                BigInteger.valueOf(RandomUtils.nextInt()),
                Date.from(Instant.now()),
                DateUtils.addYears(Date.from(Instant.now()), 10),
                new X500Name("C=CN,ST=Hubei,L=Wuhan,O=IT,OU=java,CN=ThreeCert,E=foo@abc.com"),
                threeKeyPair.getPublic()
        );
        threeCertBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));
        threeCertBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));
        X509Certificate threeCert = new JcaX509CertificateConverter()
                .getCertificate(threeCertBuilder.build(signerBuilder.build(threeKeyPair.getPrivate())));
        FileUtils.writeByteArrayToFile(new File("threeCert.cer"), threeCert.getEncoded());
    }
}
