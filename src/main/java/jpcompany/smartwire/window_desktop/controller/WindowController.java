package jpcompany.smartwire.window_desktop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WindowController {

    @PostMapping("/api")
    public String post(@RequestBody String UserId) {
        log.info("api 전송받음={}", UserId);
        return "ok";
    }
}
