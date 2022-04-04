package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.Xmls.PictureImportDto;
import softuni.exam.domain.dtos.Xmls.PictureImportRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final static String XML_PICTURES_PATH = "src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, XmlParser xmlParser,
                              ModelMapper modelMapper, ValidatorUtil validatorUtil,
                              FileUtil fileUtil) {
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = System.lineSeparator();

        PictureImportRootDto importRootDto = this.xmlParser.parseXml(PictureImportRootDto.class, XML_PICTURES_PATH);

        for (PictureImportDto pictureImportDto : importRootDto.getPictureImportDtos()) {
            Picture picture = this.modelMapper.map(pictureImportDto, Picture.class);

            if (!this.validatorUtil.isValid(picture)){
                stringBuilder.append("Invalid picture").append(line);
                continue;
            }
            this.pictureRepository.saveAndFlush(picture);
            stringBuilder.append(String.format("Successfully imported picture - %s", picture.getUrl())).append(line);

        }
        return stringBuilder.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return this.fileUtil.readFile(XML_PICTURES_PATH);
    }

}
