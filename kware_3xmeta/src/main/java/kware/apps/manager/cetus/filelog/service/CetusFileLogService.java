package kware.apps.manager.cetus.filelog.service;

import kware.apps.manager.cetus.filelog.domain.CetusFileLog;
import kware.apps.manager.cetus.filelog.domain.CetusFileLogDao;
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
}
