package com.questions.questions.operator;

import com.questions.questions.dao.*;
import com.questions.questions.dto.DeleteQuestionRequestDto;
import com.questions.questions.dto.QuestionListResponseDto;
import com.questions.questions.dto.QuestionsCreateRequestDto;
import com.questions.questions.dto.QuestionsUpdateRequestDto;
import com.questions.questions.repository.AnswersRepository;
import com.questions.questions.services.impl.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.questions.questions.util.UtilityMethods.findDescriptionCatalogue;
import static com.questions.questions.util.UtilityMethods.responseEntityDto;

@Component
@Log4j2
public class QuestionsOperator {

    @Autowired
    private QuestionsServicesImpl questionsServicesImpl;

    @Autowired
    private OptionsHeadServicesImpl optionsHeadServicesImpl;

    @Autowired
    private OptionsServicesImpl optionsServicesImpl;

    @Autowired
    private CataloguesServicesImpl cataloguesServicesImpl;

    @Autowired
    private AnswersServicesImpl answersServicesImpl;

    public ResponseEntity<?> createQuestions(QuestionsCreateRequestDto questionsCreateRequestDto) {
        OptionsHead optionsHead = new OptionsHead();
        Questions questions = new Questions();
        List<Options> options;
        this.optionsHeadServicesImpl.createOptionsHead(optionsHead);
        options = questionsCreateRequestDto.getOptions().stream().map(s -> {
            Options optionsBody = new Options();
            optionsBody.setNameOption(s.getOptionName());
            optionsBody.setValue(s.getOptionValue());
            optionsBody.setOptionsHead(optionsHead);
            return optionsBody;
        }).toList();
        this.optionsServicesImpl.createOptionsHead(options);

        questions.setOptionsHead(optionsHead);
        questions.setNameQuestions(questionsCreateRequestDto.getNameQuestions());
        questions.setCategoryQuestions(questionsCreateRequestDto.getCategoryQuestions());
        questions.setFunctionQuestions(questionsCreateRequestDto.getFunctionQuestions());
        questions.setActive(true);
        this.questionsServicesImpl.createOptionsHead(questions);

        return responseEntityDto("", questions, HttpStatus.OK);
    }

    public ResponseEntity<?> listQuestions() {
        List<Catalogues> catalogues = this.cataloguesServicesImpl.findByAll();
        List<Questions> questions = this.questionsServicesImpl.findAll();
        List<QuestionListResponseDto> list = questions.stream()
                .parallel()
                .filter(Questions::getActive)
                .map(question -> {
                            List<QuestionListResponseDto.Options> byOptionsHead = this.optionsServicesImpl.findByOptionsHead(question.getOptionsHead())
                                    .stream()
                                    .map(options ->
                                        QuestionListResponseDto.Options.builder()
                                                .idOption(options.getId())
                                                .optionName(options.getNameOption())
                                                .optionValue(options.getValue())
                                                .build()
                                    ).toList();

                            return QuestionListResponseDto.builder()
                                    .idQuestion(question.getId())
                                    .categoryQuestions(question.getCategoryQuestions())
                                    .categoryQuestionsDescription(findDescriptionCatalogue(catalogues, question.getCategoryQuestions()))
                                    .nameQuestions(question.getNameQuestions())
                                    .replied(questionReplied(question))
                                    .functionQuestions(question.getFunctionQuestions())
                                    .functionQuestionsDescription(findDescriptionCatalogue(catalogues, question.getFunctionQuestions()))
                                    .weight(0)
                                    .optionsList(byOptionsHead)
                                    .build();
                        }
                ).toList();
        if (list.isEmpty()) {
            responseEntityDto("No hay resultados", null, HttpStatus.BAD_REQUEST);
        }

        return responseEntityDto("", list, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteQuestion(DeleteQuestionRequestDto deleteQuestionRequestDto) {
        Questions questions = this.questionsServicesImpl.findById(deleteQuestionRequestDto.getIdQuestion());
        if (Objects.isNull(questions)) {
            return responseEntityDto("La pregunta no existe", null, HttpStatus.BAD_REQUEST);
        }
        questions.setActive(false);
        this.questionsServicesImpl.createOptionsHead(questions);
        return responseEntityDto("Se desactiva la pregunta", null, HttpStatus.OK);
    }

    public ResponseEntity<?> updateQuestion(QuestionsUpdateRequestDto questionsCreateRequestDto) {

        questionsCreateRequestDto.getOptions().forEach(optionValue -> {
            Optional<Options> optionalOptions = this.optionsServicesImpl.findById(optionValue.getIdOption());
            optionalOptions.ifPresent(options -> {
                options.setNameOption(optionValue.getOptionName());
                this.optionsServicesImpl.createOptions(options);
            });
        });

        Questions questions = this.questionsServicesImpl.findById(questionsCreateRequestDto.getIdQuestion());
        if (Objects.nonNull(questions)){
            questions.setNameQuestions(questionsCreateRequestDto.getNameQuestions());
            questions.setFunctionQuestions(questionsCreateRequestDto.getFunctionQuestions());
            questions.setCategoryQuestions(questionsCreateRequestDto.getCategoryQuestions());
            this.questionsServicesImpl.createOptionsHead(questions);
        }

        return responseEntityDto("Se actualiza la pregunta", null, HttpStatus.OK);
    }

    public boolean questionReplied(Questions questions) {
        List<Answers> answersList = this.answersServicesImpl.findByQuestion(questions.getId());
        return answersList.stream()
                .filter(Answers::getSelected)
                .anyMatch(answers -> answers.getUserQuestions().getQuestions().getId().equals(questions.getId()));
    }
}
