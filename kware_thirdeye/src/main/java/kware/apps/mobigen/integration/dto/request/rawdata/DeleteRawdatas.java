package kware.apps.mobigen.integration.dto.request.rawdata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     DeleteRawdatas
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [RAWDATA_03] 여러건의 원본데이터 삭제
 *              => 해당 원본데이터파일에 대해서 kware 포탈 시스템에서도 삭제
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteRawdatas {
    private String metadataId;
    private List<String> rawdataIds;
}
