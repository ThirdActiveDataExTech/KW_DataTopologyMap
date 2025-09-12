package kware.apps.thirdeye.dataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.dataset.domain.CetusDatasetDao;
import kware.apps.thirdeye.dataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.dataset.dto.response.DataSetView;
import kware.apps.thirdeye.dataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

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

    public Page<DatasetList> findDatasetPage( DatasetSearch search, Pageable pageable ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<DatasetList> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);

        // json 읽기 ( todo : 추후 api 호출 )
        if(page.getList() != null && !page.getList().isEmpty()) {
            ClassPathResource resource = new ClassPathResource("static/assets/data/testdata/dataset_list.json");
            List<DataSetView> dataList = new ArrayList<>();
            try {
                byte[] jsonData = Files.readAllBytes(resource.getFile().toPath());
                ObjectMapper objectMapper = new ObjectMapper();
                dataList = objectMapper.readValue(jsonData, new TypeReference<List<DataSetView>>() {});
            } catch (IOException e) {
                throw new RuntimeException("데이터셋 JSON 파일 읽기 실패", e);
            }

            // datasetId -> DataSetView 매핑 빠르게 찾기 위해 Map 변환
            Map<Long, DataSetView> dataMap = dataList.stream().collect(Collectors.toMap(DataSetView::getDatasetId, d -> d));

            // page 결과에 매핑
            page.getList().forEach(dataset -> {
                Long datasetId = dataset.getDatasetId();
                DataSetView match = dataMap.get(datasetId);
                if (match != null) {
                    dataset.setDatasetInfo( match.getTitle(), match.getDescription(), match.getMetadata() );
                }
            });
        }
        return page;
    }
}
