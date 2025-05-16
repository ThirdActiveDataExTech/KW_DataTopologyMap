package kware.apps.manager.cetus.downloadshist.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.downloadshist.domain.CetusDownloadsHist;
import kware.apps.manager.cetus.downloadshist.domain.CetusDownloadsHistDao;
import kware.apps.manager.cetus.downloadshist.dto.request.DownloadsHistSearch;
import kware.apps.manager.cetus.downloadshist.dto.response.DownloadsHistList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusDownloadsHistService {

    private final CetusDownloadsHistDao dao;

    @Transactional
    public void saveDownloadsHistUser(String targetCd, Long fileUid) {
        dao.insertDownloadsHist(new CetusDownloadsHist(targetCd, null, fileUid, UserUtil.getUser().getUid()));
    }

    @Transactional(readOnly = true)
    public Page<DownloadsHistList> findAllUserDownloadsHistPage(DownloadsHistSearch search, Pageable pageable) {
        search.setUserUid(UserUtil.getUser().getUid());
        return dao.page("downloadsHistPageList", "downloadsHistPageListCount", search, pageable);
    }

    @Transactional
    public void deleteDownloadHistUser(Long fileUid) {
        dao.deleteDownloadHistUser(fileUid);
    }
}
