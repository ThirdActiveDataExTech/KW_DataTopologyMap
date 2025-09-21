package kware.apps.thirdeye.datasethistory.service;


import kware.apps.thirdeye.datasethistory.domain.CetusDatasetHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CetusDatasetHistoryService {

    private final CetusDatasetHistoryDao dao;
}
