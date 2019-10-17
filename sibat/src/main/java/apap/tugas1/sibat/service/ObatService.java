package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;

import java.util.List;

public interface ObatService {
    List<ObatModel> getObatList();

    void addObat(ObatModel obat);
}
