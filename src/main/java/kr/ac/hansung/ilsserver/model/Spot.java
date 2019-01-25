package kr.ac.hansung.ilsserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "spot")
public class Spot implements Serializable {

	private static final long serialVersionUID = -4184150318691975049L;

	@Id
	@GeneratedValue
	private long id;

	private String username;

	private String building;
	private int floor;
	private String name;
	private String imageName;

	@JsonFormat(pattern = "yyyyMMdd'T'HH:mm:ss.SSSXXX")
	private Date createDate;
	private int priority;
	@NotNull
	private int sample;
	@NotNull
	private int wifi;
	private String direction;
	private int type;
	@NotNull
	private double latitude;
	@NotNull
	private double longitude;

	@JsonManagedReference
	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Wifi> wifiList = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Detail detail;

	public Spot(long id, double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
