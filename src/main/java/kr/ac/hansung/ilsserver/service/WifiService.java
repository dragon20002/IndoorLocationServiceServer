package kr.ac.hansung.ilsserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.ilsserver.model.Wifi;
import kr.ac.hansung.ilsserver.repo.WifiRepository;

@Service
public class WifiService {

	@Autowired
	private WifiRepository wifiRepo;
	
	public List<Wifi> getWifiListBySpotId(long id) {
		return wifiRepo.findAllBySpotId(id);
	}

}
