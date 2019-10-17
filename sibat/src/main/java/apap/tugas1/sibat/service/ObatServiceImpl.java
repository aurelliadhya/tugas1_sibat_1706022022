package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObatServiceImpl implements ObatService{
    @Autowired
    private ObatDb obatDb;

    @Override
    public List<ObatModel> getObatList() {
        return obatDb.findAll();
    }

    @Override
    public void addObat(ObatModel obat) {
        String kode = "";
        if (obat.getJenis().getNama().equals("Generik")) {
            kode += "1";
        } else {
            kode += "2";
        }

        if (obat.getBentuk().equals("Cairan")) {
            kode += "01";
        } else if (obat.getBentuk().equals("Kapsul")) {
            kode += "02";
        } else {
            kode += "03";
        }

        kode += "20192024";
        obat.setKode(kode);
        obatDb.save(obat);
    }
}
