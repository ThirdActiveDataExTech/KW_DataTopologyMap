package kware.apps.manager.cetus.user.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.user.dto.request.UserChange;
import kware.apps.manager.cetus.user.dto.request.UserExcelSearch;
import kware.apps.manager.cetus.user.dto.request.UserListSearch;
import kware.apps.manager.cetus.user.dto.request.UserSave;
import kware.apps.manager.cetus.user.dto.response.UserExcelPage;
import kware.apps.manager.cetus.user.dto.response.UserList;
import kware.common.excel.ExcelRender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(value="/cetus/api/user")
@RequiredArgsConstructor
public class CetusUserRestController {

    private final CetusUserService service;

    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid UserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeUser(@PathVariable("uid") Long uid, @RequestBody @Valid UserChange request) {
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/excel", produces = "application/vnd.ms-excel")
    public void renderUserExcelPage(@Valid UserExcelSearch search, HttpServletResponse res) throws IOException {
        Pageable pageable = new Pageable(1000);
        Page<UserExcelPage> paging = service.excelPage(search, pageable);
        ExcelRender excel = new ExcelRender(UserExcelPage.class);
        for (int i = 0; i < paging.getTotalPages(); i++) {
            pageable.setPageNumber(i + 1);
            excel.renderExcel(service.excelPage(search, pageable).getList());
        }
        excel.writeWorkbook(res);
    }

    @GetMapping
    public ResponseEntity userPage(@Valid UserListSearch search, Pageable pageable) {
        Page<UserList> page = service.userPage(search, pageable);
        return ResponseEntity.ok(page);
    }
}
