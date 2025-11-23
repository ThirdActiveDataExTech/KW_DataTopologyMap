package kware.apps.mobigen.mobigen.dto.response.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Map;

/**
 * @fileName     DownloadMetadataResponse
 * @author       dahyeon
 * @version      1.0.0
 * @date         2025-09-23
 * @summary      [METADATA_08] 특정 메타데이터 파일 정보 다운로드
 **/

@Getter @Setter @NoArgsConstructor @ToString
public class DownloadMetadataResponse {
    private String metadata_id;
    private String title;
    private String issued;
    private String modified;
    private String identifier;
    private String publisher;
    private String keywords;
    private String landing_page;
    private String theme;
    private String access_url;
    private String ingested_at;
    private String updated_at;
    private Map<String, Object> raw_metadata;
}
