package kr.ac.hansung.ilsserver.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import kr.ac.hansung.ilsserver.model.Detail;
import kr.ac.hansung.ilsserver.model.Spot;
import kr.ac.hansung.ilsserver.model.Wifi;
import kr.ac.hansung.ilsserver.service.SpotService;
import kr.ac.hansung.ilsserver.service.WifiService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/spots")
public class SpotRestController {

	@Autowired
	private SpotService spotService;

	@Autowired
	private WifiService wifiService;
	
	@Autowired
	private ServletContext context;

	/* GET */
	@GetMapping(value = "/num/{building}/{floor}")
	public ResponseEntity<Long> getNumOfSpots(
			@PathVariable("building") String building,
			@PathVariable("floor") int floor) {

		long numOfSpots = spotService.getNumOfSpots(building, floor);
		return new ResponseEntity<>(numOfSpots, HttpStatus.OK);
	}

	/**
	 * @param page : 시작 페이지 0부터 시작 (zero indexed)
	 * @param size : 한번에 읽을 개수
	 * @return     : 장소목록
	 */
	@GetMapping(value = "/page/{page}/{size}/{building}/{floor}")
	public ResponseEntity<List<Spot>> getSpots(
			@PathVariable("page") int page,
			@PathVariable("size") int size,
			@PathVariable("building") String building,
			@PathVariable("floor") int floor) {

		if (page < 0 || size < 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		List<Spot> spotList;
		spotList = spotService.getSpotList(page, size, building, floor);

		if (spotList == null || spotList.size() < 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(spotList, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Spot> getSpot(@PathVariable("id") long id) {
		Spot spot = spotService.getSpotById(id);
		if (spot == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		// Spot 하나만 필요한 경우 Wi-Fi 목록은 잘 사용하지 않는다.
		// 트래픽 감소를 위해 Wi-Fi 목록은 보내지 않음
		spot.setWifi(0);
		spot.setWifiList(new ArrayList<>());

		return new ResponseEntity<>(spot, HttpStatus.OK);
	}

	@GetMapping(value = "/loc/{lat}/{lng}/") //실수를 전달하면 lng의 . 이후를 무시해버리므로 /를 추가함...
	public ResponseEntity<List<Spot>> getSpotsNearby(@PathVariable("lat") double lat,
			@PathVariable("lng") double lng) {

		List<Spot> spotList = spotService.getSpotListByLoc(lat, lng);
		if (spotList == null || spotList.size() < 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(spotList, HttpStatus.OK);
	}
	
	/* POST */
	@PostMapping(value = "")
	public ResponseEntity<Spot> addSpot(UriComponentsBuilder ucBuilder,
			@RequestBody Spot spot) {
		// 추가정보 입력
		spot.setCreateDate(new Date());

		// 1:다 설정
		List<Wifi> wifiList = spot.getWifiList();
		if (wifiList != null && wifiList.size() > 0) {
			for (Wifi data : wifiList)
				data.setSpot(spot);
			spot.setWifi(wifiList.size());
			spot.setSample(spot.getWifiList().get(0).getLevels().size());
		}

		System.out.println("Registration request from '" + spot.getUsername() + "' (samples : " + spot.getSample()
				+ " / wifi : " + spot.getWifi() + ")");

		spot = spotService.addSpot(spot);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("spots").buildAndExpand(spot.getId()).toUri());

		return new ResponseEntity<>(spot, headers, HttpStatus.CREATED);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<Spot> addSpotDetail(UriComponentsBuilder ucBuilder,
			@PathVariable("id") long id,
			@RequestBody Detail detail) {

		Spot spot = spotService.getSpotById(id);

		// update Spot detail
		spot.setDetail(detail);

		spotService.addSpot(spot); //db update

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("spots/{id}").buildAndExpand(spot.getId()).toUri());

		return new ResponseEntity<>(spot, headers, HttpStatus.CREATED);
	}

	@PostMapping(value = "/images")
	public ResponseEntity<Void> uploadImage(UriComponentsBuilder ucBuilder,
			@RequestParam("imageFile") MultipartFile imageFile) {

		// save image
		if (imageFile != null && !imageFile.isEmpty()) {
			//.. check directory exist
			String realPath = context.getRealPath("/");
			String saveDir = "files/images/";
			File dir = new File(realPath + saveDir);
			if (!dir.exists())
				dir.mkdirs();

			//.. copy image
			try {
				String imageName = imageFile.getOriginalFilename();
				imageFile.transferTo(new File(realPath + saveDir + imageName));
				System.out.println(imageName);
				System.out.println(imageFile.getSize());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/file")
	public ResponseEntity<String> createTextFile(
			@RequestBody List<Spot> spots) {

		String content = "";
		for (Spot spot : spots) {
			content += String.format("%f %f %d\r\n", spot.getLatitude(), spot.getLongitude(), spot.getWifi());
			for (Wifi wifi : spot.getWifiList()) {
				content += String.format("%s %s %d %d\r\n",
						wifi.getBssid(), wifi.getSsid(), wifi.getAverage(), wifi.getVariance());
			}
		}

		// check directory exist
		String realPath = context.getRealPath("/");
		String saveDir = "files/texts/";
		File dir = new File(realPath + saveDir);
		if (!dir.exists())
			dir.mkdirs();

		// 파일명 생성
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		String date = new SimpleDateFormat("yyyy.MM.dd aa hh.mm.ss").format(new Date());
		String textName = String.format("%s %s.txt", sessionId, date);

		// write spots
		try {
			File textFile = new File(realPath + saveDir + textName);
			textFile.deleteOnExit();
			PrintWriter writer = new PrintWriter(new FileOutputStream(textFile));
			writer.print(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(textName, HttpStatus.OK);
	}

	/* PUT */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Long> editSpot(UriComponentsBuilder ucBuilder, @RequestBody Spot spot) {
		Spot targetSpot = spotService.getSpotById(spot.getId());

		targetSpot.setUsername(spot.getUsername());
		targetSpot.setBuilding(spot.getBuilding());
		targetSpot.setFloor(spot.getFloor());
		targetSpot.setName(spot.getName());
		targetSpot.setCreateDate(new Date());

		// 추가정보 입력
		spot.setCreateDate(new Date());

		// 1:다 설정
		List<Wifi> wifiList = wifiService.getWifiListBySpotId(spot.getId());
		if (wifiList != null && wifiList.size() > 0) {
			for (Wifi data : wifiList)
				data.setSpot(spot);
			spot.setWifi(wifiList.size());
			spot.setSample(spot.getWifiList().get(0).getLevels().size());
		}

		System.out.println("Modify request from '" + spot.getUsername() + "' (samples : " + spot.getSample()
				+ " / wifi : " + spot.getWifi() + ")");

		long id = spotService.addSpot(spot).getId();

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("spots").buildAndExpand(spot.getId()).toUri());

		return new ResponseEntity<>(id, headers, HttpStatus.OK);
	}

	/* DELETE */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Long> deleteSpot(@PathVariable long id) {
		Spot spot = spotService.getSpotById(id);
		
		//.. check directory exist
		String realPath = context.getRealPath("/");
		String saveDir = "files/images/";
		File dir = new File(realPath + saveDir);
		if (!dir.exists())
			dir.mkdirs();

		if (spot.getImageName() != null)
			new File(realPath + saveDir + spot.getImageName()).delete();

		Detail detail = spot.getDetail();
		if (detail != null && spot.getDetail().getImageName() != null)
			new File(realPath + saveDir + detail.getImageName()).delete();

		spotService.deleteSpot(id);

		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{building}/{floor}")
	public ResponseEntity<Integer> deleteSpots(
			@PathVariable("building") String building,
			@PathVariable("floor") int floor) {

		List<Spot> spotList;
		Long size = getNumOfSpots(building, floor).getBody();
		spotList = spotService.getSpotList(0, size.intValue(), building, floor);

		if (spotList == null || spotList.size() < 1)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		for (Spot spot : spotList) {
			//.. check directory exist
			String realPath = context.getRealPath("/");
			String saveDir = "files/images/";
			File dir = new File(realPath + saveDir);
			if (!dir.exists())
				dir.mkdirs();
	
			if (spot.getImageName() != null)
				new File(realPath + saveDir + spot.getImageName()).delete();
	
			Detail detail = spot.getDetail();
			if (detail != null && spot.getDetail().getImageName() != null)
				new File(realPath + saveDir + detail.getImageName()).delete();
	
			spotService.deleteSpot(spot.getId());
		}
		return new ResponseEntity<>(spotList.size(), HttpStatus.OK);
	}

}
