package jpabook.jpashop.domain.exception;

/**
 * 재고 부족 예외
 */
@SuppressWarnings("serial")
public class NotEnoughStockException extends RuntimeException {
	public NotEnoughStockException() {}
	
	public NotEnoughStockException(String msg){
		super(msg);
	}
	public NotEnoughStockException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	public NotEnoughStockException(Throwable cause){
		super(cause);
	}
}
