package tikape.runko.domain;

public class MalliTarvike {

    private Integer malli_id;
    private Integer tarvike_id;
    private String maara;


    public MalliTarvike(Integer malli_id, Integer tarvike_id, String maara) {
        this.malli_id = malli_id;
        this.tarvike_id = tarvike_id;
        this.maara = maara;
    }

    public Integer getMalliId() {
        return malli_id;
    }
    
    public Integer getTarvikeId() {
        return tarvike_id;
    }
   
    public String getMaara() {
        return maara;
    }
}