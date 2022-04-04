package softuni.exam.domain.dtos.Xmls;

import softuni.exam.domain.dtos.Xmls.TeamImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportRootDto {
    @XmlElement(name = "team")
    private TeamImportDto[] teamImportDtos;

    public TeamImportRootDto() {
    }

    public TeamImportDto[] getTeamImportDtos() {
        return teamImportDtos;
    }

    public void setTeamImportDtos(TeamImportDto[] teamImportDtos) {
        this.teamImportDtos = teamImportDtos;
    }
}
