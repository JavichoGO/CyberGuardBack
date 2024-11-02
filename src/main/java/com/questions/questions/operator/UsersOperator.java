package com.questions.questions.operator;

import com.questions.questions.dao.*;
import com.questions.questions.dto.*;
import com.questions.questions.mail.SendMail;
import com.questions.questions.repository.RolesRepository;
import com.questions.questions.repository.UsersQuestionsHeadRepository;
import com.questions.questions.services.impl.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.questions.questions.util.UtilityMethods.responseEntity;
import static com.questions.questions.util.UtilityConstant.FORMAT_DATE;
import static com.questions.questions.util.UtilityMethods.responseEntityDto;

@Component
public class UsersOperator {

    @Autowired
    private UserServicesImpl userServices;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private SendMail sendMail;

    @Autowired
    private UsersQuestionsHeadServicesImpl usersQuestionsHeadServicesImpl;

    @Autowired
    private UsersQuestionsServicesImpl usersQuestionsServicesImpl;

    @Autowired
    private QuestionsServicesImpl questionsServicesImpl;

    @Autowired
    private CataloguesServicesImpl cataloguesServicesImpl;

    public ResponseEntity<?> createUsers(CreateUsersRequestDto usersRequestDto) {
        Roles roles = this.rolesRepository.findByName("USER");
        Users email = this.userServices.findByEmail(usersRequestDto.getEmail());
        Users identification = this.userServices.findByIdentification(usersRequestDto.getIdentification());
        if (Objects.nonNull(email)) {
            return responseEntity("Email ya en uso", HttpStatus.BAD_REQUEST);
        }
        if (Objects.nonNull(identification)) {
            return responseEntity("Dni ya esa en uso", HttpStatus.BAD_REQUEST);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(usersRequestDto.getPassword());
        Users users = new Users();
        users.setEmail(usersRequestDto.getEmail());
        users.setIdentification(usersRequestDto.getIdentification());
        users.setNameAll(usersRequestDto.getNameAll());
        users.setDateOfBirth(LocalDate.parse(usersRequestDto.getDateOfBirth(), DateTimeFormatter.ofPattern(FORMAT_DATE)));
        users.setPassword(encode);
        users.setPosition(usersRequestDto.getPosition());
        users.setRoles(Set.of(roles));
        users.setActive(true);
        this.userServices.create(users);
        usersRequestDto.setPassword("");

        //Asociar preguntas al nuevo usuario
        CompletableFuture.runAsync(() -> {
            createUserQuestions(users);
        });
        return new ResponseEntity<>(usersRequestDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> findAllUsers() {
        List<UsersListResponseDto> usersListResponseDto = this.userServices.findByAll().stream()
                .filter(Users::isActive)
                .map(users ->
                        UsersListResponseDto.builder()
                                .id(users.getId())
                                .nameAll(users.getNameAll())
                                .identification(users.getIdentification())
                                .surveyAnswered(surveyAnswered(users.getId()))
                                .position(users.getPosition())
                                .dateOfBirth(users.getDateOfBirth().format(DateTimeFormatter.ofPattern(FORMAT_DATE)))
                                .email(users.getEmail())
                                .build()
                ).toList();
        return new ResponseEntity<>(usersListResponseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateUser(UsersListResponseDto usersListResponseDto) {
        Users idUser = this.userServices.findById(usersListResponseDto.getId());

        if (!idUser.getEmail().equalsIgnoreCase(usersListResponseDto.getEmail())) {
            Users email = this.userServices.findByEmail(usersListResponseDto.getEmail());
            if (Objects.nonNull(email)){
                return responseEntity("Email ya en uso", HttpStatus.BAD_REQUEST);
            }
        }

        if (!idUser.getIdentification().equalsIgnoreCase(usersListResponseDto.getIdentification())) {
            Users identification = this.userServices.findByIdentification(usersListResponseDto.getIdentification());
            if (Objects.nonNull(identification)) {
                return responseEntity("Dni ya esa en uso", HttpStatus.BAD_REQUEST);
            }
        }

        Users users = this.userServices.findById(usersListResponseDto.getId());
        users.setId(usersListResponseDto.getId());
        users.setNameAll(usersListResponseDto.getNameAll());
        users.setIdentification(usersListResponseDto.getIdentification());
        if (Objects.nonNull(usersListResponseDto.getPassword()) && !usersListResponseDto.getPassword().isBlank()) {
            users.setPassword(new BCryptPasswordEncoder().encode(usersListResponseDto.getPassword()));
        }
        users.setPosition(usersListResponseDto.getPosition());
        users.setEmail(usersListResponseDto.getEmail().toLowerCase());
        users.setDateOfBirth(LocalDate.parse(usersListResponseDto.getDateOfBirth(), DateTimeFormatter.ofPattern(FORMAT_DATE)));
        users.setActive(true);
        this.userServices.update(users);
        users.setPassword("");
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    public ResponseEntity<?> recoveryPassword(EmailRequestDto emailRequestDto) {
        Users users = this.userServices.findByEmail(emailRequestDto.getEmail());
        int token = ThreadLocalRandom.current().nextInt(100000, 1000000);
        users.setOtp(token);
        this.userServices.create(users);
        try {
            this.sendMail.sendMail(users, token);
        } catch (MessagingException messagingException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity("Se envio un correo", HttpStatus.OK);
    }

    public ResponseEntity<?> generateNewPasswordRecovery(GenerateNewPasswordRecoveryRequestDto generatePassword) {
        Users users = this.userServices.findByIdentification(generatePassword.getIdentification());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (Objects.isNull(users)){
            return responseEntityDto("El usuario no existe", null , HttpStatus.BAD_REQUEST);
        }
        if (!generatePassword.getPassword().equals(generatePassword.getPasswordTwo())) {
            return responseEntityDto("Las contraseñas no coinciden", null , HttpStatus.BAD_REQUEST);
        }
        users.setPassword(passwordEncoder.encode(generatePassword.getPasswordTwo()));
        users.setOtp(null);
        this.userServices.create(users);
        return responseEntityDto("Se realizo el cambio de contraseña correctamente", null , HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(RemovedUserRequestDto removedUserRequestDto) {
        Users users = this.userServices.findById(removedUserRequestDto.getIdPerson());
        users.setActive(false);
        this.userServices.update(users);
        return new ResponseEntity<>(Map.of("message", "Usuario desactivado"),HttpStatus.OK);
    }

    //Metodo para realacionar usuario con preguntas
    public void createUserQuestions(Users users) {
        List<Questions> questions = this.questionsServicesImpl.findByActive(true);
        UserQuestionsHead userQuestionsHead = new UserQuestionsHead();
        userQuestionsHead.setVersion(0);
        userQuestionsHead.setUser(users);
        userQuestionsHead.setFinished(false);
        this.usersQuestionsHeadServicesImpl.createUsersQuestionsHead(userQuestionsHead);

        questions.parallelStream().forEach(question -> {
            UserQuestions userQuestions = new UserQuestions();
            userQuestions.setUserQuestionsHead(userQuestionsHead);
            userQuestions.setQuestions(question);
            userQuestions.setReplied(false);
            this.usersQuestionsServicesImpl.createUsersQuestions(userQuestions);
        });
    }

    //Metodo para realacionar usuario con preguntas
    public ResponseEntity<?> createNewQuestionUsers(String identification) {
        Integer version;
        Users users = this.userServices.findByIdentification(identification);
        if (Objects.isNull(users)) {
            return responseEntityDto("Usuario no existe", null, HttpStatus.BAD_REQUEST);
        }
        UserQuestionsHead userQuestionsHead = this.usersQuestionsHeadServicesImpl.findByUserLastVersion(users.getId());
        if (Objects.isNull(userQuestionsHead)) {
            return responseEntityDto("Error, usuario no tiene asignado cabeceras de preguntas", null, HttpStatus.BAD_REQUEST);
        }
        List<UserQuestionsHead> userQuestionsHeads = this.usersQuestionsHeadServicesImpl.findByUser(userQuestionsHead.getUser());
        userQuestionsHeads.forEach(uq -> uq.setFinished(true));
        this.usersQuestionsHeadServicesImpl.createUsersQuestionsHead(userQuestionsHeads); //Todas las encuestas anteriores desactivarlas

        UserQuestionsHead userQuestionHeadNew = new UserQuestionsHead();
        version = userQuestionsHead.getVersion();
        userQuestionHeadNew.setVersion(++version);
        userQuestionHeadNew.setUser(users);
        userQuestionHeadNew.setFinished(false);
        this.usersQuestionsHeadServicesImpl.createUsersQuestionsHead(userQuestionHeadNew);
        List<Questions> questions = this.questionsServicesImpl.findAll();

        //Crear nueva encuesta para este usuario
        questions.parallelStream().filter(Questions::getActive).forEach(question -> {
            UserQuestions userQuestions = new UserQuestions();
            userQuestions.setUserQuestionsHead(userQuestionHeadNew);
            userQuestions.setQuestions(question);
            userQuestions.setReplied(false);
            this.usersQuestionsServicesImpl.createUsersQuestions(userQuestions);
        });

        return responseEntityDto("Se genero un nuevo listado de preguntas", null, HttpStatus.OK);
    }

    public ResponseEntity<?> graphicsCategory() {
        List<GraphicsView> graphics = this.userServices.graphicsCategory();
        List<Catalogues> cataloguesList = this.cataloguesServicesImpl.findByAll();
        Map<Long, Catalogues> cataloguesMap = cataloguesList.stream().collect(Collectors.toMap(Catalogues::getId, Function.identity()));
        List<GraphicsResponseDto.Data> graphicsResponseDtos = graphics.stream().map(graphicsView -> {
            GraphicsResponseDto.Data graphicsResponseDto = new GraphicsResponseDto.Data();
            graphicsResponseDto.setDesired(graphicsView.getDesired());
            graphicsResponseDto.setCategoryDescription(cataloguesMap.get(graphicsView.getIdCategory()).getDescription());
            graphicsResponseDto.setIdCategory(graphicsView.getIdCategory());
            graphicsResponseDto.setAverage(graphicsView.getAverage());
            return graphicsResponseDto;
        }).toList();
        return responseEntityDto("", graphicsResponseDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> graphicsFunction() {
        List<GraphicsView> graphics = this.userServices.graphicsFunction();
        List<Catalogues> cataloguesList = this.cataloguesServicesImpl.findByAll();
        GraphicsResponseDto graphicsResponse = new GraphicsResponseDto();
        Map<Long, Catalogues> cataloguesMap = cataloguesList.stream().collect(Collectors.toMap(Catalogues::getId, Function.identity()));
        List<GraphicsResponseDto.Data> graphicsResponseDtos = graphics.stream().map(graphicsView -> {
            GraphicsResponseDto.Data graphicsResponseDto = new GraphicsResponseDto.Data();
            graphicsResponseDto.setDesired(graphicsView.getDesired());
            graphicsResponseDto.setFunctionDescription(cataloguesMap.get(graphicsView.getIdFunction()).getDescription());
            graphicsResponseDto.setIdFunction(graphicsView.getIdFunction());
            graphicsResponseDto.setAverage(graphicsView.getAverage());
            return graphicsResponseDto;
        }).toList();
        graphicsResponse.setFunction(graphicsResponseDtos);
        return responseEntityDto("", graphicsResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> graphicsFunctionIdentification(String identification) {
        List<GraphicsView> graphics = this.userServices.graphicsFunctionIdentification(identification);
        if (graphics.isEmpty()) {
            return responseEntityDto("Sin datos para mostrar", graphics, HttpStatus.OK);
        }
        List<Catalogues> cataloguesList = this.cataloguesServicesImpl.findByAll();
        GraphicsResponseDto graphicsResponse = new GraphicsResponseDto();
        Map<Long, Catalogues> cataloguesMap = cataloguesList.stream().collect(Collectors.toMap(Catalogues::getId, Function.identity()));
        List<GraphicsResponseDto.Data> graphicsResponseDtos = graphics.stream().map(graphicsView -> {
            GraphicsResponseDto.Data graphicsResponseDto = new GraphicsResponseDto.Data();
            graphicsResponseDto.setDesired(graphicsView.getDesired());
            graphicsResponseDto.setFunctionDescription(cataloguesMap.get(graphicsView.getIdFunction()).getDescription());
            graphicsResponseDto.setIdFunction(graphicsView.getIdFunction());
            graphicsResponseDto.setAverage(graphicsView.getAverage());
            return graphicsResponseDto;
        }).toList();
        graphicsResponse.setFunction(graphicsResponseDtos);
        return responseEntityDto("", graphicsResponse, HttpStatus.OK);
    }

    public boolean surveyAnswered(Long clientId) {
        Users users = this.userServices.findById(clientId);
        List<UserQuestionsHead> user = this.usersQuestionsHeadServicesImpl.findByUser(users);
        return user.stream().anyMatch(u -> u.getFinished().equals(true));
    }
}
