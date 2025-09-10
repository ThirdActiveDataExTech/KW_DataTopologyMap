package kware.apps.system.user.service;


import cetus.Response;
import kware.apps.system.user.dto.request.UserSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/thirdeye/user")
public class CetusThirdEyeUserRestController {

    private final CetusUserService service;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @GetMapping("/checkEmail")
    public Response checkEmail(@RequestParam("userEmail") String email) {
        String message = "";
        isValidEmail(email);
        int findUserCnt = service.findByEmail(email);

        if (findUserCnt == 1)  message = "이미 등록된 이메일입니다. 다른 이메일을 입력해주세요";
        else if (findUserCnt == 0) message = "사용 가능한 이메일 입니다.";
        else message = "올바르지 않은 이메일 입니다.";

        if (!isValidEmail(email)) {
            findUserCnt = 2;
            message = "유효한 이메일 형식이 아닙니다.";
        }
        return Response.ok(findUserCnt, message);
    }

    @GetMapping("/checkId")
    public Response checkId(@RequestParam("userId") String userId) {
        String message = "";
        Integer findUserCnt = service.findByUserId(userId);
        if(findUserCnt == 1) message = "이미 등록된 아이디입니다. 다른 아이디를 입력해주세요";
        else if(findUserCnt == 0) message = "사용 가능한 아이디 입니다.";

        return Response.ok(findUserCnt, message);
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid UserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }
}
