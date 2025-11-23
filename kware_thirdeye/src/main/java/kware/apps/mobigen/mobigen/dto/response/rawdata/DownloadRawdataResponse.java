package kware.apps.mobigen.mobigen.dto.response.rawdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @fileName     DownloadMetadataResponse
 * @author       dahyeon
 * @version      1.0.0
 * @date         2025-09-23
 * @summary      [RAWDATA_07] 특정 원본데이터 파일 정보 다운로드
 **/

@Getter @Setter @ToString
public class DownloadRawdataResponse {
    private String rawdata_id;
}
