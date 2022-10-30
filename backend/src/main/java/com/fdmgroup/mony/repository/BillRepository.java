package com.fdmgroup.mony.repository;

import com.fdmgroup.mony.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findBillsByLedgerId(long id);

}
