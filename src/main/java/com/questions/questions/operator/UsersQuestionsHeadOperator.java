package com.questions.questions.operator;

import com.questions.questions.dao.*;
import com.questions.questions.dto.QuestionRequestDto;
import com.questions.questions.dto.UserQuestionResponseDto;
import com.questions.questions.dto.UsersQuestionsCreateRequestDto;
import com.questions.questions.dto.UsersRequestDto;
import com.questions.questions.services.impl.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.questions.questions.util.UtilityMethods.findDescriptionCatalogue;
import static com.questions.questions.util.UtilityMethods.responseEntityDto;

@Component
@Log4j2
public class UsersQuestionsHeadOperator {

    @Autowired
    private UsersQuestionsServicesImpl usersQuestionsServicesImpl;

    @Autowired
    private QuestionsServicesImpl questionsServicesImpl;

    @Autowired
    private UserServicesImpl userServicesImpl;

    @Autowired
    private UsersQuestionsHeadServicesImpl usersQuestionsHeadServicesImpl;

    @Autowired
    private OptionsServicesImpl optionsServicesImpl;

    @Autowired
    private AnswersServicesImpl answersServicesImpl;

    @Autowired
    private CataloguesServicesImpl cataloguesServicesImpl;

    //Metodo para listar las preguntas del usuario
    public ResponseEntity<?> listQuestionUser(UsersRequestDto usersRequestDto) {
        Users users = this.userServicesImpl.findByIdentification(usersRequestDto.getIdentification());
        List<Catalogues> catalogues;
        List<Catalogues> cataloguesFunction;
        if (Objects.isNull(users)) {
            return responseEntityDto("Usuario no existe", null, HttpStatus.OK);
        }
        UserQuestionsHead userQuestionsHeads = this.usersQuestionsHeadServicesImpl.findByUserLastVersion(users.getId());
        if (Objects.isNull(userQuestionsHeads)) {
            return responseEntityDto("No hay para mostrar", null, HttpStatus.OK);
        }
        UserQuestionResponseDto userQuestionResponseDto = new UserQuestionResponseDto();
        catalogues = this.cataloguesServicesImpl.findByAll();
        cataloguesFunction = this.cataloguesServicesImpl.findByCatalogueType(1);
        List<UserQuestionResponseDto.Question> responseDtoList = userQuestionsHeads.getUserQuestionsList().stream()
                .filter(userQuestions -> userQuestions.getQuestions().getActive())
                .map(userQuestions -> {
                    UserQuestionResponseDto.Question userQuestionList = new UserQuestionResponseDto.Question();
                    userQuestionList.setIdQuestion(userQuestions.getId());
                    userQuestionList.setCategoryQuestions(userQuestions.getQuestions().getCategoryQuestions());
                    userQuestionList.setCategoryQuestionsDescription(findDescriptionCatalogue(catalogues, userQuestions.getQuestions().getCategoryQuestions()));
                    userQuestionList.setFunctionQuestions(userQuestions.getQuestions().getFunctionQuestions());
                    userQuestionList.setFunctionQuestionsDescription(findDescriptionCatalogue(catalogues, userQuestions.getQuestions().getFunctionQuestions()));
                    userQuestionList.setReplied(userQuestions.getReplied());
                    userQuestionList.setNameQuestion(userQuestions.getQuestions().getNameQuestions());
                    OptionsHead optionsHead = userQuestions.getQuestions().getOptionsHead();
                    List<Options> byOptionsHead = this.optionsServicesImpl.findByOptionsHead(optionsHead);
                    userQuestionList.setOptionsList(byOptionsHead);
                    return userQuestionList;
                }).toList();

        Map<String, List<UserQuestionResponseDto.Question>> stringListMap = responseDtoList.stream().collect(Collectors.groupingBy(question -> {
            return cataloguesFunction.stream().filter(cf -> cf.getId().equals(question.getFunctionQuestions().longValue())).findFirst().map(Catalogues::getDescription).orElse("Otros");
        }));

        userQuestionResponseDto.setQuestions(stringListMap);
        userQuestionResponseDto.setFinish(userQuestionsHeads.getFinished());
        return responseEntityDto("", userQuestionResponseDto, HttpStatus.OK);
    }

    //Metodo para guardar la respuesta
    public ResponseEntity<?> saveResponse(QuestionRequestDto questionRequestDto) {

        questionRequestDto.getResponses().forEach(responses -> {
            UserQuestions questions = this.usersQuestionsServicesImpl.findByUserQuestions(responses.getQuestionId());
            if (Objects.nonNull(questions.getReplied()) && questions.getReplied()) {
//                return responseEntityDto("La pregunta ya fue respondida", null, HttpStatus.BAD_REQUEST);
                return;
            }
            List<Options> optionsList = questions.getQuestions().getOptionsHead().getOptionsList();
            if (optionsList.isEmpty()) {
//                return responseEntityDto("No hay opciones para esta pregunta", null, HttpStatus.BAD_REQUEST);
                return;
            }
            boolean validResponse = optionsList.stream().noneMatch(options -> Objects.equals(options.getId(), responses.getOptionValue().longValue()));
            if (validResponse) {
//                return responseEntityDto("La respuesta no coincide con las opciones", null, HttpStatus.BAD_REQUEST);
                return;
            }

            questions.setReplied(true);
            this.usersQuestionsServicesImpl.createUsersQuestions(questions);

            List<Answers> answers = optionsList.stream().map(options -> {
                Answers answersNew = new Answers();
                answersNew.setOptions(options);
                answersNew.setUserQuestions(questions);
                answersNew.setSelected(options.getId().equals(responses.getOptionValue().longValue()));
                return answersNew;
            }).toList();
            this.answersServicesImpl.createUsersQuestions(answers);
        });

        UserQuestions questions = this.usersQuestionsServicesImpl.findByUserQuestions(questionRequestDto.getResponses().get(0).getQuestionId());
        //Validar si todas las preguntas ya fueron contestadas
        List<UserQuestions> userQuestionsHeadList = this.usersQuestionsServicesImpl.findByUserQuestionsHead(questions.getUserQuestionsHead());
        boolean validFinish = userQuestionsHeadList
                .stream()
                .noneMatch(userQuestions ->
                        Objects.isNull(userQuestions.getReplied()) || userQuestions.getReplied().equals(false) //Encontrar un null
                );

        if (validFinish) {
            UserQuestionsHead userQuestionsHead = questions.getUserQuestionsHead();
            userQuestionsHead.setFinished(true);
            this.usersQuestionsHeadServicesImpl.createUsersQuestionsHead(userQuestionsHead);
        }

        return responseEntityDto("Se guarda la pregunta", null, HttpStatus.OK);
    }
}
