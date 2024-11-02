package com.questions.questions.operator;

import com.questions.questions.dto.OptionsCreateDto;
import com.questions.questions.dao.Options;
import com.questions.questions.dao.OptionsHead;
import com.questions.questions.mail.SendMail;
import com.questions.questions.services.impl.OptionsHeadServicesImpl;
import com.questions.questions.services.impl.OptionsServicesImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class OptionsOperator {

    @Autowired
    private OptionsHeadServicesImpl optionsHeadServicesImpl;

    @Autowired
    private SendMail sendMail;

    @Autowired
    private OptionsServicesImpl optionsServicesImpl;

    public ResponseEntity<?> createOptions(OptionsCreateDto optionsCreateDto) {
        OptionsHead optionsHead = new OptionsHead();

        this.optionsHeadServicesImpl.createOptionsHead(optionsHead);

        List<Options> optionsList = optionsCreateDto.getOptionsList().stream().map(options -> {
            Options optionsNew = new Options();
            optionsNew.setOptionsHead(optionsHead);
            optionsNew.setNameOption(options.getNameOption());
            return optionsNew;
        }).toList();

//        this.optionsServicesImpl.createOptions(optionsList);

        return new ResponseEntity<>(optionsHead, HttpStatus.CREATED);
    }
}
