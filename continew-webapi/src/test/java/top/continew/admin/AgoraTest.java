package top.continew.admin;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;
import io.agora.rtm.RtmTokenBuilder2;
import org.junit.jupiter.api.Test;

public class AgoraTest {

    // Need to set environment variable AGORA_APP_ID
    static String appId = "d3cd1a040246414bbd6e5b634264906c";
    // Need to set environment variable AGORA_APP_CERTIFICATE
    static String appCertificate = "84a781d0fdd84cbbb30b3d33ba877e6d";

    static String channelName = "63947";
    static String account = "720605616673915359";
    static int uid = 63947;
    static int uidCallee = 94397;
    static int tokenExpirationInSeconds = 3600;
    static int privilegeExpirationInSeconds = 3600;
    static int joinChannelPrivilegeExpireInSeconds = 3600;
    static int pubAudioPrivilegeExpireInSeconds = 3600;
    static int pubVideoPrivilegeExpireInSeconds = 3600;
    static int pubDataStreamPrivilegeExpireInSeconds = 3600;

    @Test
    public void generateToken() {
        System.out.printf("App Id: %s\n", appId);
        System.out.printf("App Certificate: %s\n", appCertificate);
        if (appId == null || appId.isEmpty() || appCertificate == null || appCertificate.isEmpty()) {
            System.out.printf("Need to set environment variable AGORA_APP_ID and AGORA_APP_CERTIFICATE\n");
            return;
        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR, 2);
//        int expireTimestamp = (int) (calendar.getTimeInMillis() / 1000);

//        RtcTokenBuilder token = new RtcTokenBuilder();
//        String result = token.buildTokenWithUid(appId, appCertificate, channelName, uid, RtcTokenBuilder.Role.Role_Publisher, expireTimestamp);
//        System.out.printf("Token1 with uid %d: %s\n", uid, result);
//        result = token.buildTokenWithUid(appId, appCertificate, channelName, uidCallee, RtcTokenBuilder.Role.Role_Publisher, expireTimestamp);
//        System.out.printf("Token1 with uid %d: %s\n", uidCallee, result);

        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result =
                token.buildTokenWithUid(appId, appCertificate, channelName, uid, Role.ROLE_PUBLISHER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.printf("Token with uid %d: %s\n", uid, result);
//        result =
//                token.buildTokenWithUid(appId, appCertificate, channelName, uidCallee, Role.ROLE_PUBLISHER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
//        System.out.printf("Token with uid %d: %s\n", uidCallee, result);

        RtmTokenBuilder2 rtmTokenBuilder2 = new RtmTokenBuilder2();
        result = rtmTokenBuilder2.buildToken(appId, appCertificate, String.valueOf(uid), tokenExpirationInSeconds);
        System.out.printf("RTM Token with uid %d: %s\n", uid, result);

//        result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, Role.ROLE_PUBLISHER, tokenExpirationInSeconds,
//                privilegeExpirationInSeconds);
//        System.out.printf("Token with account: %s\n", result);
//
//        result = token.buildTokenWithUid(appId, appCertificate, channelName, uid, tokenExpirationInSeconds, joinChannelPrivilegeExpireInSeconds,
//                pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds);
//        System.out.printf("Token with uid and privilege: %s\n", result);
//
//        result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, tokenExpirationInSeconds, joinChannelPrivilegeExpireInSeconds,
//                pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds);
//        System.out.printf("Token with account and privilege: %s\n", result);
//
//        result = token.buildTokenWithRtm(appId, appCertificate, channelName, account, Role.ROLE_PUBLISHER, tokenExpirationInSeconds,
//                privilegeExpirationInSeconds);
//        System.out.printf("Token with RTM: %s\n", result);
//
//        result = token.buildTokenWithRtm2(appId, appCertificate, channelName, account, Role.ROLE_PUBLISHER, tokenExpirationInSeconds,
//                joinChannelPrivilegeExpireInSeconds, pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds,
//                account, tokenExpirationInSeconds);
//        System.out.printf("Token with RTM: %s\n", result);
    }
}
