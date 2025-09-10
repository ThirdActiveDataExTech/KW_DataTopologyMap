package kware.apps.system.filelog.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.system.filelog.dto.request.FileLogExcelSearch;
import kware.apps.system.filelog.dto.response.FileLogExcelList;
import org.springframework.stereotype.Component;

@Component
public class CetusFileLogDao extends SuperDao<CetusFileLog> {

    public CetusFileLogDao() {
        super("cetusFileLog");
    }

    public Page<FileLogExcelList> fileLogExcelPage(FileLogExcelSearch search, Pageable pageable) {
        return page("fileLogExcelList", "fileLogExcelCount", search, pageable);
    }
}
