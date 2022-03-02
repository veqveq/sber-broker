package ru.veqveq.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veqveq.model.Profit;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {
}
