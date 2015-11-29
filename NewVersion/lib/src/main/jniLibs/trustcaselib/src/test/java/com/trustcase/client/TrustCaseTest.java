package com.trustcase.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.junit.Test;

/**
 * 
 * @author Gunther Klein
 */
public class TrustCaseTest {

    @Test
    public void testBuilder() {
        final String endpoint = "https://www.trustcase.com/api";
        final String jid = "123342523445@trustcase.com";
        final String privateKey = "1a8c1468cd4c7a322406ad52751502513d27cee721f7e19dc61e9a8f989fc3de";
        final String publicKey = "f448811b9966a3f9e108478ab384e1a9e8bacff1622ee5085f393d1f8a1b5962";
        final String password = "b18d784029ae4de1422d5089d6c35953";

        TrustCaseClient client = TrustCase.newBuilder().apiEndpoint(endpoint).jid(jid).privateKey(privateKey)
                .publicKey(publicKey).password(password).build();

        assertNotNull(client);
        assertEquals(endpoint, client.getApiEndpoint());
        assertEquals(jid, client.getJid());
        assertNotNull(client.getCredentials());

        assertEquals(jid, client.getCredentials().getJid());
        assertEquals(password, client.getCredentials().getPassword());
        assertEquals(privateKey, client.getCredentials().getPrivateKey());
        assertEquals(publicKey, client.getCredentials().getPublicKey());

        assertEquals(publicKey, client.getKeyPair().getPublicKey());
        assertEquals(privateKey, client.getKeyPair().getPrivateKey());

        client = TrustCase.newBuilder().apiEndpoint(endpoint).build();
        assertNotNull(client);
        assertEquals(endpoint, client.getApiEndpoint());
        assertNull(client.getCredentials());
        assertNull(client.getKeyPair());
        assertNull(client.getJid());
    }

    @Test
    public void testBuilderWithProperties() {
        Properties props = new Properties();
        final String endpoint = "https://www.trustcase.com/api";
        final String jid = "123342523445@trustcase.com";
        final String privateKey = "1a8c1468cd4c7a322406ad52751502513d27cee721f7e19dc61e9a8f989fc3de";
        final String publicKey = "f448811b9966a3f9e108478ab384e1a9e8bacff1622ee5085f393d1f8a1b5962";
        final String password = "b18d784029ae4de1422d5089d6c35953";
        props.setProperty(TrustCase.CLIENT_PROPERTY_API_ENDPOINT, endpoint);
        props.setProperty(TrustCase.CLIENT_PROPERTY_JID, jid);
        props.setProperty(TrustCase.CLIENT_PROPERTY_PASSWORD, password);
        props.setProperty(TrustCase.CLIENT_PROPERTY_PRIVATE_KEY, privateKey);
        props.setProperty(TrustCase.CLIENT_PROPERTY_PUBLIC_KEY, publicKey);

        TrustCaseClient client = TrustCase.newBuilder().properties(props).build();
        assertNotNull(client);
        assertEquals(endpoint, client.getApiEndpoint());
        assertEquals(jid, client.getJid());
        assertNotNull(client.getCredentials());

        assertEquals(jid, client.getCredentials().getJid());
        assertEquals(password, client.getCredentials().getPassword());
        assertEquals(privateKey, client.getCredentials().getPrivateKey());
        assertEquals(publicKey, client.getCredentials().getPublicKey());

        assertEquals(publicKey, client.getKeyPair().getPublicKey());
        assertEquals(privateKey, client.getKeyPair().getPrivateKey());

        final String newEndpoint = "https://www.trustcase.com/api2";
        final String newJid = "99999999999999@trustcase.com";
        final String newPrivateKey = "2a8c1568c54c7a322506ad52551502513d27cee721f7e19dc61e9a8f989fc3de";
        final String newPublicKey = "d448811d996643fde148478db344e1a9e8b4cff1622ee5085f393d1f8a1b5962";
        final String newPassword = "d18d784d29ae42e1422d2089d6235953";
        client = TrustCase.newBuilder().properties(props).apiEndpoint(newEndpoint).jid(newJid).password(newPassword)
                .privateKey(newPrivateKey).publicKey(newPublicKey).build();

        assertNotNull(client);
        assertEquals(newEndpoint, client.getApiEndpoint());
        assertEquals(newJid, client.getJid());
        assertNotNull(client.getCredentials());

        assertEquals(newJid, client.getCredentials().getJid());
        assertEquals(newPassword, client.getCredentials().getPassword());
        assertEquals(newPrivateKey, client.getCredentials().getPrivateKey());
        assertEquals(newPublicKey, client.getCredentials().getPublicKey());

        assertEquals(newPublicKey, client.getKeyPair().getPublicKey());
        assertEquals(newPrivateKey, client.getKeyPair().getPrivateKey());
    }

    /**
     * test builing of client with incomplete credentials
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuilderWithIllegalArgs() {
        TrustCase.newBuilder().jid("123817298179387293@trustcase.com").build();
    }

}
