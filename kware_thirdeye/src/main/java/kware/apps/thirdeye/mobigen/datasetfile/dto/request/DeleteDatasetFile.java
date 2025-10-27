package kware.apps.thirdeye.mobigen.datasetfile.dto.request;

import kware.apps.thirdeye.mobigen.datasetfile.enumcd.DataFileTpCd;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteDatasetFile {

    private String metadataId;
    private String rawdataId;
    private String dataTpCd;

    public DeleteDatasetFile(String dataTpCd) {
        this.dataTpCd = dataTpCd;
    }

    public static DeleteDatasetFile deleteMetadata(String metadataId) {
        DeleteDatasetFile bean = new DeleteDatasetFile(DataFileTpCd.METADATA.name());
        bean.setMetadataId(metadataId);
        return bean;
    }

    public static DeleteDatasetFile deleteRawdata(String rawdataId) {
        DeleteDatasetFile bean = new DeleteDatasetFile(DataFileTpCd.RAWDATA.name());
        bean.setRawdataId(rawdataId);
        return bean;
    }

}
