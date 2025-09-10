package kware.apps.system.filelog.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.filelog.domain.CetusFileLog;
import kware.apps.system.filelog.domain.CetusFileLogDao;
import kware.apps.system.filelog.dto.request.FileLogSearch;
import kware.apps.system.filelog.dto.response.FileLogList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusFileLogService {

    private final CetusFileLogDao dao;

    @Transactional
    public void saveFileLog(CetusFileLog bean) {
        dao.insert(bean);
    }

    @Transactional(readOnly = true)
    public Page<FileLogList> findAllFileLogPage(FileLogSearch search, Pageable pageable) {
        return dao.page("fileLogList", "fileLogCount", search, pageable);
    }


}
