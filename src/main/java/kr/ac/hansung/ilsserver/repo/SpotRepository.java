package kr.ac.hansung.ilsserver.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.ilsserver.model.Spot;

@Repository
public interface SpotRepository extends PagingAndSortingRepository<Spot, Long> {

	@Query("from Spot where latitude > :minLatitude and latitude < :maxLatitude and longitude > :minLongitude and longitude < :maxLongitude")
	public List<Spot> findAllByLatLng(
			@Param("minLatitude") double minLatitude,
			@Param("maxLatitude") double maxLatitude,
			@Param("minLongitude") double minLongitude,
			@Param("maxLongitude") double maxLongitude);

	public long countByBuildingContains(String building);
	public long countByFloor(int floor);
	public long countByBuildingContainsAndFloor(String building, int floor);

	public Page<Spot> findAllByBuildingContains(Pageable Pageable, String building);
	public Page<Spot> findAllByFloor(Pageable Pageable, int floor);
	public Page<Spot> findAllByBuildingContainsAndFloor(Pageable Pageable, String building, int floor);
}
