package kr.ac.hansung.ilsserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.ilsserver.model.Authority;
import kr.ac.hansung.ilsserver.model.User;
import kr.ac.hansung.ilsserver.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("")
	public ResponseEntity<Void> create(@RequestBody Map<String, String> requestBody) {
		String username = requestBody.get("username");
		String password = requestBody.get("password");

		// RequestBody 확인
		if (username == null || password == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		// 아이디 중복 확인
		if (service.exists(username))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);


		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(username, "ROLE_USER"));

		User user = new User(username, passwordEncoder.encode(password),
				User.SCORE_INITIAL, authorities);

		service.createOrUpdate(user);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("")
	public ResponseEntity<List<User>> readAll() {
		List<User> users = service.findAll();

		for (User user : users)
			user.setPassword(null);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> readOne(@PathVariable String username) {
		User user = service.findOne(username);
		if (user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		user.setPassword(null);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/{username}")
	public ResponseEntity<Void> update(@PathVariable String username, @RequestBody Map<String, String> requestBody) {

		User user = service.findOne(username);

		// 등록된 user인지 확인
		if (user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		String password = requestBody.get("password");

		// RequestBody 확인
		if (password == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		user.setPassword(password);

		service.createOrUpdate(user);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<Void> delete(@PathVariable String username) {
		if (!service.exists(username))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		service.delete(username);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Void> deleteAll() {

		service.deleteAll();

		return new ResponseEntity<>(HttpStatus.OK);
	}

}