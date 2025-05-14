package kware.apps.manager.cetus.bbsctt.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.bbsctt.domain.CetusBbscttDao;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttSearch;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusBbscttService {

    private final CetusBbscttDao dao;

    @Transactional(readOnly = true)
    public Page<BbscttList> findAllBbscttPage(BbscttSearch search, Pageable pageable) {
        return dao.page("bbscttList", "bbscttCount", search, pageable);
    }
}
