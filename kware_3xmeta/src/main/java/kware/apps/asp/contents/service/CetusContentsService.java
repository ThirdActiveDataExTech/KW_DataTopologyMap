package kware.apps.asp.contents.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.asp.contents.domain.CetusCategories;
import kware.apps.asp.contents.domain.CetusContents;
import kware.apps.asp.contents.domain.CetusContentsDao;
import kware.apps.asp.contents.dto.request.ContentsSearch;
import kware.apps.asp.contents.dto.response.ContentsPage;
import kware.apps.manager.cetus.bbs.domain.CetusBbs;
import kware.apps.manager.cetus.contents.categories.Categories;
import kware.apps.manager.cetus.contents.categories.ContentFormat;
import kware.apps.manager.cetus.contents.categories.ContentRatings;
import kware.apps.manager.cetus.contents.categories.DateRange;
import kware.apps.manager.cetus.contents.categories.Language;
import kware.apps.manager.cetus.contents.categories.Regions;
import kware.apps.manager.cetus.contents.categories.Sns;
import kware.apps.manager.cetus.contents.categories.Sources;
import kware.apps.manager.cetus.contents.categories.Types;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CetusContentsService {

    private final CetusContentsDao dao;

    public List<CetusCategories> categoriesList() {
        return List.of(
            new CetusCategories(Categories.FIELD, Categories.FIELDNAME, Categories.ITEMS),
            new CetusCategories(ContentFormat.FIELD, ContentFormat.FIELDNAME, ContentFormat.ITEMS),
            new CetusCategories(ContentRatings.FIELD, ContentRatings.FIELDNAME, ContentRatings.ITEMS),
            new CetusCategories(DateRange.FIELD, DateRange.FIELDNAME, DateRange.ITEMS),
            new CetusCategories(Language.FIELD, Language.FIELDNAME, Language.ITEMS),
            new CetusCategories(Regions.FIELD, Regions.FIELDNAME, Regions.ITEMS),
            new CetusCategories(Sns.FIELD, Sns.FIELDNAME, Sns.ITEMS),
            new CetusCategories(Sources.FIELD, Sources.FIELDNAME, Sources.ITEMS),
            new CetusCategories(Types.FIELD, Types.FIELDNAME, Types.ITEMS)
        );
    }

    @Transactional(readOnly = true)
    public Page<ContentsPage> getContentPageList(ContentsSearch search, Pageable pageable) {
        return dao.page("contentPageList", "contentPageListCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public CetusContents view(Long uid) {
        return dao.view(uid);
    }

}
