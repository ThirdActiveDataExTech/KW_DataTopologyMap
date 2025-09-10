package kware.apps.system.loginhist.dto.request;

import lombok.Getter;

@Getter
public class UserLoginHistExcelSearch {

    private Long userUid;

    public UserLoginHistExcelSearch(Long userUid) {
        this.userUid = userUid;
    }
}
