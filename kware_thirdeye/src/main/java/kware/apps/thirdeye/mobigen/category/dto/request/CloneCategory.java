package kware.apps.thirdeye.mobigen.category.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @fileName     CloneCategory
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-21
* @summary      기존에 데이터셋과 연결된 카테고리에 대해서 새로운 카테고리 혹은 다른 카테고리로 재연결 시키기
 *              (ex) {female} 카테고리 하위로 데이터셋 {data_A}가 있고
 *                   {여성}  카테고리 하위로 데이터셋 {data_B, data_C}가 있었을때
 *                   {female, 여성} -> {여자} 카테고리로 통합한다.
 *
 *                   (i) {data_A, data_B, data_C} 데이터셋의 카테고리를 새롭게 생성된 {여자}로 연결
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CloneCategory {
    private List<Long> uids;
    private SaveCategory category;
}
