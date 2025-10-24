package kware.apps.thirdeye.mobigen.datasetfile.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDatasetFilePage {

    private String metadataId;
    private String rawdataId;
    private String dataTpCd;

    private int offset;
    private int limit;

    public SearchDatasetFilePage( String metadataId, String rawdataId, String dataTpCd,
                                  int pageNumber, int size ) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
        this.dataTpCd = dataTpCd;
        this.offset = (pageNumber - 1) * size;
        this.limit = size;
    }
}
