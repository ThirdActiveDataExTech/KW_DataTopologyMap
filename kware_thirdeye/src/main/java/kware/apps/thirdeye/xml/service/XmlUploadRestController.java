package kware.apps.thirdeye.xml.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/xml")
public class XmlUploadRestController {

    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {

            // 1. 업로드 파일을 [InputStream] 형태로 읽음
            InputStream inputStream = file.getInputStream();

            // 2. {DOM Parser} 이용을 통해 XML 문서를 Document 객체로 파싱
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(inputStream);

            // 3. normalize() : 공백과 텍스트 노드 정리, 문서 구조 표준화
            doc.getDocumentElement().normalize();

            // 4. root ~ 재귀적으로 JSON 구조 변환
            Object json = traverseNodes(doc.getDocumentElement());

            // 5. 응답
            return ResponseEntity.ok(json);

        } catch (Exception e) {
            log.error("❌ 업로드 실패 =>> ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /* XML 노드를 탐색하며 JSON 구조 생성 */
    private Object traverseNodes(Node node) {

        // * 현재 노드가 엘리먼트가 아니라면 무시
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        // 현재 노드 -> Element 변환
        // * LinkedHashMap : JSON 순서 유지를 위한 사용 맵
        Element elem = (Element) node;
        Map<String, Object> map = new LinkedHashMap<>();

        // (1) 속성 처리
        // (ex) <Book id="bk101"> → { "id": "bk101" }
        if ( elem.hasAttributes() ) {
            NamedNodeMap attrs = elem.getAttributes();
            for ( int i = 0; i < attrs.getLength(); i++ ) {
                Node attr = attrs.item(i);
                map.put( attr.getNodeName(), attr.getNodeValue() );
            }
        }

        // (2) 자식 노드 탐색
        // => 자식 노드가 또 다른 엘리멑느라면 재귀적인 호출 진행
        NodeList children = elem.getChildNodes();
        boolean hasElementChild = false;

        for ( int i = 0; i < children.getLength(); i++ ) {

            Node child = children.item(i);

            if (child.getNodeType() == Node.ELEMENT_NODE) {

                hasElementChild = true;
                Object childObject = traverseNodes(child);
                String childName = child.getNodeName();

                // 같은 태그가 여러 개 => {List} 처리
                if (map.containsKey(childName)) {

                    Object existing = map.get(childName);
                    List<Object> list;
                    if (existing instanceof List) {
                        list = (List<Object>) existing;
                    } else {
                        list = new ArrayList<>();
                        list.add(existing);
                    }
                    list.add(childObject);
                    map.put(childName, list);

                } else {
                    map.put(childName, childObject);
                }

            } else if (child.getNodeType() == Node.TEXT_NODE) {

                String text = child.getTextContent().trim();
                if (!text.isEmpty()) {
                    // 태그 안에 속성이 없고, element 자식이 없으면 바로 텍스트 반환
                    if (!elem.hasAttributes() && !hasElementChild) {
                        return text;
                    }
                }

            }
        }

        return map;
    }

}
