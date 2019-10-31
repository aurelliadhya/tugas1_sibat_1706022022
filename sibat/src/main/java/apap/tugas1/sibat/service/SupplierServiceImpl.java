package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.SupplierModel;
import apap.tugas1.sibat.repository.SupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDb supplierDb;

    @Override
    public List<SupplierModel> getSupplierList() {
        return supplierDb.findAll();
    }

    @Override
    public Optional<SupplierModel> getSupplierByIdSupplier(Long idSupplier) {
        return supplierDb.findByIdSupplier(idSupplier);
    }
}
