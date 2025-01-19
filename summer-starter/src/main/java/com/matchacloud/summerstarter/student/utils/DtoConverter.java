package com.matchacloud.summerstarter.student.utils;

import com.matchacloud.summerstarter.student.domain.dto.StudentDTO;
import com.matchacloud.summerstarter.student.domain.vo.StudentVO;

/**
 *
 */
public class DtoConverter {

    /**
     * vo2dto插件使用
     * @param dto
     * @return
     */
    public StudentVO dtoToStudentVO(StudentDTO dto) {
        StudentVO studentVO = new StudentVO();
        studentVO.setStudentId(dto.getStudentId());
        studentVO.setName(dto.getName());
        studentVO.setAge(dto.getAge());
        studentVO.setMajor(dto.getMajor());
        studentVO.setGpa(dto.getGpa());
        return studentVO;
    }
}
