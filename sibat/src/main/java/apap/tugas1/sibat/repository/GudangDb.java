package apap.tugas1.sibat.repository;

import apap.tugas1.sibat.model.GudangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GudangDb extends JpaRepository<GudangModel, Long> {
    Optional<GudangModel> findByIdGudang(Long idGudang);
}
