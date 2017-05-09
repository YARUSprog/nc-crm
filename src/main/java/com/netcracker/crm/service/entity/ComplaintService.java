package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.dto.ComplaintDto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2017
 */
public interface ComplaintService {

    public Complaint persist(ComplaintDto dto);

    public List<Complaint> findByTitle(String title);

    public List<Complaint> findByDate(LocalDate date);

    public List<Complaint> findByCustomerId(Long id);

    public Complaint findById(Long id);


}
