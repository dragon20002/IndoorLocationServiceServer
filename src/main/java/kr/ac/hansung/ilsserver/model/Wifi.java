package kr.ac.hansung.ilsserver.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wifi")
public class Wifi implements Serializable {

	private static final long serialVersionUID = -2910545688484748952L;
	
	@Id
	@GeneratedValue
	private long id; 
	
	private String bssid;
    private String ssid;
    private int average;
    private int variance;
    
    @Transient
    private List<Integer> levels;

    @ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="spot_id")
    @JsonBackReference
    private Spot spot;
   
}
