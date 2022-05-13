package sk.stuba.fei.uim.vsa.pr2.web.response;

public class CarTypeDto {

    private Long id;
    private String name;

    public CarTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CarTypeDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
