package softuni.exam.domain.dtos.Jsons;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.dtos.Jsons.JsonPictureDto;
import softuni.exam.domain.dtos.Jsons.JsonTeamDto;

import java.math.BigDecimal;

public class PlayerImportDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int number;
    @Expose
    private BigDecimal salary;
    @Expose
    private String position;
    @Expose
    private JsonPictureDto picture;
    @Expose
    private JsonTeamDto team;

    public PlayerImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public JsonPictureDto getPicture() {
        return picture;
    }

    public void setPicture(JsonPictureDto picture) {
        this.picture = picture;
    }

    public JsonTeamDto getTeam() {
        return team;
    }

    public void setTeam(JsonTeamDto team) {
        this.team = team;
    }
}
