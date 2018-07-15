package com.wong.note.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 实体@Entity注解
 * schema：告知GreenDao当前实体属于哪个schema
 * active：标记一个实体处于活跃状态，活动实体有更新、删除和刷新方法
 * nameInDb：在数据库中使用的别名，默认使用的是实体的类名
 * indexes：定义索引，可以跨越多个列
 * createInDb：标记创建数据库表
 * <p>
 * 基础属性注解
 *
 * @Id：主键 Long 型，可以通过@Id(autoincrement = true)设置自增长
 * @Property：设置一个非默认关系映射所对应的列名，默认是使用字段名，例如：@Property(nameInDb = "name")
 * @NotNull：设置数据库表当前列不能为空
 * @Transient：添加此标记后不会生成数据库表的列 索引注解
 * @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
 * @Unique：向数据库添加了一个唯一的约束 关系注解
 * @ToOne：定义与另一个实体（一个实体对象）的关系
 * @ToMany：定义与多个实体对象的关系
 */


@Entity
public class TaskEntity {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private int days;
    private int status;
    private long time;
    private int layoutType;
    private long updateTime;  //每天更新一次，每一天都将签到状态初始化为未签到
    @Generated(hash = 1878399033)
    public TaskEntity(Long id, String title, int days, int status, long time,
            int layoutType, long updateTime) {
        this.id = id;
        this.title = title;
        this.days = days;
        this.status = status;
        this.time = time;
        this.layoutType = layoutType;
        this.updateTime = updateTime;
    }
    @Generated(hash = 397975341)
    public TaskEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getDays() {
        return this.days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getLayoutType() {
        return this.layoutType;
    }
    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }
    public long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
