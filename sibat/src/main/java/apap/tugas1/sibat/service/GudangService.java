package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.ObatModel;

import java.util.List;
import java.util.Optional;

public interface GudangService {
    List<GudangModel> getGudangList();

    Optional<GudangModel> getGudangByIdGudang(Long idGudang);

    void addGudang(GudangModel gudang);

    void deleteGudang(Long idGudang);
}
