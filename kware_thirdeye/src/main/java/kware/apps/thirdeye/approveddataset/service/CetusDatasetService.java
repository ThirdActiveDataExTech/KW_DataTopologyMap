package kware.apps.thirdeye.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.approveddataset.domain.CetusDatasetDao;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetViewSearch;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetDetailView;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetList;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetView;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CetusDatasetService {

    private final CetusDatasetDao dao;

    /**
     * @method      findDatasetPage
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 승인된 데이터셋 목록 조회 + 페이징
    **/
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

    /**
     * @method      findDatasetByUid
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 승인된 데이터셋 단건 조회
    **/
    @Transactional(readOnly = true)
    public DatasetDetailView findDatasetByUid(Long approvedUid) {

        // 1. 데이터셋 ID 정보 조회
        DatasetViewSearch search = new DatasetViewSearch(approvedUid, UserUtil.getUser().getUid());
        DatasetDetailView dataset = dao.getDatasetByUid(search);
        if(dataset == null) return null;

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
        ClassPathResource resource = new ClassPathResource("static/testdata/dataset_list.json");
        List<DatasetView> dataList = new ArrayList<>();
        try (InputStream in = resource.getInputStream()) {  // 여기 수정
            ObjectMapper objectMapper = new ObjectMapper();
            dataList = objectMapper.readValue(in, new TypeReference<List<DatasetView>>() {});
        } catch (IOException e) {
            throw new RuntimeException("데이터셋 JSON 파일 읽기 실패", e);
        }

        // datasetId -> DatasetView 매핑
        return dataList.stream().collect(Collectors.toMap(DatasetView::getDatasetId, d -> d));
    }
}
