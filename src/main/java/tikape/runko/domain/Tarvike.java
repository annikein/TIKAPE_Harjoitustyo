package tikape.runko.domain;

public class Tarvike {

    private Integer id;
    private String tyyppi;
    private String pituus;
    private String koko;
    private String teksti;


    public Tarvike(Integer id, String tyyppi, String pituus, String koko) {
        this.id = id;
        this.tyyppi = tyyppi;
        this.pituus = pituus;
        this.koko = koko;
        this.teksti = tyyppi + ", " + koko + ", " + pituus;
        
    }

    public Integer getId() {
        return id;
    }

    public String getTyyppi() {
        return tyyppi;
    }
    
    public String getPituus() {
        return pituus;
    }
    
    public String getKoko() {
        return koko;
    }
    
    public String getTeksti() {
        return teksti;
    }
    
    
}
