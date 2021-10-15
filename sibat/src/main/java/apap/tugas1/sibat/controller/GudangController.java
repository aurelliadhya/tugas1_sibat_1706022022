package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.service.GudangObatService;
import apap.tugas1.sibat.service.GudangService;
import apap.tugas1.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    @Autowired
    private ObatService obatService;

    @Autowired
    private GudangObatService gudangObatService;

    @RequestMapping(value = "/gudang")
    public String gudang(Model model) {
        List<GudangModel> listGudang = gudangService.getGudangList();
        model.addAttribute("gudangList", listGudang);
        return "gudang";
    }

    @RequestMapping(path = "/gudang/view", method = RequestMethod.GET)
    public String viewGudang(@RequestParam(value = "idGudang") Long idGudang, Model model) {
        GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
        List<GudangObatModel> listGudangObat = gudang.getListGudangObat();
        Integer jumlahObat = listGudangObat.size();

        List<ObatModel> listObat = obatService.getObatList();

        model.addAttribute("gudang", gudang);
        model.addAttribute("jumlahObat", jumlahObat);
        model.addAttribute("gudangObatList", listGudangObat);
        model.addAttribute("obatList", listObat);
        return "lihat-gudang";
    }

    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.GET)
    public String addGudangFormPage(Model model) {
        GudangModel gudangBaru = new GudangModel();
        model.addAttribute("gudang", gudangBaru);
        return "form-tambah-gudang";
    }

    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.POST)
    public String addGudangSubmit(@ModelAttribute GudangModel gudang, Model model) {
        gudangService.addGudang(gudang);
        model.addAttribute("namaGudang", gudang.getNama());
        return "tambah-gudang-sukses";
    }

    @RequestMapping(value = "/gudang/hapus/{idGudang}", method = RequestMethod.GET)
    public String deleteGudang(@PathVariable(value = "idGudang") Long idGudang, Model model) {
        GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
        List<GudangObatModel> listGudangObat = gudang.getListGudangObat();
        model.addAttribute("namaGudang", gudang.getNama());

        if (listGudangObat.size() == 0) {
            gudangService.deleteGudang(idGudang);
            return "hapus-gudang-sukses";
        }
        return "hapus-gudang-gagal";
    }

    @RequestMapping(value = "/gudang/tambah-obat", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute GudangModel gudang, Model model, HttpServletRequest request) {
        Long idObat = Long.valueOf(request.getParameter("obat"));
        Long idGudang = Long.valueOf(request.getParameter("idGudang"));

        ObatModel obatModel = obatService.getObatByIdObat(idObat).get();
        GudangModel gudangModel = gudangService.getGudangByIdGudang(idGudang).get();

        // List semua obat yang ada
        List<ObatModel> listAllObat = obatService.getObatList();

        // List semua obat yang ada di gudang
        List<GudangObatModel> listGudangObat = gudangModel.getListGudangObat();
        List<ObatModel> listObatInGudang = new ArrayList<>();
        for (GudangObatModel obat : listGudangObat) {
            listObatInGudang.add(obat.getObat());
        }

        // List semua obat yang ada di gudang (duplikat)
        List<ObatModel> listObatBaru = new ArrayList<>(listObatInGudang);

        // Remove obat yang sudah ada di gudang
        listObatBaru.removeAll(listAllObat);

        model.addAttribute("gudang", gudangModel);
        model.addAttribute("listAllObat", listAllObat);
        model.addAttribute("gudangObatList", listObatBaru);

        if (listObatInGudang.contains(obatModel)) {
            model.addAttribute("gudang", gudangModel);
            model.addAttribute("gudangObatList", listGudangObat);
            return "lihat-gudang";
        } else {
            GudangObatModel gudangObatBaru = new GudangObatModel();
            model.addAttribute("gudang", gudangModel);
            gudangObatBaru.setObat(obatService.getObatByIdObat(idObat).get());
            gudangObatBaru.setGudang(gudangService.getGudangByIdGudang(idGudang).get());
            gudangObatService.addGudangObat(gudangObatBaru);
            model.addAttribute("gudangObatList", gudangModel.getListGudangObat());
            return "redirect:/gudang/view?idGudang=" + idGudang;
        }
    }

    @RequestMapping(value = "/gudang/cari")
    public String cariGudang(Model model) {
        List<GudangModel> listGudang = gudangService.getGudangList();
        model.addAttribute("gudangList", listGudang);
        return "cari-gudang";
    }

    @RequestMapping(path = "/gudang/expired-obat", method = RequestMethod.GET)
    public String obatExpired(@RequestParam(value = "idGudang") Long idGudang, Model model) {
        GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
        List<GudangObatModel> listGudangObat = gudang.getListGudangObat();
        List<GudangObatModel> listObatExpired = gudangObatService.filterObatExpired(listGudangObat);

        if (listObatExpired.size() == 0) {
            model.addAttribute("namaGudang", gudang.getNama());
            return "obat-expired-null";
        }

        model.addAttribute("expiredObatList", listObatExpired);
        return "lihat-obat-expired";
    }
}
