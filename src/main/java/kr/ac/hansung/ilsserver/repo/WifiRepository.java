package kr.ac.hansung.ilsserver.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.ilsserver.model.Wifi;

@Repository
public interface WifiRepository extends CrudRepository<Wifi, Long> {

	public List<Wifi> findAllBySpotId(long spotId);
	
	public List<Wifi> findAllByBssid(String bssid);

}
