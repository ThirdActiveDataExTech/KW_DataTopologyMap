package kware.apps.thirdeye.bbsctt.dto.request;


import lombok.Getter;

@Getter
public class BbscttExcelSearch {

    private Long bbsUid;
    private Long regUid;
    private String openAt;
    private String bbscttUrlPrefix;

    public BbscttExcelSearch(Long bbsUid, Long regUid, String openAt, String baseUrl) {
        this.bbsUid = bbsUid;
        this.regUid = regUid;
        this.openAt = openAt;
        this.bbscttUrlPrefix = baseUrl;
    }

    public void setBaseUrl(String baseUrl, String subCode) {
        this.bbscttUrlPrefix = baseUrl.replaceAll("/+$", "") + "/portal/bbs/" + subCode + "/";
    }
}
