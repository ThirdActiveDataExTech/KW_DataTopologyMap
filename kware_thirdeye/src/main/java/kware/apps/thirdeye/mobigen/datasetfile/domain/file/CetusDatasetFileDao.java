package kware.apps.thirdeye.mobigen.datasetfile.domain.file;

import cetus.dao.SuperDao;
import kware.apps.mobigen.mobigen.dto.response.rawdata.RawdataListItemResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.DeleteDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFile;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchDatasetFilePage;
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

    public List<CetusDatasetFileView> getDataFile(SearchDatasetFile search) {
        return selectList("getDataFile", search);
    }

    public List<RawdataListItemResponse> getDataFilePage(SearchDatasetFilePage search) {
        return selectList("getDataFilePage", search);
    }

    public int getDataFilePageCount(SearchDatasetFilePage search) {
        return selectOne("getDataFilePageCount", search);
    }

    public CetusDatasetFileView getRawdataFileView(SearchDatasetFileView search) {
        return selectOne("getRawdataFileView", search);
    }

    public void deleteFile(DeleteDatasetFile request) {
        update("deleteFile", request);
    }
}
