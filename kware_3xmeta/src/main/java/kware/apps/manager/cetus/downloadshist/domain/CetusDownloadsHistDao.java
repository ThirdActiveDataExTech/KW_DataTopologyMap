package kware.apps.manager.cetus.downloadshist.domain;


import cetus.dao.SuperDao;
import kware.apps.manager.cetus.downloadshist.dto.response.DownloadsHistList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDownloadsHistDao extends SuperDao<CetusDownloadsHist> {

    public CetusDownloadsHistDao() {
        super("cetusDownloadsHist");
    }

    public int insertDownloadsHist(CetusDownloadsHist bean) {
        return insert("insertDownloadsHist", bean);
    }

    public List<DownloadsHistList> getAllUserDownloadsHist(Long userUid) {
        return selectList("getAllUserDownloadsHist", userUid);
    }

    public int deleteDownloadHistUser(Long uid) {
        return delete("deleteDownloadHistUser", uid);
    }
}
