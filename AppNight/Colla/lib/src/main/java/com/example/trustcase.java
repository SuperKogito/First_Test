package com.example;

import com.trustcase.client.TrustCase;
import com.trustcase.client.TrustCaseClient;
import com.trustcase.client.api.enums.RoomIcon;
import com.trustcase.client.api.enums.TrustCaseRole;
import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.messages.RoomMetadataMessage;
import com.trustcase.client.api.responses.Identity;
import com.trustcase.client.api.responses.OpenRoomResponse;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class trustcase {

    public static void main(String[] args) throws TrustCaseClientException, NoSuchAlgorithmException {

// This is base url of trustcase REST API.
        final String endpoint = "https://api.trustcase.com/api/v0.6";
        final RoomIcon roomIcon = RoomIcon.BUILDING_1;
        final String name = "Klasse 7a";
        // Mein Zugang/Lehrer
        final String jid = "AJ6E5HJT";
        final String participant_jid = "VFL45TZU";
        final String privateKey = "9d765b99c710ca060349285ece7f6a2282ef6e344b1633958458d919f6392c12";
        final String publicKey = "e2178e6433b04730d05ac80c1f1c552d44c45b74a82b5654356e88831f65e024";
        final String password = "6abbec7c23de4b14a899f94dba287212";

        TrustCaseClient client = TrustCase.newBuilder().apiEndpoint(endpoint).jid(jid).privateKey(privateKey)
                .publicKey(publicKey).password(password).build();

        //Nutzer Identitäten
        Identity identity = new Identity("9NAFSAA2", "7594ada9a2429d54597c2809dc3dbb6ebbfb0a0821bb254111c4898e3748d27c", TrustCaseRole.MOBILE_USER);
        Identity identity1 = new Identity("4EZ4BCDA", "ee0317eb09edf9cfc4f9e485c25cdfa4d401cb259052cbd05d86bb46cb7ccf54", TrustCaseRole.MOBILE_USER);
        Identity identity2 = new Identity("XS9ENJJR", "6949f17709622a5bc910ba1f5104e51a8fa6772605f0edf9f06a9b3eaeb2bd7a", TrustCaseRole.MOBILE_USER);
        ArrayList< Identity > participants = new ArrayList < Identity > ();
        ArrayList < String > administratorJIDs = new ArrayList < String > ();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecretKey key = keyGen.generateKey();
        administratorJIDs.add("8c88390038db4b1ca7066990fd99abc5@rooms.f24.com");
        participants.add(identity);
        participants.add(identity1);
        participants.add(identity2);
        OpenRoomResponse Klasse7a = client.openRoom(name, roomIcon, key.toString(), participants, administratorJIDs);
        //client.openRoom(new RoomMetadataMessage())


    }
}
