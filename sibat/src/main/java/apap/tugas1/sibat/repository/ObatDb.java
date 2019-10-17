package apap.tugas1.sibat.repository;

import apap.tugas1.sibat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObatDb extends JpaRepository<ObatModel, Long> {
    Optional<ObatModel> findByIdObat(Long idObat);
}
