package com.questions.questions.rest;

import com.questions.questions.dao.EmailRequestDto;
import com.questions.questions.dto.CreateUsersRequestDto;
import com.questions.questions.dto.GenerateNewPasswordRecoveryRequestDto;
import com.questions.questions.dto.RemovedUserRequestDto;
import com.questions.questions.dto.UsersListResponseDto;
import com.questions.questions.mail.SendMail;
import com.questions.questions.operator.UsersOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.questions.questions.util.UtilityMethods.responseEntityDto;

@RestController
@RequestMapping("/user")
public class RestControllersUsers {

    @Autowired
    private UsersOperator usersOperator;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUsers(@RequestBody CreateUsersRequestDto createUsersRequestDto) {
        return this.usersOperator.createUsers(createUsersRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userList() {
        return this.usersOperator.findAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUsers(@RequestBody UsersListResponseDto usersListResponseDto) {
        return this.usersOperator.updateUser(usersListResponseDto);
    }

    @PostMapping(path = "/recovery", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recoveryPassword(@RequestBody EmailRequestDto emailRequestDto) {
        return this.usersOperator.recoveryPassword(emailRequestDto);
    }

    @PostMapping(path = "/generateNewPasswordRecovery", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateNewPasswordRecovery(@RequestBody GenerateNewPasswordRecoveryRequestDto generatePassword) {
        return this.usersOperator.generateNewPasswordRecovery(generatePassword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@RequestBody RemovedUserRequestDto removedUserRequestDto) {
        return this.usersOperator.deleteUser(removedUserRequestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(path = "/create/new/question/{identification}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewQuestionUsers(@PathVariable String identification) {
        return this.usersOperator.createNewQuestionUsers(identification);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(path = "/graphics/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> graphics(@PathVariable("type") Integer type) {
        if (type.equals(1)) {
            return this.usersOperator.graphicsCategory();
        } else if (type.equals(2)) {
            return this.usersOperator.graphicsFunction();
        }
        return responseEntityDto("No data", null, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(path = "/graphics-identification/{identification}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> graphicsIdentification(@PathVariable("identification") String identification) {
        return this.usersOperator.graphicsFunctionIdentification(identification);
    }
}
