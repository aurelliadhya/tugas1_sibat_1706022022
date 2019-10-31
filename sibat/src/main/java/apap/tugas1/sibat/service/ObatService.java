package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;

import java.util.List;
import java.util.Optional;

public interface ObatService {
    List<ObatModel> getObatList();

    void addObat(ObatModel obat);

    Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi);

    Optional<ObatModel> getObatByIdObat(Long idObat);

    ObatModel ubahObat(ObatModel obatModel);
}
