package com.questions.questions.util;

public class UtilityConstant {

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String QUERY_GRAPHICS =
            """
            SELECT tq.categoryQuestions AS idCategory, tc.desired AS desired, AVG(to2.value) AS average
            FROM UserQuestionsHead tuqh
            INNER JOIN Users tu ON tu.id = tuqh.user.id
            AND tuqh.version = (SELECT MAX(version) FROM UserQuestionsHead tu2 WHERE tu2.user.id = tuqh.user.id)
            AND tuqh.finished = true
            INNER JOIN UserQuestions tuq ON tuq.userQuestionsHead.id = tuqh.id
            INNER JOIN Questions tq ON tq.id = tuq.questions.id
            INNER JOIN Answers ta ON ta.userQuestions.id = tuq.id AND ta.selected = true
            INNER JOIN Options to2 ON to2.id = ta.options.id
            INNER JOIN Catalogues tc ON tc.id = tq.categoryQuestions
            GROUP BY tq.categoryQuestions, tc.desired
            """;

    //Revisar si la funcion tiene promedio deseado.
    public static final String QUERY_GRAPHICS_FUNCTION =
            """
            SELECT tq.functionQuestions AS idFunction, tc.desired, AVG(to2.value) AS average
            FROM UserQuestionsHead tuqh
            INNER JOIN Users tu ON tu.id = tuqh.user.id
            AND tuqh.version = (SELECT MAX(version) FROM UserQuestionsHead tu2 WHERE tu2.user.id = tuqh.user.id)
            AND tuqh.finished = true
            INNER JOIN UserQuestions tuq ON tuq.userQuestionsHead.id = tuqh.id
            INNER JOIN Questions tq ON tq.id = tuq.questions.id
            INNER JOIN Answers ta ON ta.userQuestions.id = tuq.id AND ta.selected = true
            INNER JOIN Options to2 ON to2.id = ta.options.id
            INNER JOIN Catalogues tc ON tc.id = tq.functionQuestions
            GROUP BY tq.functionQuestions, tc.desired
            """;

    public static final String QUERY_GRAPHICS_FUNCTION_IDENTIFICATION =
            """
            SELECT tq.functionQuestions AS idFunction, tc.desired, AVG(to2.value) AS average
            FROM UserQuestionsHead tuqh
            INNER JOIN Users tu ON tu.id = tuqh.user.id
            AND tuqh.version = (SELECT MAX(version) FROM UserQuestionsHead tu2 WHERE tu2.user.id = tuqh.user.id)
            AND tuqh.finished = true
            INNER JOIN UserQuestions tuq ON tuq.userQuestionsHead.id = tuqh.id
            INNER JOIN Questions tq ON tq.id = tuq.questions.id
            INNER JOIN Answers ta ON ta.userQuestions.id = tuq.id AND ta.selected = true
            INNER JOIN Options to2 ON to2.id = ta.options.id
            INNER JOIN Catalogues tc ON tc.id = tq.functionQuestions
            WHERE tu.identification = :identification
            GROUP BY tq.functionQuestions, tc.desired
            """;
}
