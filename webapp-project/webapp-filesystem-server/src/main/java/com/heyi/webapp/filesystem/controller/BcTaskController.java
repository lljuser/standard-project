package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.BcTask;
import com.heyi.core.filestorage.service.BcTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class BcTaskController {

    @Autowired
    private BcTaskService taskService;

    // 增加一个任务
    @PostMapping(value = "/add")
    public Object saveTask(@RequestBody BcTask task) {
        return ApiResponse.success(taskService.saveTask(task));
    }

    // 修改任务状态
    @PostMapping(value = "/update")
    public Object updateTask(@RequestBody BcTask task) {
        Object obj = taskService.updateTaskState(task);
        if(obj.equals(0)){
            return  ApiResponse.error("更新状态失败");
        }else {
            return ApiResponse.success(taskService.updateTaskState(task));
        }
    }

    @GetMapping(value = "/todos")
    public ApiResponse<PageResult<BcTask>> getToDoList(PageQuery pageQuery) {
        return ApiResponse.success(taskService.getToDoList(pageQuery));
    }

    @GetMapping("/validate-all-task-status/{transactionId}")
    public ApiResponse<Boolean> validateAllTaskStatus(@PathVariable String transactionId) {
        try {
            taskService.allReceiveBrokerConfirmed(transactionId);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
        return ApiResponse.success(true);
    }
}