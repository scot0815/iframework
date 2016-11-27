package com.scot.iframework.timeTask.constant;

/**
 * 定时任务状态.
 * Created by shengke on 2016/11/26.
 */
public class TaskState {

    /**
     * 阻塞(等待下次运行).
     */
    public static final int STATE_BLOCKED = 4;

    /**
     * 完成那一刻，不过一般不用这个判断Job状态.
     */
    public static final int STATE_COMPLETE = 2;

    /**
     * 错误.
     */
    public static final int STATE_ERROR = 3;

    /**
     * 未知.
     */
    public static final int STATE_NONE = -1;

    /**
     * 正常无任务，用这个判断Job是否在运行.
     */
    public static final int STATE_NORMAL = 0;

    /**
     * 暂停状态.
     */
    public static final int STATE_PAUSED = 1;
}
