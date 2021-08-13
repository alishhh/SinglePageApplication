package com.example.single_page_application.service;

import com.example.single_page_application.model.Detail;

import java.util.List;

public interface IDetailService {
    List<Detail> getAllDetails();
    Detail getDetailById(Long id);
    Detail createDetail(Detail detail);
    void addCountDetail(Long id);
    void deleteDetail(Long id);
    void changeParent(Long parentId, Double addPrice);
}
