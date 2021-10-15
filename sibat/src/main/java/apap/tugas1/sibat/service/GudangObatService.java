package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangObatModel;

import java.util.List;

public interface GudangObatService {
    void addGudangObat(GudangObatModel gudangObat);

    List<GudangObatModel> filterObatExpired(List<GudangObatModel> listGudangObat);
}
