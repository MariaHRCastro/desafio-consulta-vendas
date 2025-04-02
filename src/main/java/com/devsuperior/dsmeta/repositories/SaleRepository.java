package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	
	/*
	SELECT tbv.id, tbv.amount, tbv.date, sel.name FROM tb_sales tbv 
	JOIN tb_seller sel  ON  tbv.seller_id = sel.id
	WHERE tbv.date between '2022-05-01' AND '2022-05-31'
	AND UPPER(sel.name) LIKE UPPER('%odinson%')
	 */
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, obj.seller.name) FROM Sale obj "
			+ " WHERE obj.date >= :minDate AND obj.date <= :maxDate "
			+ " AND UPPER(obj.seller.name) LIKE UPPER (CONCAT ('%', :name, '%')) ")
	Page<SaleMinDTO> searchBySeller(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	
	/*
	SELECT name, ROUND(SUM(amount),2) as TOTAL FROM tb_sales 
	JOIN tb_seller ON tb_seller.id = tb_sales.seller_id
	WHERE tb_sales.date BETWEEN '2022-01-01' AND '2022-06-30' 
	GROUP BY name
	*/
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj "
			+ " WHERE obj.date >= :minDate AND obj.date <= :maxDate "
			+ " GROUP BY obj.seller.name ")
	Page<SellerMinDTO> searchSumBySeller(LocalDate minDate, LocalDate maxDate, Pageable pageable);

	
}
