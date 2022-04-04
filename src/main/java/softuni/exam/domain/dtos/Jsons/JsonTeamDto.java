package softuni.exam.domain.dtos.Jsons;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.dtos.Jsons.JsonPictureDto;

public class JsonTeamDto {
    @Expose
    private String name;
    @Expose
    private JsonPictureDto picture;

    public JsonTeamDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonPictureDto getPicture() {
        return picture;
    }

    public void setPicture(JsonPictureDto picture) {
        this.picture = picture;
    }
}
