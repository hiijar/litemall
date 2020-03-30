package org.linlinjava.litemall.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallOrder;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);
}