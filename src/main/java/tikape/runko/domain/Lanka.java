package tikape.runko.domain;

public class Lanka {

    private Integer id;
    private String nimi;
    private String vari;
    private String varikoodi;
    private String valmistaja;
    private String materiaali;
    private String paksuus;
    private String teksti;

    public Lanka(Integer id, String nimi, String vari, String varikoodi, 
            String valmistaja, String materiaali, String paksuus) {
        this.id = id;
        this.nimi = nimi;
        this.vari = vari;
        this.varikoodi = varikoodi;
        this.valmistaja = valmistaja;
        this.materiaali = materiaali;
        this.paksuus = paksuus;
        this.teksti = nimi + ", " + vari;
    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getNimi() {
        return nimi;
    }

//    public void setNimi(String nimi) {
//        this.nimi = nimi;
//    }
    
    public String getVari() {
        return vari;
    }
    
    public String getVarikoodi() {
        return varikoodi;
    }
    
    public String getValmistaja() {
        return valmistaja;
    }
    
    public String getMateriaali() {
        return materiaali;
    }
    
    public String getPaksuus() {
        return paksuus;
    }
    
    
    public String getTeksti() {
        return teksti;
    }

}
