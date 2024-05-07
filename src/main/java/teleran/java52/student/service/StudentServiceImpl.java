package teleran.java52.student.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import teleran.java52.student.dao.StudentRepository;
import teleran.java52.student.dto.ScoreSto;
import teleran.java52.student.dto.StudentAddDto;
import teleran.java52.student.dto.StudentDto;
import teleran.java52.student.dto.StudentUpdateDto;
import teleran.java52.student.dto.exception.StudentNotFoundException;
import teleran.java52.student.model.Student;

@Component
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	final ModelMapper modelMaper;
	final StudentRepository studentRepository;

	@Override
	public Boolean addStudent(StudentAddDto studentAddDto) {
		
		if (studentRepository.existsById(studentAddDto.getId())) {
			return false;
		}
//		Student student = new Student(studentAddDto.getId(), studentAddDto.getName(), studentAddDto.getPassword());
		Student student = modelMaper.map(studentAddDto, Student.class);
		studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Long id) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		//return new StudentDto(id, student.getName(), student.getScores());
		return modelMaper.map(student, StudentDto.class);
	}

	@Override
	public StudentDto removeStudent(Long id) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		studentRepository.deleteById(id);
		return modelMaper.map(student, StudentDto.class);
	}

	@Override
	public StudentAddDto updateStudent(Long id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		if (studentUpdateDto.getName() != null) {
			student.setName(studentUpdateDto.getName());
		}
		if (studentUpdateDto.getPassword() != null) {
			student.setPassword(studentUpdateDto.getPassword());
		}
		studentRepository.save(student);
		return modelMaper.map(student,StudentAddDto.class );
	}

	@Override
	public Boolean addScore(Long id, ScoreSto scoreSto) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		boolean res = student.addScore(scoreSto.getExamName(), scoreSto.getScore());
		studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		return studentRepository.findByNameIgnoreCase(name)										  
										  .map(s -> modelMaper.map(s, StudentDto.class)).collect(Collectors.toList());
	}

	@Override
	public Long getStudentsNamesQuantity(Set<String> names) {		
		return studentRepository.countStudentsByNameInIgnoreCase(names);
	}

	@Override
	public List<StudentDto> getStudentsByExamMinScore(String exam, Integer minScore) {

		return studentRepository.findByExamAndScoreGreaterThen(exam, minScore)
				.map(s -> modelMaper.map(s, StudentDto.class)).toList();
	}

}
