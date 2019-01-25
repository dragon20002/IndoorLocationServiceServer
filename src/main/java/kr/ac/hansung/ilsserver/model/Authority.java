package kr.ac.hansung.ilsserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = -2978928924253482316L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String username;

	private String authority;

	public Authority(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

}