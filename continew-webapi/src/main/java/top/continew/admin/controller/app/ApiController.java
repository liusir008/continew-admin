package top.continew.admin.controller.app;

import cn.dev33.satoken.annotation.SaIgnore;
import io.agora.media.RtcTokenBuilder2;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.config.agora.AgoraProperties;
import top.continew.starter.log.annotation.Log;
import top.continew.starter.web.model.R;

@Tag(name = "APP API")
@Log(module = "APP")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final AgoraProperties agoraProperties;

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public R<?> index() {
        String appId = agoraProperties.getAppId();
        System.out.println("appId:" + appId);
        String token = buildRtcToken("liusir");
        return R.ok(token);
    }

    @GetMapping("/status")
    public R<?> status() {
        return R.ok("status: good");
    }

    private String buildRtcToken(String channelName) {
        String appId = "d3cd1a040246414bbd6e5b634264906c";
        String appCertificate = "84a781d0fdd84cbbb30b3d33ba877e6d";

        int uid = 0;
        String account = "720605616673915359"; // 720605133691421149

        int tokenExpirationInSeconds = 3600;
        int privilegeExpirationInSeconds = 3600;
        int joinChannelPrivilegeExpireInSeconds = 3600;
        int pubAudioPrivilegeExpireInSeconds = 3600;
        int pubVideoPrivilegeExpireInSeconds = 3600;
        int pubDataStreamPrivilegeExpireInSeconds = 3600;

//        SimpleTokenBuilder tokenBuilder = new SimpleTokenBuilder(agoraConfig.getAppId(), agoraConfig.getAppCertificate(), channelName, 0);
//        tokenBuilder.initPrivileges(SimpleTokenBuilder.Role.Role_Attendee);
//        tokenBuilder.setPrivilege(AccessToken.Privileges.kJoinChannel, expireTimestamp);
//        tokenBuilder.setPrivilege(AccessToken.Privileges.kPublishVideoStream, expireTimestamp);
//        return tokenBuilder.buildToken();

        RtcTokenBuilder2 token = new RtcTokenBuilder2();
//        String result =
//                token.buildTokenWithUid(appId, appCertificate, channelName, uid, RtcTokenBuilder2.Role.ROLE_PUBLISHER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
//        System.out.printf("Token with uid: %s\n", result);

        String result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, RtcTokenBuilder2.Role.ROLE_PUBLISHER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
        System.out.printf("Token with account: %s\n", result);

//        result = token.buildTokenWithUid(appId, appCertificate, channelName, uid, tokenExpirationInSeconds, joinChannelPrivilegeExpireInSeconds,
//                pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds);
//        System.out.printf("Token with uid and privilege: %s\n", result);

//        result = token.buildTokenWithUserAccount(appId, appCertificate, channelName, account, tokenExpirationInSeconds, joinChannelPrivilegeExpireInSeconds,
//                pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds);
//        System.out.printf("Token with account and privilege: %s\n", result);

//        result = token.buildTokenWithRtm(appId, appCertificate, channelName, account, RtcTokenBuilder2.Role.ROLE_PUBLISHER, tokenExpirationInSeconds,
//                privilegeExpirationInSeconds);
//        System.out.printf("Token with RTM: %s\n", result);
//
//        result = token.buildTokenWithRtm2(appId, appCertificate, channelName, account, RtcTokenBuilder2.Role.ROLE_PUBLISHER, tokenExpirationInSeconds,
//                joinChannelPrivilegeExpireInSeconds, pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds,
//                account, tokenExpirationInSeconds);
//        System.out.printf("Token with RTM: %s\n", result);

        return result;
    }
}
