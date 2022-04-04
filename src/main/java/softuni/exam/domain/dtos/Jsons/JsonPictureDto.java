package softuni.exam.domain.dtos.Jsons;

import com.google.gson.annotations.Expose;

public class JsonPictureDto {
    @Expose
    private String url;

    public JsonPictureDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
