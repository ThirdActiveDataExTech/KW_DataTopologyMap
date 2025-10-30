package kware.apps.thirdeye.mobigen.approveddataset.service;

import cetus.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchMetadataApproved;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusApprovedDatasetService2 {

    private final CetusApprovedDatasetDao dao;

    @Transactional
    public void updateDatasetSearchData(Long metadataId, String title, List<TagList> tags) {
        List<Long> tagUids = tags.stream().map(TagList::getTagUid).collect(Collectors.toList());
        Map<String, Object> searchDataMap = new HashMap<>();
        searchDataMap.put("title", title);
        searchDataMap.put("tags", tagUids);
        String searchDataJson = ObjectUtil.makeJsonString(searchDataMap);
        dao.updateApprovedDatasetSearchData(new CetusApprovedDataset(metadataId, searchDataJson));
    }


    /**
     * @method      findMetadataIsApproved
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 데이터셋에 대해서 현재 KWARE 포탈 시스템에 진열관리 중인지 체크
     **/
    @Transactional(readOnly = true)
    public Boolean findMetadataIsApproved(SearchMetadataApproved search) {
        return dao.getMetadataIsApproved(search);
    }
}
