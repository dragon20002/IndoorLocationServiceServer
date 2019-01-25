package kr.ac.hansung.ilsserver.exception;

import kr.ac.hansung.ilsserver.model.Detail;
import kr.ac.hansung.ilsserver.model.Spot;

public class SpotNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7231891739604302678L;
	
	private double latitude;
	private double longitude;

	public SpotNotFoundException(Spot spotInfo) {
		super();
		latitude = spotInfo.getLatitude();
		longitude = spotInfo.getLongitude();
	}
	
	public SpotNotFoundException(Detail detail) {
		super();
	} 
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
