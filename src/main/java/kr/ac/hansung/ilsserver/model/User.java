package kr.ac.hansung.ilsserver.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = -8702447741453239660L;
	public static final int SCORE_MAXIMUM = 40;
	public static final int SCORE_INITIAL = 15;
	public static final int SCORE_DISABLE = 0;

	@Id
	@Size(min = 1, message = "아이디를 입력해주세요")
	private String username;
	@Size(min = 1, message = "비밀번호를 입력해주세요")
	private String password;

	private int score;

	@OneToMany(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Authority> authorities;

	public User(String username, String password, int score, List<Authority> authorities) {
		this.username = username;
		this.password = password;
		this.score = score;
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return score > SCORE_DISABLE;
	}
	
}
