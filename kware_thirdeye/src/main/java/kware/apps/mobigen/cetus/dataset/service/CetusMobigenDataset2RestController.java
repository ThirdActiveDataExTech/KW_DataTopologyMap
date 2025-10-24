package kware.apps.mobigen.cetus.dataset.service;


import kware.apps.mobigen.cetus.dataset.dto.request2.*;
import kware.apps.mobigen.cetus.dataset.dto.response2.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/mobigen-dataset2")
public class CetusMobigenDataset2RestController {

    private final CetusMobigenDatasetService service;

    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:PACKAGE] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/package01")
    public ResponseEntity package01(@RequestBody reqPackage01 request) {
        log.info("[PACKAGE01] MetadataId: {}, RawdataIds: {}", request.getMetadataId(), request.getRawdataIds());
        resPackage01 result = new resPackage01(
                "package01_100200",
                "Package Download completed",
                new resPackage01.Package01_Result(request.getMetadataId(), request.getRawdataIds())
        );
        return ResponseEntity.ok(result);
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:PACKAGE] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:METADATA] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/metadata01")
    public ResponseEntity metadata01(@RequestBody reqMetadata01 search) {
        log.info("[METADATA01] search: {} ", search);

        Random random = new Random();
        int itemCount = random.nextInt(5) + 1; // 1~5 랜덤 개수
        List<resMetadata01.resMetadata01_result.resMetadata01_items> items = new ArrayList<>();
        for (int i = 1; i <= itemCount; i++) {
            String metadataId = "metadata_" + i;
            String title = "Test Title " + i;
            String joined = String.join("," , Arrays.asList("tag" + i, "tag" + (i + 1), "tag" + (i + 2)));
            items.add(new resMetadata01.resMetadata01_result.resMetadata01_items(metadataId, title, joined));
        }
        resMetadata01.resMetadata01_result resMetadata01Result = new resMetadata01.resMetadata01_result(
                itemCount, search.getPagination().getPage(), search.getPagination().getLimit(), items
        );

        return ResponseEntity.ok(new resMetadata01("metadata01_100200", "Metadata search completed", resMetadata01Result));
    }

    @PostMapping("/metadata08")
    public ResponseEntity metadata08(@RequestBody reqMetadata08 request) {
        log.info("[METADATA08] request : {} ", request);
        return ResponseEntity.ok("binary file download…");
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:METADATA] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:RAWDATA] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/rawdata07")
    public ResponseEntity rawdata07(@RequestBody reqRawdata07 request) {
        log.info("[RAWDATA07] request : {} ", request);
        return ResponseEntity.ok("binary file download…");
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:RAWDATA] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:RELATION] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/relation01")
    public ResponseEntity relation01(@RequestBody reqRelation01 search) {
        log.info("[RELATION01] search : {} ", search);

        Random random = new Random();
        int itemCount = random.nextInt(5) + 1;
        List<resRelation01.resRelation01_result.resRelation01_items> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {

            String metadataId = "meta_" + (random.nextInt(9000) + 1000);
            String title = "테스트 제목_" + (i + 1);
            float similarityScore = random.nextFloat(); // 0.0 ~ 1.0
            List<String> allFields = Arrays.asList("field_a", "field_b", "field_c", "field_d");
            Collections.shuffle(allFields);
            List<String> commonFields = allFields.subList(0, random.nextInt(allFields.size()) + 1);

            items.add(
                new resRelation01.resRelation01_result.resRelation01_items(metadataId, title, similarityScore, new ArrayList<>(commonFields)
            ));
        }

        int relationCount = random.nextInt(5) + 1;
        List<resRelation01.resRelation01_result.resRelation01_relations> relations = new ArrayList<>();
        for (int i = 0; i < relationCount; i++) {
            String source = "src_" + (random.nextInt(10) + 1);
            String target = "tgt_" + (random.nextInt(10) + 1);
            float correlation = random.nextFloat();
            relations.add(
                new resRelation01.resRelation01_result.resRelation01_relations(source, target, correlation)
            );
        }

        resRelation01.resRelation01_result result = new resRelation01.resRelation01_result(
                "1",
                itemCount,
                search.getPagination().getPage(),
                search.getPagination().getLimit(),
                items,
                relations
        );
        return ResponseEntity.ok(new resRelation01("relation01_100200", "Related metadata retrieved", result));
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:RELATION] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:RECOMMENDATIONS] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/recommendation01")
    public ResponseEntity recommendation01(@RequestBody reqRecommendations01 search) {
        log.info("[RECOMMENDATION01] search : {} ", search);

        Random random = new Random();
        int itemCount = random.nextInt(5) + 1;
        List<resRecommendations01.resRecommendations01_result.resRecommendations01_items> items = new ArrayList<>();
        String[] reasons = {"유사한 주제", "같은 기관 데이터", "최근 인기", "연관 키워드"};
        String[] types = {"similar", "popular", "related"};
        for (int i = 1; i <= itemCount; i++) {
            String metadataId = "meta_" + (1000 + random.nextInt(9000));
            String title = "추천 데이터셋 " + i;
            float score = Math.round(random.nextFloat() * 100) / 100.0f; // 0.00 ~ 1.00
            String reason = reasons[random.nextInt(reasons.length)];
            String type = types[random.nextInt(types.length)];
            items.add(
                new resRecommendations01.resRecommendations01_result.resRecommendations01_items(metadataId, title, score, reason, type)
            );
        }

        resRecommendations01.resRecommendations01_result result =
                new resRecommendations01.resRecommendations01_result(
                        "meta_" + (1000 + random.nextInt(9000)), // metadata_id
                        itemCount,
                        search.getPagination().getPage(),
                        search.getPagination().getLimit(),
                        items
                );
        return ResponseEntity.ok(new resRecommendations01("recommendation01_100200", "Recommendations generated", result));
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:RECOMMENDATIONS] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:META] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/meta01")
    public ResponseEntity meta01() {
        List<String> list = Arrays.asList("publisher", "theme", "tags", "created_at");
        return ResponseEntity.ok(new resMeta01("meta01_100200", "Success", new resMeta01.resMeta01_result(list)));
    }
    @PostMapping("/meta02")
    public ResponseEntity meta02(@RequestBody reqMeta02 search) {
        log.info("[META02] search : {} ", search);
        resMeta02.resMeta02_result result = new resMeta02.resMeta02_result(search.getKey(), Arrays.asList("publisher001", "publisher002", "publisher003"));
        return ResponseEntity.ok(new resMeta02("meta02_100200", "Success", result));
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:META] =*=*=*=*=*=*=*=*=*=*=*=* */
}
