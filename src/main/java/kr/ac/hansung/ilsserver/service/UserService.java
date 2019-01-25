package kr.ac.hansung.ilsserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.ac.hansung.ilsserver.model.User;
import kr.ac.hansung.ilsserver.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	public void createOrUpdate(User user) {
		repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("등록되지 않은 아이디입니다.");

		return user;
	}

	public List<User> findAll() {
		return (List<User>) repository.findAll();
	}

	public User findOne(String username) {
		return repository.findByUsername(username);
	}

	public void delete(String username) {
		repository.deleteByUsername(username);
	}

	public void deleteAll() {
		repository.deleteAll();
	}

	public boolean exists(String username) {
		return repository.existsByUsername(username);
	}

}
