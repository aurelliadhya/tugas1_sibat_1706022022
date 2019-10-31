package apap.tugas1.sibat.repository;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GudangObatDb extends JpaRepository<GudangObatModel, Long> {
    List<GudangObatModel> getGudangByObat(ObatModel obat);
    List<GudangObatModel> getObatByGudang(GudangModel gudang);
}
