package df.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class IndexController {

    @GetMapping(
            value = "/favicon.ico",
            produces = "image/x-icon")
    @ResponseBody
    public byte[] favicon() throws IOException {
        return StreamUtils.copyToByteArray(getClass()
                .getResourceAsStream("/static/assets/images/favicon.ico"));
    }

}
