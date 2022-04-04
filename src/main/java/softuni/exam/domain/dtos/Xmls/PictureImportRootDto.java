package softuni.exam.domain.dtos.Xmls;

import softuni.exam.domain.dtos.Xmls.PictureImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureImportRootDto {
    @XmlElement(name = "picture")
    private PictureImportDto[] pictureImportDtos;

    public PictureImportRootDto() {
    }

    public PictureImportDto[] getPictureImportDtos() {
        return pictureImportDtos;
    }

    public void setPictureImportDtos(PictureImportDto[] pictureImportDtos) {
        this.pictureImportDtos = pictureImportDtos;
    }
}
