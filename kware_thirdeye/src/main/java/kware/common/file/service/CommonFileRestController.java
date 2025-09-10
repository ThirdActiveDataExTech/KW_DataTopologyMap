package kware.common.file.service;

import cetus.Response;
import cetus.user.UserUtil;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.file.domain.CommonFile;
import kware.common.file.tus.util.SimpleJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/files")
public class CommonFileRestController {
    
    private final CommonFileService fileService;

    @GetMapping({"view"})
    public ResponseEntity<Resource> view(final HttpServletRequest req, final HttpServletResponse res) {
        return fileService.fileView(req);
    }

    @GetMapping({"download"})
    public ResponseEntity<Resource> download(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        return fileService.download(req);
    }

    @GetMapping({"download2"})
    public ResponseEntity<Resource> download2(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        return fileService.download2(req);
    }

    @GetMapping("/check-file")
    public ResponseEntity<Boolean> checkFile(final HttpServletRequest req, final HttpServletResponse res) {
        return ResponseEntity.ok(fileService.checkFile(req));
    }

    /**
     * 저장된 파일 목록
     * @param bean
     * @return
     */
    @GetMapping("list")
    public Response list(CommonFile bean) {
        return Response.ok(fileService.list(bean));
    }

    /**
     * jwt token으로 대용량파일 관련 보안처리용
     */
    @GetMapping("bigfile/jwttoken")
    public Response jwttoken(HttpServletRequest req) {

        SessionUserInfo user = UserUtil.getUser(req);

        Long userUid = user.getUid();
        Map<String,Object> claimsMap = new HashMap<String,Object>();
        claimsMap.put("userUid", userUid.toString());
        //endpointUid는 임시로 사용함, 기존로직을 수정하면서 임시로 처리함: 지우면 오류남.
        claimsMap.put("endpointUid", (new Long(System.currentTimeMillis())).toString());

        //subject 이외의 정보를 넣기 위함
        String webToken = SimpleJwtUtil.getJwtToken(claimsMap);
        claimsMap.clear();
        claimsMap = null;
        //하나의 subject로 처리할 경우
        //String webToken = SimpleJwtUtil.getJwtToken(userUid);

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("jwtToken", webToken);

        return Response.ok(retMap);
    }
}
