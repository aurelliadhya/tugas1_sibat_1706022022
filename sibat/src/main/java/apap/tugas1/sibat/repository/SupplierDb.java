package apap.tugas1.sibat.repository;

import apap.tugas1.sibat.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierDb extends JpaRepository<SupplierModel, Long> {
    Optional<SupplierModel> findByIdSupplier(Long idSupplier);
}
