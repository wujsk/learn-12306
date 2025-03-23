package com.learn.index12306.frameworks.starter.convention.page;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: cyy
 * @create: 2025-03-13 19:27
 * @description: 分页响应对象
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    @Min(value = 1, message = "当前页数不能小于1")
    private Long current;

    /**
     * 每页条数
     */
    @Min(value = 10, message = "每页条数不能小于10")
    private Long size;

    /**
     * 总条数
     */
    @Min(value = 0, message = "总条数不能小于0")
    private Long total;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    public PageResponse(Long current, Long size) {
        this(current, size, 0L);
    }

    public PageResponse(Long current, Long size, Long total) {
        this.current = current;
        this.size = size;
        this.total = total;
    }

    public PageResponse<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public <R> PageResponse<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(Collectors.toList());
        return ((PageResponse<R>) this).setRecords(collect);
    }
}
