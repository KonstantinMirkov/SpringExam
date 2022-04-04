package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.domain.dtos.Xmls.TeamImportDto;
import softuni.exam.domain.dtos.Xmls.TeamImportRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final static String XML_TEAM_PATH = "src/main/resources/files/xml/teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository,
                           XmlParser xmlParser, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil, FileUtil fileUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = System.lineSeparator();

        TeamImportRootDto rootDto = this.xmlParser.parseXml(TeamImportRootDto.class, XML_TEAM_PATH);

        for (TeamImportDto teamImportDto : rootDto.getTeamImportDtos()) {
            Team team = this.modelMapper.map(teamImportDto, Team.class);

            Picture picture = this.pictureRepository.findPictureByUrl(teamImportDto.getPictureImportDto().getUrl());
            if (picture != null){
                team.setPicture(picture);
            }
            if (!this.validatorUtil.isValid(team) || picture == null){
                stringBuilder.append("Invalid team!").append(line);
                continue;
            }
            this.teamRepository.saveAndFlush(team);
            stringBuilder.append(String.format("Successfully imported team - %s", team.getName())).append(line);
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(XML_TEAM_PATH);
    }
}
