package com.vinspier.aop.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
@Table(name = "system_log")
public class SystemLog implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志名称
     */
    private String name;

    /**
     * 日志类型 1.异常 2.操作日志
     */
    private String type;

    /**
     * 类名称
     */
    private String classname;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String message;

    /**
     * 异常信息
     */
    private String exceptionMsg;

}
