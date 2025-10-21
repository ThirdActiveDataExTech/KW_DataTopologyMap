package kware.apps.mobigen.cetus.dataset.service;


import kware.apps.mobigen.cetus.dataset.dto.request2.*;
import kware.apps.mobigen.cetus.dataset.dto.response2.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/package02")
    public ResponseEntity package02(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        log.info("[PACKAGE02] file: {} ", file.getOriginalFilename());
        resPackage02.resPackage02_Metadata metadata = new resPackage02.resPackage02_Metadata(
                "metadata_9999",
                "test title you can change",
                Arrays.asList("tag1", "tag2", "tag3")
        );
        return ResponseEntity.ok(new resPackage02("package02_100200", "Package import completed", metadata));
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
            List<String> keywords = Arrays.asList("tag" + i, "tag" + (i + 1), "tag" + (i + 2));
            items.add(new resMetadata01.resMetadata01_result.resMetadata01_items(metadataId, title, keywords));
        }
        resMetadata01.resMetadata01_result resMetadata01Result = new resMetadata01.resMetadata01_result(
                itemCount, search.getPagination().getPage(), search.getPagination().getLimit(), items
        );

        return ResponseEntity.ok(new resMetadata01("metadata01_100200", "Metadata search completed", resMetadata01Result));
    }

    @PostMapping("/metadata02")
    public ResponseEntity metadata02(@RequestBody reqMetadata02 request){
        log.info("[METADATA02] request: {} ", request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/metadata03")
    public ResponseEntity metadata03(@RequestPart("metadata") reqMetadata03 metadata,
                                     @RequestPart(value = "file", required = false) MultipartFile file) {
        log.info("[METADATA03] metadata: {} ", metadata);
        resMetadata03.resMetadata03_result result = null;
        if(file != null && !file.isEmpty()) {
            log.info("[METADATA03] file: {} ", file.getOriginalFilename());
            result = new resMetadata03.resMetadata03_result(metadata, file);
        } else {
            log.info("[METADATA03] no file uploaded");
            result = new resMetadata03.resMetadata03_result(metadata);
        }
        return ResponseEntity.ok(new resMetadata03("metadata03_100200", "Import completed", result));
    }

    @PostMapping("/metadata04")
    public ResponseEntity metadata04(@RequestBody reqMetadata04 request) {
        log.info("[METADATA04] request : {} ", request);
        resMetadata04.resMetadata04_result result = new resMetadata04.resMetadata04_result("테스트 타이틀", "키워드?");
        return ResponseEntity.ok(new resMetadata04("metadata04_100200", "Metadata found", result));
    }

    @PostMapping("/metadata05")
    public ResponseEntity metadata05(@RequestBody reqMetadata05 request) {
        log.info("[METADATA05] request : {} ", request);
        resMetadata05.resMetadata05_result result = new resMetadata05.resMetadata05_result(request.getMetadata_id());
        return ResponseEntity.ok(new resMetadata05("metadata05_100200", "Metadata updated successfully", result));
    }

    @PostMapping("/metadata07")
    public ResponseEntity metadata07(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        log.info("[METADATA07] file: {} ", file.getOriginalFilename());
        resMetadata07.reqMetadata07_Metadata metadata = new resMetadata07.reqMetadata07_Metadata(
                "metadata_9999",
                "test title you can change",
                Arrays.asList("tag1", "tag2", "tag3")
        );
        return ResponseEntity.ok(new resMetadata07("metadata07_100200", "Package import completed", metadata));
    }

    @PostMapping("/metadata08")
    public ResponseEntity metadata08(@RequestBody reqMetadata08 request) {
        log.info("[METADATA08] request : {} ", request);
        return ResponseEntity.ok("binary file download…");
    }
    /* =*=*=*=*=*=*=*=*=*=*=*=* [E:METADATA] =*=*=*=*=*=*=*=*=*=*=*=* */


    /* =*=*=*=*=*=*=*=*=*=*=*=* [S:RAWDATA] =*=*=*=*=*=*=*=*=*=*=*=* */
    @PostMapping("/rawdata01")
    public ResponseEntity rawdata01(@RequestBody reqRawdata01 request) {
        log.info("[RAWDATA01] request : {} ", request);

        Random random = new Random();
        int itemCount = random.nextInt(5) + 1; // 1~5 랜덤 개수
        List<resRawdata01.resRawdata01_result.resRawdata01_items> items = new ArrayList<>();
        for (int i = 1; i <= itemCount; i++) {
            items.add(new resRawdata01.resRawdata01_result.resRawdata01_items("checksum_"+i));
        }
        resRawdata01.resRawdata01_result resRawdata01Result = new resRawdata01.resRawdata01_result(
                request.getMetadata_id(), itemCount, items
        );
        return ResponseEntity.ok(new resRawdata01("rawdata01_100200", "Raw data list retrieved", resRawdata01Result));
    }

    @PostMapping("/rawdata02")
    public ResponseEntity rawdata02(@RequestPart("rawdata") reqRawdata02 rawdata,
                                    @RequestPart("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("no file founded");
        }
        log.info("[RAWDATA02] rawdata : {}, file: {} ", rawdata, file.getOriginalFilename());
        resRawdata02.resRawdata02_result result = new resRawdata02.resRawdata02_result(file);
        return ResponseEntity.ok(new resRawdata02("rawdata02_100200", "Raw data uploaded successfully", result));
    }

    @PostMapping("/rawdata04")
    public ResponseEntity rawdata04(@RequestBody reqRawdata04 search) {
        log.info("[RAWDATA04] search : {} ", search);
        resRawdata04.resRawdata04_result resRawdata04Result = new resRawdata04.resRawdata04_result(search.getRawdata_id(), search.getMetadata_id());
        return ResponseEntity.ok(new resRawdata04("rawdata04_100200", "Raw data details retrieved", resRawdata04Result));
    }

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
