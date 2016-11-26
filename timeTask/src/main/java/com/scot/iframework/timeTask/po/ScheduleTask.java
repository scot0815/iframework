package com.scot.iframework.timeTask.po;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Date;

/**
 * 定时任务信息
 * Created by shengke on 2016/11/25.
 */
public class ScheduleTask {

    /**
     * 唯一标示.
     */
    private String id;
    /**
     * 父任务ID.
     */
    private String parentId;
    /**
     * 任务名称.
     */
    private String name;
    /**
     * 任务描述.
     */
    private String desc;
    /**
     * 执行次数.
     */
    private int planExe;
    /**
     * 任务组名,执行的类名.
     */
    private String group;
    /**
     * 任务组描述.
     */
    private String groupDesc;
    /**
     * 任务表达式.
     */
    private String cron;
    /**
     * 表达式名称.
     */
    private String cronDesc;
    /**
     * 触发器，执行的方法名.
     */
    private String trigger;
    /**
     * 触发器描述.
     */
    private String triggerDesc;

    /**
     * 触发器组.
     */
    private String triggerGroup;

    /**
     * 触发器组描述.
     */
    private String triggerGroupDesc;
    /**
     * 执行次数.
     */
    private int execute;
    /**
     * 最后执行时间.
     */
    private Date lastTime;
    /**
     * 最后完成时间.
     */
    private Date lastFinishTime;
    /**
     * 任务状态。0：禁用、1：启动、2：删除
     */
    private int state;
    /**
     * 延时时间.
     */
    private int delay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPlanExe() {
        return planExe;
    }

    public void setPlanExe(int planExe) {
        this.planExe = planExe;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getCronDesc() {
        return cronDesc;
    }

    public void setCronDesc(String cronDesc) {
        this.cronDesc = cronDesc;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTriggerDesc() {
        return triggerDesc;
    }

    public void setTriggerDesc(String triggerDesc) {
        this.triggerDesc = triggerDesc;
    }

    public int getExecute() {
        return execute;
    }

    public void setExecute(int execute) {
        this.execute = execute;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getLastFinishTime() {
        return lastFinishTime;
    }

    public void setLastFinishTime(Date lastFinishTime) {
        this.lastFinishTime = lastFinishTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getTriggerGroupDesc() {
        return triggerGroupDesc;
    }

    public void setTriggerGroupDesc(String triggerGroupDesc) {
        this.triggerGroupDesc = triggerGroupDesc;
    }
}
