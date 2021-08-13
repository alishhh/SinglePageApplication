package com.example.single_page_application.service;

import com.example.single_page_application.model.Detail;
import com.example.single_page_application.repository.DetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DetailService implements IDetailService {

    private final DetailRepository detailRepository;

    @Override
    public List<Detail> getAllDetails() {
        return detailRepository.findAll();
    }

    @Override
    public Detail getDetailById(Long id) {
        return detailRepository.getById(id);
    }

    @Override
    public Detail createDetail(Detail detail) {
        Detail newDetail = new Detail();
        newDetail.setName(detail.getName());
        newDetail.setParentId(detail.getParentId());
        newDetail.setPrice(detail.getPrice());
        newDetail.setCount(detail.getCount());
        newDetail.setQuantityCost(detail.getPrice() * detail.getCount());
        changeParent(newDetail.getParentId(), newDetail.getPrice());
        return detailRepository.save(newDetail);
    }

    @Override
    public void addCountDetail(Long currentId) {
        Detail existingDetail = detailRepository.getById(currentId);
        existingDetail.setCount(existingDetail.getCount() + 1);
        existingDetail.setQuantityCost(existingDetail.getPrice() * existingDetail.getCount());
        changeParent(existingDetail.getParentId(), existingDetail.getPrice());
        detailRepository.save(existingDetail);
    }

    @Override
    public void changeParent(Long parentId, Double addPrice) {
        if (parentId == null || parentId == 0) {
            return;
        }
        Detail parent = detailRepository.getById(parentId);
        parent.setPrice(parent.getPrice() + addPrice);
        parent.setQuantityCost(parent.getPrice() * parent.getCount());
        detailRepository.save(parent);
        changeParent(parent.getParentId(), addPrice);
    }

    @Override
    public void deleteDetail(Long id) {
        detailRepository.deleteById(id);
    }
}
