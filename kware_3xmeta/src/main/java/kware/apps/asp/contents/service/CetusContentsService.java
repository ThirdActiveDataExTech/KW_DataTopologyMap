package kware.apps.asp.contents.service;

import kware.apps.asp.contents.domain.CetusCategories;
import kware.apps.manager.cetus.categories.Categories;
import kware.apps.manager.cetus.categories.ContentFormat;
import kware.apps.manager.cetus.categories.ContentRatings;
import kware.apps.manager.cetus.categories.DateRange;
import kware.apps.manager.cetus.categories.Language;
import kware.apps.manager.cetus.categories.Regions;
import kware.apps.manager.cetus.categories.Sns;
import kware.apps.manager.cetus.categories.Types;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusContentsService {

    public List<CetusCategories> categoriesList() {
        return List.of(
            new CetusCategories(Categories.FIELD, Categories.FIELDNAME, Categories.ITEMS),
            new CetusCategories(ContentFormat.FIELD, ContentFormat.FIELDNAME, ContentFormat.ITEMS),
            new CetusCategories(ContentRatings.FIELD, ContentRatings.FIELDNAME, ContentRatings.ITEMS),
            new CetusCategories(DateRange.FIELD, DateRange.FIELDNAME, DateRange.ITEMS),
            new CetusCategories(Language.FIELD, Language.FIELDNAME, Language.ITEMS),
            new CetusCategories(Regions.FIELD, Regions.FIELDNAME, Regions.ITEMS),
            new CetusCategories(Sns.FIELD, Sns.FIELDNAME, Sns.ITEMS),
            new CetusCategories(Types.FIELD, Types.FIELDNAME, Types.ITEMS)
        );
    }
}
