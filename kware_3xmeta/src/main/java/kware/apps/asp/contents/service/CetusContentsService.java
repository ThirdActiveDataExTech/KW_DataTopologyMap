package kware.apps.asp.contents.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.asp.contents.domain.CetusCategories;
import kware.apps.asp.contents.domain.CetusContents;
import kware.apps.asp.contents.domain.CetusContentsDao;
import kware.apps.asp.contents.domain.CetusTags;
import kware.apps.asp.contents.dto.request.ContentsSearch;
import kware.apps.asp.contents.dto.response.ContentsPage;
import kware.apps.asp.contents.dto.response.ContentsView;
import kware.apps.asp.contents.request.ContentChange;
import kware.apps.manager.cetus.contents.categories.Categories;
import kware.apps.manager.cetus.contents.categories.Sources;
import kware.apps.manager.cetus.contents.categories.Types;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CetusContentsService {

    private final CetusContentsDao dao;
    private final CommonFileService commonFileService;

    public List<CetusCategories> categoriesList() {
        return List.of(
            new CetusCategories(Categories.FIELD, Categories.FIELDNAME, Categories.ITEMS),
            // new CetusCategories(ContentFormat.FIELD, ContentFormat.FIELDNAME, ContentFormat.ITEMS),
            // new CetusCategories(ContentRatings.FIELD, ContentRatings.FIELDNAME, ContentRatings.ITEMS),
            // new CetusCategories(DateRange.FIELD, DateRange.FIELDNAME, DateRange.ITEMS),
            // new CetusCategories(Language.FIELD, Language.FIELDNAME, Language.ITEMS),
            // new CetusCategories(Regions.FIELD, Regions.FIELDNAME, Regions.ITEMS),
            // new CetusCategories(Sns.FIELD, Sns.FIELDNAME, Sns.ITEMS),
            new CetusCategories(Sources.FIELD, Sources.FIELDNAME, Sources.ITEMS),
            new CetusCategories(Types.FIELD, Types.FIELDNAME, Types.ITEMS)
        );
    }

    @Transactional(readOnly = true)
    public Page<ContentsPage> getContentPageList(ContentsSearch search, Pageable pageable) {
        return dao.page("contentPageList", "contentPageListCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public ContentsView view(Long uid) {
        ContentsView contents = dao.contentsView(uid);
        List<CetusTags> tags = dao.findTagsByContentsUid(uid);
        contents.setTags(tags);
        return contents;
    }
    
    @Transactional
    public void changeContent(Long uid, ContentChange request) {
        CetusContents view = dao.view(uid);

        // img
        Long contentFile = commonFileService.processFileSeparately(request.getContentFile(), request.getContentFileDel(), request.getContentFileUid());
        Long thumbnail = commonFileService.processFileSeparately(request.getThumbnail(), request.getThumbnailDel(), request.getThumbnailUid());

        CetusContents bean = view.changeContent(uid, request);
        bean.setFileUids(contentFile, thumbnail);

        dao.update(bean);
    }

}
