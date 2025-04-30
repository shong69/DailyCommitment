package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;

@Transactional
@SpringBootTest
//@ContextConfiguration(locations="classpath:appConfig.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
public class ItemServiceTest {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemService itemService;
	
	
	/**
	 * Item은 추상 클래스이기 때문에 하위 클래스를 사용해서 테스트 해야 한다.
	 * Item에 대한 공통 기능에 대한 테스트는 하나의 하위 클래스만을 사용해도 된다. 
	 */
	//지정한 예외가 발생해야 성공하는 테스트
	@Test 
	public void 중복_아이템_예외(){
		//Given
		Item item1 = new Book();
		item1.setName("어린왕자");
		
		Item item2 = new Book();
		item2.setName("어린왕자");
		
		//When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            itemService.saveItem(item1);
            itemService.saveItem(item2);
        });
	}
	
	
	
	
	
}
