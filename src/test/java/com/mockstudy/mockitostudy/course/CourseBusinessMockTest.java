package com.mockstudy.mockitostudy.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

public class CourseBusinessMockTest {

    CourseService mockService;
    CourseBusiness business;
    List<String> courses;

    @BeforeEach
    void setup(){
        mockService = mock(CourseService.class);
        business = new CourseBusiness(mockService);
        courses = Arrays.asList(
                "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "Spotify Engineering Culture Desmistificado",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
                "Docker do Zero à Maestria - Contêinerização Desmistificada",
                "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
                "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
                "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
        );

    }

    //mock basico
    @Test
    void testCoursesRelatedToSpring_When_UsingAMock(){
        when(mockService.retriveCourses("Leandro")).thenReturn(courses);

        var filteredCourses = business.retrieveCoursesRelatedToSpring("Leandro");

        assertEquals(4, filteredCourses.size());
    }

    @Test
    void givenNullArgument_WhenRetrieveCoursesRelatedToSpring_ShouldThrowIllegalArgumentException(){

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> business.retrieveCoursesRelatedToSpring(null));

        assertEquals("Student cannot be null", thrown.getMessage());
    }

    //usando verify com mockito
    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethod_deleteCourse(){
        when(mockService.retriveCourses("Leandro")).thenReturn(courses);

        business.deleteCoursesNotRelatedToSpring("Leandro");

        verify(mockService).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
        verify(mockService, never()).deleteCourse("Microsserviços do 0 com Spring Cloud, Kotlin e Docker");
    }

    //capturando argumentos com mockito
    @Test
    void testDeleteCoursesNotRelatedToSpring_UsingArgumentCaptor_ShouldCaptureDeletedCourses() {
        when(mockService.retriveCourses("Leandro")).thenReturn(courses);

        business.deleteCoursesNotRelatedToSpring("Leandro");

        // Criando um ArgumentCaptor para capturar os argumentos passados para deleteCourse
        ArgumentCaptor<String> courseCaptor = ArgumentCaptor.forClass(String.class);

        // Verificando se deleteCourse foi chamado e capturando os argumentos
        verify(mockService, times(7)).deleteCourse(courseCaptor.capture());

        // Obtendo os valores capturados e verificando se são os cursos esperados
        List<String> deletedCourses = courseCaptor.getAllValues();
        assertAll(
                () -> assertEquals(7, deletedCourses.size()),
                () -> assertEquals("REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker", deletedCourses.get(0)),
                () -> assertEquals("Agile Desmistificado com Scrum, XP, Kanban e Trello", deletedCourses.get(1)),
                () -> assertEquals("Spotify Engineering Culture Desmistificado", deletedCourses.get(2)),
                () -> assertEquals("Docker do Zero à Maestria - Contêinerização Desmistificada", deletedCourses.get(3)),
                () -> assertEquals("Docker para Amazon AWS Implante Apps Java e .NET com Travis CI", deletedCourses.get(4)),
                () -> assertEquals("Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#", deletedCourses.get(5)),
                () -> assertEquals("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android", deletedCourses.get(6))
        );
    }
}
