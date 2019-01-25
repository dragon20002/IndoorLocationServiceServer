package kr.ac.hansung.ilsserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.ac.hansung.ilsserver.exception.DetailNotFoundException;
import kr.ac.hansung.ilsserver.exception.ErrorResponse;
import kr.ac.hansung.ilsserver.exception.SpotNotFoundException;
import kr.ac.hansung.ilsserver.model.Spot;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(SpotNotFoundException.class)
	public ResponseEntity<Spot> handleSpotNotFoundException(SpotNotFoundException ex) {

		System.out.println("There are no registed point around coordinate : ("
				+ ex.getLatitude() + ", " + ex.getLongitude() + ")");	
	
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("point.notfound.exception");
		errorResponse.setErrorMsg("Point is not found.");
		
		return new ResponseEntity<>(new Spot(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(DetailNotFoundException.class)
	public ResponseEntity<Spot> handleDetailNotFoundException(DetailNotFoundException ex) {

		System.out.println("There is no detail infomation corresponding to " + ex.getId());	
	
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode("detail.notfound.exception");
		errorResponse.setErrorMsg("Detail is not found.");
		
		return new ResponseEntity<>(new Spot(), HttpStatus.NO_CONTENT);
	}
	
}
