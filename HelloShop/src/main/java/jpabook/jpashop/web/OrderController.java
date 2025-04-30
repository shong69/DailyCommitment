package jpabook.jpashop.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.Service.OrderService;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.entity.item.Item;

@RequestMapping("/order")
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired ItemService itemService;
	@Autowired MemberService memberService;
	
	@GetMapping("")
	public String createForm(Model model) {
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();
		
		model.addAttribute("members",members);
		model.addAttribute("items", items);
		
		return "/orderForm";
	}
	
	@PostMapping("")
	public String order(@RequestParam("memberId") Long memberId,
							@RequestParam("itemId") Long itemId,
							@RequestParam("count") int count) {
		orderService.order(memberId, itemId, count);
		return"redirect:/orders";
	}
	
}
