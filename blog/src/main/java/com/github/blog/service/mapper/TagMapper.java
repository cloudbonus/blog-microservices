package com.github.blog.service.mapper;

import com.github.blog.controller.dto.common.TagDto;
import com.github.blog.controller.dto.request.TagRequest;
import com.github.blog.controller.dto.request.filter.TagFilterRequest;
import com.github.blog.repository.entity.Tag;
import com.github.blog.repository.filter.TagFilter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper extends BasePageMapper<Tag, TagDto> {
    Tag toEntity(TagRequest request);

    TagDto toDto(Tag tag);

    TagFilter toEntity(TagFilterRequest requestFilter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag partialUpdate(TagRequest request, @MappingTarget Tag tag);
}