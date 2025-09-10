package kware.apps.system.filelog.service;

import kware.apps.thirdeye.enumstatus.DownloadTargetCd;
import kware.apps.system.filelog.domain.CetusFileLogDao;
import kware.apps.system.filelog.dto.request.FileLogExcelSearch;
import kware.apps.system.filelog.dto.response.FileLogExcelList;
import kware.common.excel.ExcelCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CetusFileLogExcelService {

    private final ExcelCreate excelCreate;
    private final CetusFileLogDao dao;

    @Async
    public void renderEXCEL(FileLogExcelSearch search) {
        excelCreate.createExcelFile(
                FileLogExcelList.class,
                p -> dao.fileLogExcelPage(search, p),
                DownloadTargetCd.DOWNLOAD_HIST
        );
    }
}
