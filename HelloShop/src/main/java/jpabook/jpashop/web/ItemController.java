package jpabook.jpashop.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;

@Controller
@RequestMapping("/itmes")
public class ItemController {
	@Autowired ItemService itemService;
	
	/** 상품 등록 페이지 진입
	 * @return
	 */
	@RequestMapping(value = "/new",method = RequestMethod.GET)
	public String createForm() {
		return "items/createItemForm";
	}
	
	/** 상품 등록
	 * @param book
	 * @return
	 */
	@PostMapping("/new")
	public String create(@ModelAttribute Book book) {
		itemService.saveItem(book);
		return "redirect:/items";
	}
	
	/** 상품 목록 조회
	 * @param model
	 * @return
	 */
	@GetMapping("")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		return "items/itemList";
	}
	
	@GetMapping("/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Item item = itemService.fineOne(itemId);
		model.addAttribute("item", item);
		return"items/updateItemForm";
	}
	
	@PostMapping("/{itemId}/edit")
	public String updateItem(@ModelAttribute("item") Book item) { //form에서 만들어온 엔티티 인스턴스임 -> 준영속 상태
		itemService.saveItem(item);
		return "redirect:/items";
	}
}
