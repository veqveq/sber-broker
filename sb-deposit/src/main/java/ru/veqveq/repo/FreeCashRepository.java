package ru.veqveq.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veqveq.model.FreeCash;

import java.util.Optional;

@Repository
public interface FreeCashRepository extends JpaRepository<FreeCash, Long> {
    Optional<FreeCash> getByAccount_Id(Long accountId);
}
