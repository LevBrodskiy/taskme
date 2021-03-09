package com.blm.taskme.service.mapper;

import com.blm.taskme.api.v1.request.TaskRequest;
import com.blm.taskme.api.v1.response.TaskResponse;
import com.blm.taskme.domain.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toResponse(Task task);
    void mergeIntoTask(@MappingTarget Task task, TaskRequest request);
}
