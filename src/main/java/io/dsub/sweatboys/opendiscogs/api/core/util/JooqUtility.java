package io.dsub.sweatboys.opendiscogs.api.core.util;

import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;

import java.util.List;

public final class JooqUtility {
    private JooqUtility(){}
    
    private static final JooqUtility INSTANCE = new JooqUtility();
    
    public static List<SortField<Object>> getSortFields(Pageable pageable){
        return INSTANCE.doGetSortFields(pageable);
    } 
    public List<SortField<Object>> doGetSortFields(Pageable pageable) {
        return pageable.getSort().stream()
                .map(o -> o.isAscending() ? DSL.field(o.getProperty()).asc() : DSL.field(o.getProperty()).desc())
                .toList();
    }
}
