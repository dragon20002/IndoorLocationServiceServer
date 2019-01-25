package kr.ac.hansung.ilsserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kr.ac.hansung.ilsserver.model.Spot;
import kr.ac.hansung.ilsserver.model.Wifi;
import kr.ac.hansung.ilsserver.repo.SpotRepository;
import kr.ac.hansung.ilsserver.repo.WifiRepository;
import kr.ac.hansung.ilsserver.util.ILSUtils;

@Service
public class SpotService {
	private static String NULL = "NULL";
	private static final int SEARCH_RANGE = 100; // 탐색범위(m)

	@Autowired
	private SpotRepository spotRepo;
	
	@Autowired
	private WifiRepository wifiRepo;

	public long getNumOfSpots(String building, int floor) {
		long count;
		
		if (building.equals(NULL)) {
			if (floor == 0)
				count = spotRepo.count();
			else
				count = spotRepo.countByFloor(floor);
		} else {
			if (floor == 0)
				count = spotRepo.countByBuildingContains(building);
			else
				count = spotRepo.countByBuildingContainsAndFloor(building, floor);
		}

		return count;
	}

	public Spot getSpotById(long id) {
		return spotRepo.findById(id).get();
	}

	public Spot getSpotIdByBssid(String bssid) {
		List<Wifi> wifiList = wifiRepo.findAllByBssid(bssid);
		if (wifiList.size() == 0)
			return null;
		return wifiList.get(0).getSpot();
	}

	public List<Spot> getSpotList(int page, int size, String building, int floor) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.DESC, "id");
		
		Page<Spot> pageOfSpots;

		if (building.equals(NULL)) {
			if (floor == 0)
				pageOfSpots = spotRepo.findAll(pageRequest);
			else
				pageOfSpots = spotRepo.findAllByFloor(pageRequest, floor);
		} else {
			if (floor == 0)
				pageOfSpots = spotRepo.findAllByBuildingContains(pageRequest, building);
			else
				pageOfSpots = spotRepo.findAllByBuildingContainsAndFloor(pageRequest, building, floor);
		}

		return pageOfSpots.getContent();
	}

	public List<Spot> getSpotListByLoc(double latitude, double longitude) {
		// 현재 지점으로부터 45도, -135도로 100m 떨어진 곳의 latlng 계산
		double[] latlngBL = ILSUtils.getDestinationLatLng(latitude, longitude, -135, SEARCH_RANGE);
		double[] latlngTR = ILSUtils.getDestinationLatLng(latitude, longitude, 45, SEARCH_RANGE);

		// 대각선 200m 사각형 범위 내의 Spot 검색
		List<Spot> spotList = spotRepo.findAllByLatLng(latlngBL[0], latlngTR[0], latlngBL[1], latlngTR[1]);

		return spotList;
	}
	
	public Spot addSpot(Spot spot) {
		return spotRepo.save(spot);
	}
	
	public void deleteSpot(long id) {
		spotRepo.deleteById(id);
	}

}