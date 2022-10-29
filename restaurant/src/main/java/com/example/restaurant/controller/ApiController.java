package com.example.restaurant.controller;

import com.example.restaurant.wishList.service.WishListService;
import com.example.restaurant.wishList.dto.WishListEntityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class ApiController {

    private final WishListService wishListService;

    @GetMapping("/search")
    public WishListEntityDto search(@RequestParam String query){
        return wishListService.search(query);
    }

    @PostMapping("")
    public WishListEntityDto add(@RequestBody WishListEntityDto wishListEntityDto){
        log.info("{}", wishListEntityDto);

        return wishListService.add(wishListEntityDto);
    }

    @GetMapping("/all")
    public List<WishListEntityDto> findAll(){
        return wishListService.findAll();
    }

    @DeleteMapping("/{index}")
    public void delete(@PathVariable int index){
        wishListService.delete(index);
    }
    @PostMapping("/{index}")
    public void addVisit(@PathVariable int index){
        wishListService.addVisit(index);
    }
}
