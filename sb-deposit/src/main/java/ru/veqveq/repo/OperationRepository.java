package ru.veqveq.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veqveq.model.Operation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByAccount_IdAndOperationDateBetween(Long accountId, LocalDate fromDate, LocalDate toDate);
}
