package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
* @fileName     SearchApprovedDataset
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-14
* @summary
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class SearchApprovedDataset {

    private Long workplaceUid;
    private Long userUid;
    private String showAt;
    private String uiTypeCd;
    private String dataTarType;
    private String startDate;
    private String endDate;
    private String keyword;
    private List<Long> categories;  // 카테고리 검색

    public SearchApprovedDataset(Long workplaceUid, Long userUid,
                                 String showAt, String uiTypeCd, String dataTarType,
                                 String startDate, String endDate,
                                 String keyword, List<Long> categories ) {
        this.workplaceUid = workplaceUid;
        this.userUid = userUid;
        this.showAt = showAt;
        this.uiTypeCd = uiTypeCd;
        this.dataTarType = dataTarType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = normalizeKeyword(keyword);
        this.categories = categories;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
    public void setUserUid(Long userUid) {
        this.userUid = userUid;
    }

    /** 검색어는 앞뒤 공백을 지우고, 비어 있으면 조건을 걸지 않도록 null 로 둔다 */
    public void setKeyword(String keyword) {
        this.keyword = normalizeKeyword(keyword);
    }

    static String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        String trimmed = keyword.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
