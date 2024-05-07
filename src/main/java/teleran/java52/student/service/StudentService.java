package teleran.java52.student.service;

import java.util.List;
import java.util.Set;

import teleran.java52.student.dto.ScoreSto;
import teleran.java52.student.dto.StudentAddDto;
import teleran.java52.student.dto.StudentDto;
import teleran.java52.student.dto.StudentUpdateDto;

public interface StudentService {
	Boolean addStudent(StudentAddDto stidentAddDto);

	StudentDto findStudent(Long id);

	StudentDto removeStudent(Long id);

	StudentAddDto updateStudent(Long id, StudentUpdateDto studentUpdateDto);

	Boolean addScore(Long id, ScoreSto scoreSto);
	
	List<StudentDto> findStudentsByName(String name);
	
	Long getStudentsNamesQuantity(Set<String> names);
	
	List<StudentDto> getStudentsByExamMinScore(String exam, Integer minScore);

}
