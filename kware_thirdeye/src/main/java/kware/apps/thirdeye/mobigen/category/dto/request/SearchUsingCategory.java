package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     SearchUsingCategory
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      사용중인 카테고리 목록 조회를 위한 응답 dto
 *              ""
 *              kware 포탈 시스템에서 진열관리 중인 데이터셋 목록 중에서
 *              (a) 현재 [show] 상태이며, [삭제되지 않은] 데이터셋 목록으로 필터링하고
 *              (b) 데이터셋에 대한 화면 UI 정보가 {mainUiUid}인 정보들로만 필터링한다
 *              =>> 이렇게 필터링된 데이터셋들의 카테고리 목록만 조회해온다.
 *              ""
**/

@Getter @NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class SearchUsingCategory {

    private Long mainUiUid;
    private Long workplaceUid;
    private Integer limit;

    public SearchUsingCategory(Long mainUiUid, Long workplaceUid, Integer limit) {
        this.mainUiUid = mainUiUid;
        this.workplaceUid = workplaceUid;
        this.limit = limit;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
