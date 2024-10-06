package com.nelsonxilv.gstoutimetable.data.mapper.mappers

import com.nelsonxilv.gstoutimetable.data.model.GroupDbModel
import com.nelsonxilv.gstoutimetable.domain.entity.Group
import javax.inject.Inject

class GroupDbMapper @Inject constructor() : Mapper<GroupDbModel, Group> {
    override fun transform(data: GroupDbModel) = Group(
        groupName = data.groupName
    )
}