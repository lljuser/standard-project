package com.heyi.core.netty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @描述 com.heyi.core.netty.domain
 * @创建人 ljliu
 * @创建时间 2018/10/22
 * @修改人和其它信息
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" },ignoreUnknown = true)
public interface IEntity {
}
