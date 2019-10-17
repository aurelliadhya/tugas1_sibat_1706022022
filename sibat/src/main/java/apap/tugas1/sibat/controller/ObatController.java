package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.model.SupplierModel;
import apap.tugas1.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @RequestMapping("/")
    public String beranda(Model model) {
        List<ObatModel> listObat = obatService.getObatList();
        model.addAttribute("obatList", listObat);
        return "beranda";
    }

    // URL mapping untuk mengakses halaman tambah obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String addObatFormPage(Model model) {
        ObatModel obatBaru = new ObatModel();
        model.addAttribute("obat", obatBaru);
        return "form-tambah-obat";
    }

    // URL mapping untuk submit form yang telah diisi user
    @RequestMapping(value = "/obat/", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute ObatModel obat, Model model) {
        System.out.println(obat.getBentuk() + obat.getKode() + obat.getNama());
        obatService.addObat(obat);
        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        return "tambah-obat-sukses";
    }
}
