package top.continew.admin.config.agora;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "agora")
public class AgoraProperties {

    private String appId;
    private String appCertificate;

    private String clientId;
    private String clientSecret;
}
