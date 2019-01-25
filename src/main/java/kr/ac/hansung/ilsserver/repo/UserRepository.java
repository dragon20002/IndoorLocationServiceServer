package kr.ac.hansung.ilsserver.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.ilsserver.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	User findByUsername(String username);

	void deleteByUsername(String username);

	boolean existsByUsername(String username);
}
