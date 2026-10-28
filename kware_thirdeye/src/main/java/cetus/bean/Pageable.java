package cetus.bean;

import cetus.support.Reflector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
    /** 한 페이지 최대 건수. 화면 목록(lengthMenu)의 최대값과 같고, 요청값이 더 커도 이 이상은 내리지 않는다 */
    public static final int MAX_SIZE = 100;

    private Integer countPage = 10;
    private Integer pageNumber = 1;
    private Integer size = 10;

    public Pageable(Integer size) {
        this.size = size;
    }

    public void setCountPage(Integer countPage) {
        if(countPage != null) {
            this.countPage = Math.max(countPage, 1);
        }
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber != null) {
            this.pageNumber = Math.max(pageNumber, 1);
        }
    }

    /** 요청 파라미터의 건수는 1~MAX_SIZE 로 제한한다. 매퍼가 OFFSET/LIMIT 에 그대로 쓰므로 음수·거대값을 막는다 */
    public void setSize(Integer size) {
        if(size != null) {
            this.size = Math.min(Math.max(size, 1), MAX_SIZE);
        }
    }
    public final Map<String, Object> generateMap(Object bean) {
        Map<String, Object> map = Reflector.getGetterMap(bean);
        map.put("startNumber", (this.pageNumber - 1) * this.size);  // offset
        map.put("endNumber", this.size);    // limit
        return map;
    }
}
