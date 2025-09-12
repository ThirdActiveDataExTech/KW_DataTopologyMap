
package kware.apps.thirdeye.bookmark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cetus.user.UserUtil;
import kware.apps.thirdeye.bookmark.domain.CetusBookMark;
import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import kware.apps.thirdeye.dataset.dto.response.DataSetView;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kware.apps.thirdeye.bookmark.dto.request.UserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusBookMarkService {

    private final CetusBookMarkDao dao;

    @Transactional(readOnly = true)
    public List<UserBookMarkList> findUserBookMarkList(SearchUserBookMark search ) {

        List<UserBookMarkList> userBookMarkList = dao.getUserBookMarkList(search);

        try {
            
            // todo 추후에 API => DATASET_ID 값으로 정보 호출
            
            ClassPathResource resource = new ClassPathResource("static/assets/data/testdata/dataset_list.json");
            byte[] jsonData = Files.readAllBytes(resource.getFile().toPath());

            ObjectMapper objectMapper = new ObjectMapper();
            List<DataSetView> dataList = objectMapper.readValue(jsonData, new TypeReference<List<DataSetView>>() {});

            Map<Long, DataSetView> dataMap = dataList.stream()
                                        .collect(Collectors.toMap(DataSetView::getDatasetId, data -> data));

            for ( UserBookMarkList userBookMark : userBookMarkList ) {
                Long datasetId = userBookMark.getDatasetId();
                DataSetView data = dataMap.get(datasetId);
                if ( data != null ) {
                    userBookMark.setDatasetInfo( data.getTitle(), data.getDescription() );
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userBookMarkList;
    }

    @Transactional
    public Boolean toggleLike( UserBookMarkToggle request ) {

        Long userUid = UserUtil.getUser().getUid();
        SearchUserBookMarkToggle search = new SearchUserBookMarkToggle( userUid, request.getDatasetId() );

        // 기존 좋아요 체크
        Boolean wishExists = dao.isBookMarkExists(search);

        CetusBookMark bean = new CetusBookMark(userUid, request.getDatasetId());

        if( wishExists ) {

            dao.deleteBookMark(bean);
             return false;

        } else {

             dao.insert(bean);
             return true;

        }
    }

    @Transactional
    public void deleteBookMark( Long datasetId ) {
        CetusBookMark bean = new CetusBookMark( UserUtil.getUser().getUid(), datasetId );
        dao.deleteBookMark(bean);
    }

    @Transactional(readOnly = true)
    public Boolean isBookmarkExists( SearchUserBookMarkToggle search ) {
        return dao.isBookMarkExists(search);
    }

}
