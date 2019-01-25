package kr.ac.hansung.ilsserver.exception;

public class DetailNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -7203863079215581673L;

	private long id;
	
	public DetailNotFoundException(long id) {
		super();
		this.id = id;
		System.err.println("err: detail not found " + id);
	}

	public long getId() {
		return id;
	}
	
}
