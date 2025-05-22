package top.continew.admin;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.continew.admin.common.config.mybatis.BCryptEncryptor;
import top.continew.starter.security.crypto.encryptor.AesEncryptor;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitTest {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int LENGTH = 16;

    private static final SecureRandom random = new SecureRandom();

    @Test
    public void testGenerator() {
        StringBuilder sb = new StringBuilder(LENGTH);
        // 生成第一个字符，确保是字母
        int firstCharIndex = random.nextInt(LETTERS.length());
        sb.append(LETTERS.charAt(firstCharIndex));

        if (LENGTH > 1) {
            // 生成剩余的LENGTH-1个字符，可以是字母或数字
            for (int i = 1; i < LENGTH; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(randomIndex));
            }
        }

        System.out.println(sb);
    }

    @Test
    public void testEncoder() throws Exception {
        final String password = "K8H23psg5Irw3vyB";
        // String publicKey =
        // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8M+pZXVfDSP8sE8+rFfjl1G/nEqUOJPXTVdwmkjlKFZZA+vvniHJ9H7b+vAvxYep73R5I3rQKAl74rpu0UfBRGEUtkwPPMk8sJ9shpZqQ9VLZM64qHxxVPU3tByveEDxoVsIufVGnjtCNJwzNtbpl+wBxdXyjF5xcmov3DjZ1PzxT856HdHwjq9SFXlNKbTS2wl/xfCWuW0ss4EeNBJ3U5qLgk8qjOGYaGXv74tj/4Jo4lEuyOm6KjKevxjAS4D6TVbECovK3csw8w/h/9cUeiHshc060O+P37223A1MHrd2vY2SCKC28GYKGvpmtie7l5Tuz4VZTMi5VTmlVHKicwIDAQAB";
        // String privateKey =
        // "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDwz6lldV8NI/ywTz6sV+OXUb+cSpQ4k9dNV3CaSOUoVlkD6++eIcn0ftv68C/Fh6nvdHkjetAoCXvium7RR8FEYRS2TA88yTywn2yGlmpD1UtkzriofHFU9Te0HK94QPGhWwi59UaeO0I0nDM21umX7AHF1fKMXnFyai/cONnU/PFPznod0fCOr1IVeU0ptNLbCX/F8Ja5bSyzgR40EndTmouCTyqM4ZhoZe/vi2P/gmjiUS7I6boqMp6/GMBLgPpNVsQKi8rdyzDzD+H/1xR6IeyFzTrQ74/fvbbcDUwet3a9jZIIoLbwZgoa+ma2J7uXlO7PhVlMyLlVOaVUcqJzAgMBAAECggEACfQ7h1Mbs7paFpuf9pXHdZjOd9JpYBFUZAEKSYZb2pf/I/dbt0ikRXP8+dl10A90ic5ht4K7GfySX2Pfcs0pgv8UURjBxHx5AsAER2fuSpGFhUw6O9SxBpcAT3SkGC9vDYFq0ez+l0nRycTvJQQrMblytU/Lr9uziswy4EKFVpVtiGV26o9okla/LuXFUOJGWSZxMDfJlTu+zk+4MC9R4SfssVs2FNh6nItxU/XyX2vvDqNlcKmu/P9A0mhj4mF7yqEQBPA9GmaxHmqpHcsEVgiO2YDXSsr8nSkIMMdttM+LdCD+K2m9cAJ7XyN6YHHM2kKWd1wyrDrKnSmE595DsQKBgQD9AXZhGOPql5YrTmMiM8lo07zhpehRoqJAHAukFGwszWjPVzHx2h7S9TiyYX/6ju+d4y09ARPLjMr1WfwjbZA8tjABs1XVEGTXXblsZhEGzx54ASrDtiIql6RCKmUmjf0oIa+TlDV2wzKePNgFpNmtnS/NdFUiPjBpoPfn/1Ep6wKBgQDzqUDSPI86P1xKoHDF4OKCXorcbsDAmSvglbVPy/gfMsT648aeR7vskhKysk55qK1li1XVPG/PjfqIEY3LwrCPFfvjsvIa+Yzzg2dTELx/KFSMCvK3BWtEMVvC9SpqhxCnqJUbPSUlXL3sC5pYYYS97loZhIQ46Bb0tIcYlS9/mQKBgAQajPgANloswUPMjrEUU/T+Ujb6wOzqDWWqzsmHh0Wmw0dxq563of5b+eJy0GeY/+v4rlgp90865mPaZMuSUF+buOWeKgs9q9UOcR5//VC19VGO+0mXX7B/KyvjkiZw8SVAfp+PLNrTSbPlWr+kr98wCIF1nJfkmFZuRPcfXgqnAoGAM8tXnxkGbSBI0wg10KpUG9hS9jaIvKgSnqPULckyzhbxrk86yix/cvM+DERA0DWd3kb3EOPO9LS+e6wXkyeodlH7AFTqh/diEeVt0kdtei4tgrNVEriRK6a+Vbvdr1VUof8A6xBdVArZyFJTFdRs7h67gk8b+CjFsHCrgvs7ZIkCgYB+6vgScj+vPK4cs75UhuTv3+tEVKNqVJE09gIERuUHchkLHO2MFK5Hlo+6rcB/SJg9r9PM3C4D6vSWbP1yBEfbvrpR/wbxn3dLPQg8OhrHiVxNa8Rvb7Z5SnINVuBDVVMAZl9XcxZw99ge03edFwD9A3qJpsmYuYKN2VVaf8w6XQ==";
        final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9zHWcYUSFLQ0IgWLarZ+pnVyreaWenpilPpwQwyAmW/yrRw3GuWaulnZgicD3EWG+8fEihdVrI2CeeF4tI4KwOL2lKl8trZu4qFX+qcgvsIizxcNuKXe+pf6xFirdxKWX8507ITm4U/hQSaNMPBjlg6Eg0IPNJteFeSEDZTeNhGMYVyz3AFLgXgSeAVPLQvmS6OFojz/WK3zgGEsCISFs3LWVs74tzGpJV+Tuhx+nwwYTiKuHIYl9Lsm0j6FAMNTjNs7Hh0+YRidKOKvfdd5HDpsa57dTX7OKIG6cahJS6lOhk6KeM0U2Jp2/70Cnod4UjBqT+9LvNyBjN4MjpYigQIDAQAB";
        final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQD3MdZxhRIUtDQiBYtqtn6mdXKt5pZ6emKU+nBDDICZb/KtHDca5Zq6WdmCJwPcRYb7x8SKF1WsjYJ54Xi0jgrA4vaUqXy2tm7ioVf6pyC+wiLPFw24pd76l/rEWKt3EpZfznTshObhT+FBJo0w8GOWDoSDQg80m14V5IQNlN42EYxhXLPcAUuBeBJ4BU8tC+ZLo4WiPP9YrfOAYSwIhIWzctZWzvi3MaklX5O6HH6fDBhOIq4chiX0uybSPoUAw1OM2zseHT5hGJ0o4q9913kcOmxrnt1Nfs4ogbpxqElLqU6GTop4zRTYmnb/vQKeh3hSMGpP70u83IGM3gyOliKBAgMBAAECggEAESpl0NZDzipBYIdwCgYIP3jycL8Nfg3XoK7PcuiAWadbe8f0z6pYF067+vzImNg/KmbR8CD0sxnUkLAmmKswdfqM0v/+CBYdFyHLIT3P2U9T6XuZWVQzasV5BX9+0LL6vEZy+iW0frr0Unjh3x8iWz5A13iT47xSSUToIqVO1QQ6ARmvXnGkAt/Cu3usBZmknrtb+vkICczgtStBK4NkNuVy+OtPfA7+utw17NoT9INFRWViQZw2rrj0L6sq4uTjOAjMjTpWvjxBoGMjeX0t8vvdqkihG4+tHECzwx07JyFAMCWWdksybaPjZu+dN4W60iikbNIEOoP2pwJLqbthAQKBgQD/p+gAxq3kUeC9ej1mC4tXfRvg0V/Um5xYmjxXbQnKHHXTJ/KYbqR/87H2VVJ16s4M39QY1RX7yx1dGABL3wII4QrC/s53nqpfkEqf5hXb2AcnREc8VuSyNAhestgzkFk1ljCU8uPDucUvaoV8vxbn77/yzBfUgaEndZQ6/zrGcQKBgQD3hwQO0nE/N+3eZlRXiXk7lwLLVFKeg2p0I6D1TW1cOj+tfdJlm0Uryi3S4p+TN803cC7Dr9BzWWTsSX7TJQfEr00IdjjlcMDwGgqnGUQ0kss5jrc77NOPcWs9Ct11K4L8rWpe1ROFYxq/aH8ChKcqLpsSkvLNGOhfJ7d4kZrFEQKBgQDNr1IaJb7L3hAZgZqaWhleZzqhsvINxLCSHG0FzaU9pY4qN46XkxToGLGKRXUVh7cFZqNm4zFPpkfWmTPL1vz1FdGptoV4egAK6LURVnUprfF7ZxSE+EgBE5nUTsFhIvaTBWXKwGv9EZ4SmvoQtlEAg8SD3C4j+7MjYxbCLIwmEQKBgQDGMnnxVFgRgrsYpV9LaGyGZnh6Tt6IxjlrHUw4nvK+MnXieSkK6rUMRytB/OxBeSD9Dvqi/hgvBJlnCcJfVvtNZgCMkf0k2o/isTdubTDL1+6Z+8iEVzFfXjW7/wMsWtbbdBAENyEWYTB9qzJn1cf0YrUQvlFkIFmQN8EU7aG7wQKBgCqjQXlrLatxRbmJaofSXO3bwLlITqpyuwKEG8QgmKPbz1YQRVaJdQFAAN/JRp1HzjBd9zMhVWUs9PHejUAoz6+MJjP2o/oAak710ygFs8ZWrGJCgML5Jq967nB+hdjj5ytwFVqEcZFfOgzxBzg9/2mka7nI/6IzH0JwEuMbPfgU";

        Map<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>(1);
        idToPasswordEncoder.put("bcrypt", new BCryptPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", idToPasswordEncoder);
        BCryptEncryptor bCryptEncryptor = new BCryptEncryptor(passwordEncoder);

        String pwd = "admin123";
        String encodedPwd = bCryptEncryptor.encrypt(pwd, password, publicKey);
        System.out.println(pwd + " encode >> " + encodedPwd);
        assertTrue(passwordEncoder.matches(pwd, encodedPwd));

        System.out.println("test in db");
        assertTrue(
                passwordEncoder.matches(pwd, "{bcrypt}$2a$10$4jGwK2BMJ7FgVR.mgwGodey8.xR8FLoU1XSXpxJ9nZQt.pufhasSa"));
        System.out.println("test in db ok");

        String phone = "15824982385";
        AesEncryptor aesEncryptor = new AesEncryptor();
        String encodedPhone = aesEncryptor.encrypt(phone, password, publicKey);
        System.out.println(phone + " encoded >> " + encodedPhone);
        String decodedPhone = aesEncryptor.decrypt(encodedPhone, password, privateKey);
        System.out.println(encodedPhone + " decoded >> " + decodedPhone);
        assertEquals(phone, decodedPhone);

        String email = "liulinjie.cpp@outlook.com";
        String encodedEmail = aesEncryptor.encrypt(email, password, publicKey);
        System.out.println(email + " encoded >> " + encodedEmail);
        String decodedEmail = aesEncryptor.decrypt(encodedEmail, password, privateKey);
        System.out.println(encodedEmail + " decoded >> " + decodedEmail);
        assertEquals(email, decodedEmail);

//        String accessKey = "cOfkY2kua3ASfqYxLb5J";
//        String encodedAccessKey = aesEncryptor.encrypt(accessKey, password, publicKey);
//        System.out.println(accessKey + " encoded >> " + encodedAccessKey);
//        String decodedAccessKey = aesEncryptor.decrypt(encodedAccessKey, password, privateKey);
//        System.out.println(encodedAccessKey + " decoded >> " + decodedAccessKey);
//        assertEquals(accessKey, decodedAccessKey);
//
//        String secureKey = "INygFtG4rmc4QRKtYsGVt5NWOjon8WLlTaqahqZX";
//        String encodedSecureKey = aesEncryptor.encrypt(secureKey, password, publicKey);
//        System.out.println(secureKey + " encoded >> " + encodedSecureKey);
//        String decodedSecureKey = aesEncryptor.decrypt(encodedSecureKey, password, privateKey);
//        System.out.println(encodedSecureKey + " decoded >> " + decodedSecureKey);
//        assertEquals(secureKey, decodedSecureKey);

        String encodedRsa = Base64.encode(SecureUtil.rsa(null, publicKey).encrypt("admin123", KeyType.PublicKey));
        System.out.println(encodedRsa);

        String decodedRsa = SecureUtil.rsa(privateKey, null).decryptStr(encodedRsa, KeyType.PrivateKey);
        System.out.println(decodedRsa);
    }
}
