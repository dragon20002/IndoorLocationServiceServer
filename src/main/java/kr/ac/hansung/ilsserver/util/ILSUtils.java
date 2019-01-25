package kr.ac.hansung.ilsserver.util;

public class ILSUtils {

	private static double earthR = 6378100; //m
    
	/**
	 * Vincenty Solution을 구현한 것
	 * @param lat       시작 위도
	 * @param lng       시작 경도
	 * @param bearing   시계방향 각도 (0도 이면 N, degree)
	 * @param dist      목적지까지의 거리
	 * @return latlng   목적지의 위도 경도. 0: 위도, 1: 경도
	 */
	public static double[] getDestinationLatLng(double lat, double lng, double bearing, int dist) {
		double[] latlng = new double[2]; //0 : lat, 1 : lng;
		double latInRad = Math.toRadians(lat);
		double lngInRad = Math.toRadians(lng);
		double bearingInRad = Math.toRadians(bearing);

		latlng[0] = Math.asin(Math.sin(latInRad) * Math.cos(dist / earthR) +
				Math.cos(latInRad) * Math.sin(dist / earthR) * Math.cos(bearingInRad));
		latlng[1] = lngInRad + Math.atan2(Math.sin(bearingInRad) * Math.sin(dist / earthR) * Math.cos(latInRad),
				Math.cos(dist / earthR) - Math.sin(latInRad) * Math.sin(latlng[0]));
		
		latlng[0] = Math.toDegrees(latlng[0]);
		latlng[1] = Math.toDegrees(latlng[1]);
		
		return latlng;
	}
}
