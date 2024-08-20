package com.nelsonxilv.gstoutimetable.data.mapper

import com.nelsonxilv.gstoutimetable.data.mapper.mappers.GroupDbMapper
import com.nelsonxilv.gstoutimetable.data.mapper.mappers.LessonDbMapper
import com.nelsonxilv.gstoutimetable.data.mapper.mappers.LessonDtoMapper
import com.nelsonxilv.gstoutimetable.data.model.GroupDbModel
import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel
import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import javax.inject.Inject

class MapperService @Inject constructor(
    private val lessonDtoMapper: LessonDtoMapper,
    private val lessonDbMapper: LessonDbMapper,
    private val groupDbMapper: GroupDbMapper
) {

    fun mapListLessonDtoToListDbModel(list: List<LessonDto>) = list.map { dto ->
        lessonDtoMapper.transform(dto)
    }

    fun mapListLessonDbToListEntity(list: List<LessonDbModel>) = list.map { dbModel ->
        lessonDbMapper.transform(dbModel)
    }

    fun mapListGroupDbToListEntity(list: List<GroupDbModel>) = list.map { dbModel ->
        groupDbMapper.transform(dbModel)
    }

}