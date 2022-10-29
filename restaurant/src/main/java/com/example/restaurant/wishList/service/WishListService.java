package com.example.restaurant.wishList.service;

import com.example.restaurant.Entity.repository.WishListRepository;
import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishList.WishListEntity;
import com.example.restaurant.wishList.dto.WishListEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;



    public WishListEntityDto search(String query){
        // 지역검색

        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes= naverClient.localSearch(searchLocalReq);

        if(searchLocalRes.getTotal() >0){

            var localItem = searchLocalRes.getItems().stream().findFirst().get();
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>","");
            var searchImageReq = new SearchImageReq();
            searchLocalReq.setQuery(imageQuery);

            //이미지 검색
            var searchImageRes = naverClient.imageSearch(searchImageReq);

            if(searchImageRes.getTotal() >0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();
                //결과출력

                var result = new WishListEntityDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;

                }

            }

        return new WishListEntityDto();
        }


    public WishListEntityDto add(WishListEntityDto wishListEntityDto) {

        var entity= dtoToEntity(wishListEntityDto);
        var saveEntity = wishListRepository.save(entity);
        return entityToDto(saveEntity);

    }

    private WishListEntity dtoToEntity(WishListEntityDto wishListEntityDto){
        var entity = new WishListEntity();
        entity.setIndex(wishListEntityDto.getIndex());
        entity.setTitle(wishListEntityDto.getTitle());
        entity.setCategory(wishListEntityDto.getCategory());
        entity.setAddress(wishListEntityDto.getAddress());
        entity.setRoadAddress(wishListEntityDto.getRoadAddress());
        entity.setHomePageLink(wishListEntityDto.getHomePageLink());
        entity.setImageLink(wishListEntityDto.getImageLink());
        entity.setVisit(wishListEntityDto.isVisit());
        entity.setVisitCount(wishListEntityDto.getVisitCount());
        entity.setLastVisitDate(wishListEntityDto.getLastVisitDate());
        return entity;
    }
    private WishListEntityDto entityToDto(WishListEntity wishListEntity){
        var dto = new WishListEntityDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setCategory(wishListEntity.getCategory());
        dto.setAddress(wishListEntity.getAddress());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setHomePageLink(wishListEntity.getHomePageLink());
        dto.setImageLink(wishListEntity.getImageLink());
        dto.setVisit(wishListEntity.isVisit());
        dto.setVisitCount(wishListEntity.getVisitCount());
        dto.setLastVisitDate(wishListEntity.getLastVisitDate());
        return dto;
    }

    public List<WishListEntityDto> findAll(){
        return wishListRepository.listAll().stream().map(it->entityToDto(it)).collect(Collectors.toList());
    }

}




