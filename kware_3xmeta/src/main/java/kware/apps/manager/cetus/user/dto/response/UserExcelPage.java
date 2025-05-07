package kware.apps.manager.cetus.user.dto.response;

import cetus.annotation.ExcelColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExcelPage {

    @ExcelColumn(headerName = "사용자 ID")
    private String userId;
    @ExcelColumn(headerName = "사용자 이름")
    private String userNm;
    @ExcelColumn(headerName = "사용자 전화번호")
    private String userTel;
    @ExcelColumn(headerName = "사용자 이메일")
    private String userEmail;
    @ExcelColumn(headerName = "사용자 승인여부")
    private String approveAt;
    @ExcelColumn(headerName = "사용자 사용여부")
    private String useAt;
    @ExcelColumn(headerName = "사용자 권한")
    private String roleNm;
}
