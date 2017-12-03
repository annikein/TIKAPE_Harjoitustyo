package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.LankaDao;
import tikape.runko.database.MalliDao;
import tikape.runko.database.MalliLankaDao;
import tikape.runko.database.MalliTarvikeDao;
import tikape.runko.database.TarvikeDao;
import tikape.runko.domain.Lanka;
import tikape.runko.domain.Malli;
import tikape.runko.domain.MalliLanka;
import tikape.runko.domain.MalliTarvike;
import tikape.runko.domain.Tarvike;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/neuleet.db");
  
        LankaDao lankaDao = new LankaDao(database);
        MalliDao malliDao = new MalliDao(database);
        TarvikeDao tarvikeDao = new TarvikeDao(database);
        MalliLankaDao malliLankaDao = new MalliLankaDao(database);
        MalliTarvikeDao malliTarvikeDao = new MalliTarvikeDao(database);

        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("mallit", malliDao.findAll());
            map.put("tarvikkeet", tarvikeDao.findAll());
            map.put("langat", lankaDao.findAll());
//            map.put("mallitlangat", malliLankaDao.findAll());
//            map.put("mallittarvikkeet", malliTarvikeDao.findAll());

            return new ModelAndView(map, "/index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            String tyyppi = req.queryParams("tyyppi");
            //luodaan uusi malli
            malliDao.createNew(nimi, tyyppi);    
         
            res.redirect("/");
            return "";
        });
        
//        Spark.post("/", (req, res) -> {
//            
//            if (!req.queryParams("nimi").isEmpty()) {
//                String nimi = req.queryParams("nimi");
//                String tyyppi = req.queryParams("tyyppi");
//                //luodaan uusi malli
//                malliDao.createNew(nimi, tyyppi);    
//            }
//            
//            if (req.queryParams("malliid")!= null && !req.queryParams("koko").isEmpty()) {
//                Integer malliid = Integer.parseInt(req.params("malliid"));
//                String koko = req.queryParams("koko");
//                //lisätään ohjeelle koko ja ohje
//                malliDao.addKoko(malliid, koko);  
//            }
//            
//            if (req.queryParams("malliid")!=null && !req.queryParams("ohje").isEmpty()) {
//                Integer malliid = Integer.parseInt(req.params("malliid"));
//                String ohje = req.queryParams("ohje");
//                //lisätään ohjeelle koko ja ohje
//                malliDao.addOhje(malliid, ohje);  
//            }
//            
//            if (!req.queryParams("lmaara").isEmpty()) {
//                Integer malliid = Integer.parseInt(req.params("malliid"));
//                Integer lankaid = Integer.parseInt(req.params("lankaid"));
//                String lmaara = req.queryParams("lmaara");
//                //lisätään ohjeelle lanka
//                MalliLanka ml = new MalliLanka(malliid, lankaid, lmaara);
//                malliLankaDao.save(ml);
//            }
//            
//            if (!req.queryParams("tmaara").isEmpty()) {
//                Integer malliid = Integer.parseInt(req.params("malliid"));
//                Integer tarvikeid = Integer.parseInt(req.params("tarvikeid"));
//                String tmaara = req.queryParams("tmaara");
//                //lisätään ohjeelle lanka
//                MalliTarvike mt = new MalliTarvike(malliid, tarvikeid, tmaara);
//                malliTarvikeDao.save(mt);
//            }
//            
//            res.redirect("/");
//            return "";
//        });
        
        

        Spark.get("/langat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("langat", lankaDao.findAll());

            return new ModelAndView(map, "langat");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/tarvikkeet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tarvikkeet", tarvikeDao.findAll());

            return new ModelAndView(map, "tarvikkeet");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/langat", (req, res) -> {
            Lanka l = new Lanka(1, req.queryParams("nimi"), req.queryParams("vari"), req.queryParams("varikoodi"),
            req.queryParams("valmistaja"),req.queryParams("materiaali"),req.queryParams("paksuus"));
            lankaDao.save(l);

            res.redirect("/langat");
            return "";
        });
      
        Spark.post("/tarvikkeet", (req, res) -> {
            Tarvike t = new Tarvike(1, req.queryParams("tyyppi"), req.queryParams("pituus"), req.queryParams("koko"));
            tarvikeDao.save(t);

            res.redirect("/tarvikkeet");
            return "";
        });
        
        
        Spark.get("/malli/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer malliId = Integer.parseInt(req.params(":id"));
  
            map.put("malli", malliDao.findOne(malliId));
            map.put("langat", lankaDao.findAllForMalli(malliId));
            map.put("tarvikkeet", tarvikeDao.findAllForMalli(malliId));
            map.put("maarat", malliLankaDao.findAllForMalli(malliId));
 
            
            return new ModelAndView(map, "malli");
        }, new ThymeleafTemplateEngine());
        
        

        
        Spark.get("/delete/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer malliId = Integer.parseInt(req.params(":id"));
            map.put("malli", malliDao.findOne(malliId));  

            return new ModelAndView(map, "delete");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/delete/:id", (req, res) -> {
            Integer malliId = Integer.parseInt(req.params(":id"));
            malliDao.delete(malliId);
            res.redirect("/");
            return "";
        });
     
        

    }
}
