
package kware.apps.thirdeye.bookmark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kware.apps.thirdeye.bookmark.domain.CetusBookmark;
import kware.apps.thirdeye.bookmark.domain.CetusBookmarkDao;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kware.apps.thirdeye.bookmark.dto.request.CetusBookmarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.CetusSearchBookmark;
import kware.apps.thirdeye.contents.dto.response.HomeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusBookmarkService {

    private final CetusBookmarkDao dao;

    @Transactional(readOnly = true)
    public List<CetusBookmark> getList(CetusSearchBookmark searchBookmark) {

        List<CetusBookmark> bookmarkList = dao.findListByUserUid(searchBookmark);
        try {
            ClassPathResource resource = new ClassPathResource("static/assets/data/thirdeye/list_data.json");
            byte[] jsonData = Files.readAllBytes(resource.getFile().toPath());

            ObjectMapper objectMapper = new ObjectMapper();
            List<HomeData> dataList = objectMapper.readValue(jsonData, new TypeReference<List<HomeData>>() {});

            Map<Long, HomeData> dataMap = dataList.stream()
                                        .collect(Collectors.toMap(HomeData::getUid, data -> data));

            for (CetusBookmark bookmark : bookmarkList) {
                HomeData data = dataMap.get(bookmark.getContentsUid());
                if (data != null) {
                    bookmark.setTitle(data.getTitle());
                    bookmark.setDescription(data.getDescription());
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookmarkList;
    }

    @Transactional
    public Boolean toggleLike(CetusBookmarkToggle bean, Long userUid) {

        CetusBookmark bookmark = new CetusBookmark( userUid, bean.getContentsUid() );

        bookmark.setRegUid(userUid);
        bookmark.setUpdtUid(userUid);

        // 코드 체크
        // vaildationTargetType(wish.getTargetType());

        // 기존 좋아요 체크
        Boolean wishExists = dao.isBookmarkExists(bookmark);

        if(wishExists) {
             dao.deleteBookMark(bookmark);
             return false;
        } else {
             dao.insert(bookmark);
             return true;
        }
    }

    public void delete(CetusBookmark request) {
        dao.deleteBookMark(request);
    }

    @Transactional(readOnly = true)
    public Boolean isBookmarkExists(CetusBookmark bookmark) {
        return dao.isBookmarkExists(bookmark);
    }

}
