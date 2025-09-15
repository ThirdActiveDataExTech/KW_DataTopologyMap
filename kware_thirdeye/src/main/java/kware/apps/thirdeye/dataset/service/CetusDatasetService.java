package kware.apps.thirdeye.dataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.dataset.domain.CetusDatasetDao;
import kware.apps.thirdeye.dataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.dataset.dto.request.DatasetViewSearch;
import kware.apps.thirdeye.dataset.dto.response.DatasetDetailView;
import kware.apps.thirdeye.dataset.dto.response.DatasetView;
import kware.apps.thirdeye.dataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CetusDatasetService {

    private final CetusDatasetDao dao;

    @Transactional(readOnly = true)
    public Page<DatasetList> findDatasetPage( DatasetSearch search, Pageable pageable ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<DatasetList> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);

        if(page.getList() != null && !page.getList().isEmpty()) {
            Map<Long, DatasetView> dataMap = this.findDatasetDetailByAPI();
            page.getList().forEach(dataset -> {
                Long datasetId = dataset.getDatasetId();
                DatasetView match = dataMap.get(datasetId);
                if (match != null) {
                    dataset.setDatasetInfo( match.getTitle(), match.getDescription(), match.getMetadata() );
                }
            });
        }
        return page;
    }

    @Transactional(readOnly = true)
    public DatasetDetailView findDatasetByUid(Long approvedUid) {

        // 1. 데이터셋 ID 정보 조회
        DatasetViewSearch search = new DatasetViewSearch(approvedUid, UserUtil.getUser().getUid());
        DatasetDetailView dataset = dao.getDatasetByUid(search);

        // 2. 데이터셋 ID 값을 통해 상세 정보 API 조회
        Map<Long, DatasetView> dataMap = this.findDatasetDetailByAPI();
        Long datasetId = dataset.getDatasetId();
        DatasetView match = dataMap.get(datasetId);
        if (match != null) {
            dataset.setDatasetInfo( match.getTitle(), match.getDescription(), match.getMetadata() );
        }

        return dataset;
    }
    
    // todo 추후 API 호출로 변경
    public Map<Long, DatasetView> findDatasetDetailByAPI() {
        ClassPathResource resource = new ClassPathResource("static/assets/data/testdata/dataset_list.json");
        List<DatasetView> dataList = new ArrayList<>();
        try {
            byte[] jsonData = Files.readAllBytes(resource.getFile().toPath());
            ObjectMapper objectMapper = new ObjectMapper();
            dataList = objectMapper.readValue(jsonData, new TypeReference<List<DatasetView>>() {});
        } catch (IOException e) {
            throw new RuntimeException("데이터셋 JSON 파일 읽기 실패", e);
        }

        // datasetId -> DatasetView 매핑 빠르게 찾기 위해 Map 변환
        return dataList.stream().collect(Collectors.toMap(DatasetView::getDatasetId, d -> d));
    }
}
