package kware.apps.manager.cetus.user.dto.request;

import cetus.annotation.YOrN;
import lombok.Getter;


@Getter
public class UserExcelSearch {

    private String browseText;

    @YOrN
    private String approveAt;

    public UserExcelSearch(String browseText, String approveAt) {
        this.browseText = browseText;
        this.approveAt = approveAt;
    }

}