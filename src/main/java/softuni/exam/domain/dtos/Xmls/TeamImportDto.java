package softuni.exam.domain.dtos.Xmls;

import softuni.exam.domain.dtos.Xmls.PictureImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportDto {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "picture")
    private PictureImportDto pictureImportDto;

    public TeamImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureImportDto getPictureImportDto() {
        return pictureImportDto;
    }

    public void setPictureImportDto(PictureImportDto pictureImportDto) {
        this.pictureImportDto = pictureImportDto;
    }
}
