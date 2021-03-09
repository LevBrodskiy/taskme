package com.blm.taskme.service.mapper;

import com.blm.taskme.api.v1.request.TaskListRequest;
import com.blm.taskme.api.v1.response.TaskListResponse;
import com.blm.taskme.domain.TaskList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    void mergeIntoTaskList(@MappingTarget TaskList taskList, TaskListRequest request);

    TaskListResponse toResponse(TaskList taskList);
}
