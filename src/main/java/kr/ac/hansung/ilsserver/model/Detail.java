package kr.ac.hansung.ilsserver.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "detail")
public class Detail implements Serializable {

	private static final long serialVersionUID = 3908275520918232270L;

	@Id
	@GeneratedValue()
	private long id;

    private String description;
    private String address;
    private String imageName;
    private String contact;
    private String url;
}
