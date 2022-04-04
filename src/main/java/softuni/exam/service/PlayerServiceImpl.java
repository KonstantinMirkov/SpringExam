package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.domain.dtos.Jsons.PlayerImportDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final static String JSON_PLAYER_PATH = "src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository,
                             PictureRepository pictureRepository, ModelMapper modelMapper,
                             ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = System.lineSeparator();

        PlayerImportDto[] playerImportDtos = this.gson.fromJson(this.readPlayersJsonFile(), PlayerImportDto[].class);

        for (PlayerImportDto playerImportDto : playerImportDtos) {
            Player player = this.modelMapper.map(playerImportDto, Player.class);

            Team team = this.teamRepository.findTeamByName(playerImportDto.getTeam().getName());
            if (team != null) {
                player.setTeam(team);
            }

            Picture picture = this.pictureRepository.findPictureByUrl(playerImportDto.getPicture().getUrl());

            if (picture != null) {
                player.setPicture(picture);
            }
            if (!playerImportDto.getPosition().isEmpty() && playerImportDto.getPosition().length() == 2) {
                player.setPosition(Position.valueOf(playerImportDto.getPosition()));
            } else {
                player.setPosition(null);
            }

            if (!this.validatorUtil.isValid(player) || team == null || picture == null) {
                stringBuilder.append("Invalid player!").append(line);
                continue;
            }
            this.playerRepository.saveAndFlush(player);
            stringBuilder.append(String.format("Successfully imported player - %s %s", player.getFirstName(), player.getLastName()))
                    .append(line);

        }
        return stringBuilder.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(JSON_PLAYER_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = System.lineSeparator();

        List<Player> players = this.playerRepository.findAllBySalaryIsGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000));

        for (Player player : players) {
            sb.append(String.format("Player name: %s %s", player.getFirstName(), player.getLastName())).append(line);
            sb.append(String.format("\tNumber: %d", player.getNumber())).append(line);
            sb.append(String.format("\tSalary: %s", player.getSalary())).append(line);
            sb.append(String.format("\tTeam: %s", player.getTeam().getName())).append(line);
        }
        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        String line = System.lineSeparator();

        List<Player> players = this.playerRepository.findAllByOrderById();
        sb.append("Team: North Hub").append(line);
        for (Player player : players) {
            if (player.getTeam().getName().equals("North Hub")) {
                sb.append(String.format("\tPlayer name: %s %s - %s", player.getFirstName(), player.getLastName(),
                                player.getPosition().toString())).append(line)
                        .append(String.format("\tNumber: %d", player.getNumber())).append(line);
            }
        }
        return sb.toString().trim();
    }
}
