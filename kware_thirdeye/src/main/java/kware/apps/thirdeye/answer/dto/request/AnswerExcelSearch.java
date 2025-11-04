package kware.apps.thirdeye.answer.dto.request;

import lombok.Getter;

@Getter
public class AnswerExcelSearch {

    private Long bbsUid;
    private Long regUid;
    private String bbscttUrlPrefix;

    public AnswerExcelSearch(Long bbsUid, Long regUid, String bbscttUrlPrefix) {
        this.bbsUid = bbsUid;
        this.regUid = regUid;
        this.bbscttUrlPrefix = bbscttUrlPrefix;
    }

    public void setBaseUrl(String baseUrl, String subCode) {
        this.bbscttUrlPrefix = baseUrl.replaceAll("/+$", "") + "/portal/bbs/" + subCode + "/";
    }
}
