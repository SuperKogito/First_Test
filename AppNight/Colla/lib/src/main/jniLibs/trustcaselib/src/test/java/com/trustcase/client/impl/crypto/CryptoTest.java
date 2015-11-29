package com.trustcase.client.impl.crypto;

import com.trustcase.client.api.enums.EncryptionType;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;
import java.util.List;

public class CryptoTest extends TestCase {

    private static final String TEST_MESSAGE = "Falsches Üben von Xylophonmusik quält jeden größeren Zwerg";
    private static final String TEST_ROOM_ID = "room_101";
    private static final String TEST_KEY = "0661a49b24568b9002eaf59f26c198d704331d2fb4a0559447103739babe8d06";

    class MockKeyManager implements KeyManager {
        @Override
        public PublicKey getPublicKey() {
            return new PublicKey("f448811b9966a3f9e108d788b384e149e8bacff1622ee5085f393d1f8a1b5962");
        }

        @Override
        public PrivateKey getPrivateKey() {
            return new PrivateKey("2a8b1468cd4c7a332406ad02751502f13d27cee721f7e19dc61e9a8f989fc3de");
        }

        @Override
        public PublicKey getRoomKey(String roomId) {
            return new PublicKey("ea83fa5ec9b55b7267bc834052d217736917e9d9b701293c27ea9d79f56aa85c");
        }

        @Override
        public List<String> getParticipantJIDs(String roomId) {
            return Arrays.asList("test1", "test2", "test3", "test4");
        }

        @Override
        public PublicKey getParticipantPublicKey(String roomId, String jid) {
            switch (jid) {
                case "test1":
                    return new PublicKey("a0deecccc2b769029804526ad92ba22d52e4a490c33eaaa334ba528cfad3384f");
                case "test2":
                    return new PublicKey("5f91c47ab47237082a9213e559dc80eba2aa94b66a9ffcdc7b127ed456374965");
                case "test3":
                    return new PublicKey("7e626f37fd5b66f83ef3eb9dfbfb985bad79b7282f782e70a5f35b8d14b7de5c");
                case "test4":
                    return new PublicKey("f448811b9966a3f9e108d788b384e149e8bacff1622ee5085f393d1f8a1b5962");
                default: return null;
            }
        }

        @Override
        public int getMyPositionInRoom(String roomId) {
            return 3;
        }

        @Override
        public String getContactProfileKey(String jid) {
            return null;
        }
    }

    private final String str0 = "AAAAAAAAAAAAAAAARmFsc2NoZXMgw5xiZW4gdm9uIFh5bG9waG9ubXVzaWsgcXXDpGx0IGplZGVu\n" +
            "IGdyw7bDn2VyZW4gWndlcmc=\n";

    private final String str1 = "AAAAAQAAAAAAAAAAMEFNYuKHYNY5N08NiIdD7dWhApy28JpwdscCIbPxwcabJoEvcH+EFD97nVNS\n" +
            "pwX+psb+u/QheFIGhumMxyuMyGH9qGEvRElVHqKUATlLg1nRekTA0+MZLTUuvKwnb4I+uW1KOdXP\n";

    private final String str2 = "AAAAAgAAAAAAAAAAAAAABFqXPTl3afMey1Mz2/2DsSdFr0T74lrLNPRIgRuZZqP54QjXiLOE4Uno\n" +
            "us/xYi7lCF85PR+KG1li8rAOjHvUpZbjO/aAmWh9QqYSL33ZSYkcd07ki//SvHg2YN96NwS1qDHz\n" +
            "Sb85y/Ymn5N01Jd6u2Gjl+wGqITN82VBQHqy7U6GBG1MA2bRKYzW9fSEQv2jPGnBaXom1zAMjGoq\n" +
            "46wJBleVKDj5klCXLDDwBx+e+dInl+EwDHyPqgr0RbiCnhV5oPhgXTbAnlROML8f7RZWB33EPRA/\n" +
            "1nx3Xlwt3GpFhDh6Rjm2BThVce/KwISbK1lHBpDp6E9jNC1wx/0IVclyGL/xA90gEhlFVfuCJcm4\n" +
            "CEz+x2GAXWECCUUauqSE2mW/vuBHQRiQ95RkYzraAzw4Jvdo7vJ81B+oujrGiwTDADnXubEaafxz\n";

    private final String str3 = "AAAAAwAAAAAAAAAAAAAABLUlaoX4OWBOeRRF4Tb9lXccZ6Kb4h0FyJp0sKRnn2cfvB2c92TvtvED\n" +
            "dsdPeLxCZl6hRGap26o/+hOJLKweCuk6LyMQ0YAvNUBH/9bQsNoTkd5znYRAQfhgkjY60Nm+AGmh\n" +
            "IcLx2jAErnUIyrKfWS3JdZDjSEw8/VO1fRh7W68pWYKi0TxK/+1BV6yI4oyUPXdkKbZVwpuCK1vv\n" +
            "qOFG6QSFH4gCT8u0vUcYWKysNhEpzm/GLuULTJoRPA8ppFYUevlmqB6M3j25uxrl/J/nxdykNQck\n" +
            "aSNJ1urEf84gUtJk074XjKCQ3ptO4wgL7DJcj87A4eyVTrvQ7EQNIuGHheaxHYACB0LIt+EWL1SM\n" +
            "0e/VEzF1KJe6c7O1XuDS8faenybF5QP79Q==\n";

    private CryptoEngine roomsCrypto;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        roomsCrypto = new CryptoEngine();
        roomsCrypto.init(new MockKeyManager());
    }

    public void testEncrypt0() throws Exception {
        byte[] cipher = roomsCrypto.encryptMessage(TEST_MESSAGE, EncryptionType.UNENCRYPTED, TEST_ROOM_ID, null);
        String decryptedMessage = roomsCrypto.decryptMessage(cipher, TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, decryptedMessage);
    }

    public void testDecrypt0() throws Exception {
        String message = roomsCrypto.decryptMessage(Base64.decodeBase64(str0), TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, message);
    }

    public void testEncrypt1() throws Exception {
        byte[] cipher = roomsCrypto.encryptMessage(TEST_MESSAGE, EncryptionType.SYMMETRIC_KEY, TEST_ROOM_ID, null);
        String decryptedMessage = roomsCrypto.decryptMessage(cipher, TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, decryptedMessage);
    }

    public void testDecrypt1() throws Exception {
        String message = roomsCrypto.decryptMessage(Base64.decodeBase64(str1), TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, message);
    }

    public void testEncrypt2() throws Exception {
        byte[] cipher = roomsCrypto.encryptMessage(TEST_MESSAGE, EncryptionType.PUBLIC_KEY, TEST_ROOM_ID, null);
        String decryptedMessage = roomsCrypto.decryptMessage(cipher, TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, decryptedMessage);
    }

    public void testDecrypt2() throws Exception {
        String message = roomsCrypto.decryptMessage(Base64.decodeBase64(str2), TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, message);
    }

    public void testEncrypt3() throws Exception {
        byte[] cipher = roomsCrypto.encryptMessage(TEST_MESSAGE, EncryptionType.NO_KEY, TEST_ROOM_ID, null);
        String decryptedMessage = roomsCrypto.decryptMessage(cipher, TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, decryptedMessage);
    }

    public void testDecrypt3() throws Exception {
        String message = roomsCrypto.decryptMessage(Base64.decodeBase64(str3), TEST_ROOM_ID);
        Assert.assertEquals(TEST_MESSAGE, message);
    }

    public void testSymmetricEncryption() throws Exception {
        final byte[] cipher = roomsCrypto.encryptSymmetrically(TEST_MESSAGE, TEST_KEY);
        final String decryptedData = roomsCrypto.decryptSymmetrically(cipher, TEST_KEY);
        Assert.assertEquals(TEST_MESSAGE, decryptedData);
    }
}
