package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.repository.GudangObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GudangObatServiceImpl implements GudangObatService {
    @Autowired
    private GudangObatDb gudangObatDb;

    @Override
    public void addGudangObat(GudangObatModel gudangObat) {
        gudangObatDb.save(gudangObat);
    }

    @Override
    public List<GudangObatModel> filterObatExpired(List<GudangObatModel> listGudangObat) {
        List<GudangObatModel> listObatExpired = new ArrayList<>();
        LocalDate hariIni = LocalDate.now();
        Integer tahunIni = hariIni.getYear();

        for (GudangObatModel obat : listGudangObat) {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy");
            Integer tahunObat = Integer.valueOf(formater.format(obat.getObat().getTanggalTerbit()));

            if ((tahunIni - tahunObat) > 5) {
                listObatExpired.add(obat);
            }
        }
        return listObatExpired;
    }
}
