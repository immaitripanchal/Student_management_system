package com.maitri.java.demo.service.implementation;

import com.maitri.java.demo.dto.AddStudentRequestDto;
import com.maitri.java.demo.dto.StudentDto;
import com.maitri.java.demo.entity.Student;
import com.maitri.java.demo.repository.StudentRepo;
import com.maitri.java.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService
{
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        List<StudentDto> studentDtoList = students
                .stream()
                .map(student -> new StudentDto(student.getId(),
                        student.getName(),
                        student.getEmail()))
                .toList();
        return studentDtoList;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found:"));
        return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto) {
        Student newStudent = modelMapper.map(addStudentRequestDto,Student.class);
        Student student = studentRepo.save(newStudent);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
      if(!studentRepo.existsById(id))
      {
          throw new IllegalArgumentException("Student from this id is not exist");
      }
      studentRepo.deleteById(id);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found:"));
        modelMapper.map(addStudentRequestDto, student);
         student = studentRepo.save(student);
         return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public StudentDto updatePartialStudent(Long id, Map<String, Object> update) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found:"));

         update.forEach((field,value) -> {
             switch (field)
             {
                 case "name" :
                     student.setName((String) value);
                     break;
                 case "email" :
                     student.setEmail((String) value);
                     break;
                 default:
                     throw new IllegalArgumentException("key is not supported");
             }
         });
        Student newStudent = studentRepo.save(student);
        return modelMapper.map(newStudent,StudentDto.class);
    }

}
