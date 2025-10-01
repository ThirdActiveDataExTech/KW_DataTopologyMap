package kware.apps.thirdeye.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.thirdeye.approveddataset.domain.CetusDataset;
import kware.apps.thirdeye.approveddataset.domain.CetusDatasetDao;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetViewSearch;
import kware.apps.thirdeye.approveddataset.dto.request.SaveDataset;
import kware.apps.thirdeye.approveddataset.dto.response.*;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.datasetui.service.CetusDatasetUiService;
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
    private final CetusDatasetUiService datasetUiService;
    private final CetusMobigenDatasetService mobigenDatasetService;

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
     * @method      approveDataset
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 모비젠 저장소에 저장된 데이터셋 진열등록
    **/
    @Transactional
    public void approveDataset(SaveDataset request) {

        // 1. 모비젠 데이터셋에서 가져온 데이터 정보 진열/승인
        CetusDataset bean = new CetusDataset(request, UserUtil.getUserWorkplaceUid(), UserUtil.getUser().getUid());
        dao.insert(bean);
        Long approvedDatasetUid = bean.getUid();

        // 2. 진열/승인된 데이터에 대한 UI 정보 저장
        datasetUiService.saveDatasetUi(approvedDatasetUid, request);
    }

    /**
     * @method      findApprovedDatasetIdList
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 모비젠 저장소에서 kware 포탈 시스템으로 진열등록된 데이터셋 ID 목록
    **/
    @Transactional(readOnly = true)
    public List<DatasetIdList> findApprovedDatasetIdList() {
        return dao.getApprovedDatasetIdList(UserUtil.getUserWorkplaceUid());
    }

    /**
     * @method      findDatasetByUid
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 진열등록된 데이터셋 단건 조회
     *              TODO 추후 삭제
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
    
    /**
     * @method      findApprovedDatasetView
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대한 상세 정보 조회
     *              => 해당 데이터셋의 UI 정보도 함께 조회한다.
     *              => 해당 데이터셋에 대해서 모비젠 저장소에 저장된 메타데잍터 정보도 조회한다.
    **/
    @Transactional(readOnly = true)
    public ApprovedDatasetView findApprovedDatasetView(Long uid) {
        ApprovedDatasetView approvedDatasetView = dao.getApprovedDatasetView(uid);

        DatasetUiView uiView = datasetUiService.findDatasetUiView(uid);
        approvedDatasetView.setUiView(uiView);

        MobigenDatasetView mobigenDatasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(approvedDatasetView.getDatasetId());
        approvedDatasetView.setMobigenDatasetView(mobigenDatasetView);
        
        return approvedDatasetView;
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
