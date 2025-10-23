package kware.apps.thirdeye.mobigen.datasetfile.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFileView;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileList;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDatasetFileDao extends SuperDao<CetusDatasetFile> {

    public CetusDatasetFileDao() {
        super("cetusDatasetFile");
    }

    public Long key() {
        return selectOne("key");
    }

    public CetusDatasetFile getFileInfoByFileId(String fileId) {
        return selectOne("getFileInfoByFileId", fileId);
    }

    public void increaseDownCnt(CetusDatasetFile bean) {
        update("increaseDownCnt", bean);
    }

    public List<CetusDatasetFileList> getFileList(CetusDatasetFile bean) {
        return selectList("getFileList", bean);
    }

    public void deleteFile(CetusDatasetFile bean) {
        update("deleteFile", bean);
    }

    public List<CetusDatasetFileView> getDataFile(SearchDatasetFile search) {
        return selectList("getDataFile", search);
    }

    public CetusDatasetFileView getRawdataFileView(SearchDatasetFileView search) {
        return selectOne("getRawdataFileView", search);
    }
}
