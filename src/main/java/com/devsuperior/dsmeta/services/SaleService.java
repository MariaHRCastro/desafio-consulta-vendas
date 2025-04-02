package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> findAll(String name, String minDate, String maxDate, Pageable pageable) {
		LocalDate finalMinDate = LocalDate.now();
		LocalDate finalMaxDate = LocalDate.now();
		
		if(maxDate.isEmpty()) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			finalMaxDate = today;
		}else {
			finalMaxDate = LocalDate.parse(maxDate);
		}
		
		if(minDate.isEmpty()) {
			LocalDate result = finalMinDate.minusYears(1L);
			finalMinDate = result;
		}else {
			finalMinDate = LocalDate.parse(minDate);
		}
		
		Page<SaleMinDTO> result = repository.searchBySeller(finalMinDate, finalMaxDate, name, pageable);
		return result;
	}
	
	public Page<SellerMinDTO> filterSumByDate(String minDate, String maxDate, Pageable pageable){
		LocalDate finalMinDate = LocalDate.now();
		LocalDate finalMaxDate = LocalDate.now();
		
		if(maxDate.isEmpty()) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			finalMaxDate = today;
		}else {
			finalMaxDate = LocalDate.parse(maxDate);
		}
		
		if(minDate.isEmpty()) {
			LocalDate result = finalMinDate.minusYears(1L);
			finalMinDate = result;
		}else {
			finalMinDate = LocalDate.parse(minDate);
		}
		
		Page<SellerMinDTO> result = repository.searchSumBySeller(finalMinDate, finalMaxDate, pageable);
		
		return result;
		
	}
	
}
