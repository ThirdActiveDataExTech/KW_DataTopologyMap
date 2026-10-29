package kware.apps.thirdeye.mobigen.category.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.mobigen.category.dto.request.changesortno.ChangeCategorySortNoList;
import org.junit.jupiter.api.Test;

/**
 * 카테고리 일괄 삭제·통합·순서 변경 요청은 대상 목록이 비면 거절한다(빈 목록은 건별 반복이 아무 일도 안 하거나 SQL 오류).
 * 순서 변경 항목은 카테고리 uid 와 1 이상의 순서가 있어야 한다.
 */
class CetusDatasetCategoryAdminRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 일괄_삭제는_대상이_비면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"uids\":[]}", DeleteCategories.class)))).containsExactly("uids");
        assertThat(validator.validate(mapper.readValue("{\"uids\":[5,6]}", DeleteCategories.class))).isEmpty();
    }

    @Test
    void 통합은_대상과_카테고리가_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", CloneCategory.class)))).containsExactlyInAnyOrder("uids", "category");
        assertThat(paths(validator.validate(mapper.readValue("{\"uids\":[5],\"category\":{\"categoryNm\":\"\"}}", CloneCategory.class))))
                .containsExactly("category.categoryNm");
        assertThat(validator.validate(mapper.readValue("{\"uids\":[5,6],\"category\":{\"uid\":7,\"categoryNm\":\"여자\"}}", CloneCategory.class))).isEmpty();
    }

    @Test
    void 순서_변경은_항목마다_카테고리와_1_이상의_순서가_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"categories\":[]}", ChangeCategorySortNoList.class)))).containsExactly("categories");
        assertThat(paths(validator.validate(mapper.readValue("{\"categories\":[{\"categoryUid\":null,\"sortNo\":0}]}", ChangeCategorySortNoList.class))))
                .containsExactlyInAnyOrder("categories[0].categoryUid", "categories[0].sortNo");
        assertThat(validator.validate(mapper.readValue("{\"categories\":[{\"categoryUid\":5,\"sortNo\":1},{\"categoryUid\":6,\"sortNo\":2}]}", ChangeCategorySortNoList.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
