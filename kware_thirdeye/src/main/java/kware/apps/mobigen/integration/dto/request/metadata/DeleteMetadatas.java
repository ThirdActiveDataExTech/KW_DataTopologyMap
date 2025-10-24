package kware.apps.mobigen.integration.dto.request.metadata;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     DeleteMetadatas
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [METADATA_02] 여러건의 메타데이터 삭제 (물리 삭제)
 *              => 만일 해당 메타데이터가 KWARE 포탈 시스템에서 진열관리중인 메타데이터라면,
 *                 KWARE 포탈 시스템에서도 삭제한다 (논리 삭제)
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteMetadatas {
    private List<Long> uids;
}
