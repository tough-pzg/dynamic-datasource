package com.pzg.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * @author pzg
 * @description
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {
    /**
     * @return 使用的数据源名称,默认使用  default 数据源
     */
    String value() default "default";

}
