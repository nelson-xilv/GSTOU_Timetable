package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import com.nelsonxilv.gstoutimetable.domain.entity.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val timetableRepository: TimetableRepository
) {
    operator fun invoke(): Flow<List<Group>> {
        return timetableRepository.getGroupList()
    }
}