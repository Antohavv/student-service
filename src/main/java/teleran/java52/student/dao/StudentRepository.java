package teleran.java52.student.dao;


import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import teleran.java52.student.model.Student;

public interface StudentRepository extends MongoRepository<Student, Long> {
	Stream<Student> getAllBy();
	
	Stream<Student> findByNameIgnoreCase(String name);
	
	Long countStudentsByNameInIgnoreCase(Set<String> names);
		 
	@Query("{'scores.?0': {$gt: ?1}}")
	Stream<Student> findByExamAndScoreGreaterThen(String exam, int score);
	
	
	

}
