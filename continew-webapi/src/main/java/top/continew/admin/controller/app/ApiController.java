package top.continew.admin.controller.app;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.log.annotation.Log;
import top.continew.starter.web.model.R;

@Tag(name = "APP API")
@Log(module = "APP")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public R<?> index() {
        return R.ok("good");
    }

    @GetMapping("/status")
    public R<?> status() {
        return R.ok("status: good");
    }
}
