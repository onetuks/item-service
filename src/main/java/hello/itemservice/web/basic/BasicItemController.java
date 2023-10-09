package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping(path = "/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping(path = "/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping(path = "/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    //    @PostMapping(path = "/add")
    public String addItemV2(
            @ModelAttribute Item item, Model model
    ) {
        itemRepository.save(item);
        /**
         * @ModelAttribute 역할
         * 1. 요청 파라미터 처리
         *  - 객체 생성
         *  - 요청 파라미터 값을 프로퍼티 접근법 (setXXX)로 입력
         * 2. Model 추가
         *  - 모델에 @ModelAttribute 로 지정한 객체 자동으로 넣어줌
         *  - 모델에 데이터 담을 때는 name 속성을 활용 (만약에 name 속성과 변수명이 같다면 생략 가능)
         *
         *  ## 추가
         *  - 심지어 @ModelAttribute를 생략할 수도 있음
         */
        // model.addAttribute("item", item);
        return "basic/item";
    }

    @PostMapping(path = "/add")
    public String addItemV3(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 1000, 10));
        itemRepository.save(new Item("itemA", 1000, 20));
    }
}
