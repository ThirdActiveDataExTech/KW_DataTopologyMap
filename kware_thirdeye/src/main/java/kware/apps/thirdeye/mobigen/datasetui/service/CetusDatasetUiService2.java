package kware.apps.thirdeye.mobigen.datasetui.service;

import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUi;
import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUiDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusDatasetUiService2 {

    private final CetusDatasetUiDao dao;

    @Transactional
    public void changeDatasetCategory(Long datasetUi, Long categoryUid) {
        CetusDatasetUi bean = new CetusDatasetUi(datasetUi, categoryUid);
        dao.updateDatasetCategory(bean);
    }

}
