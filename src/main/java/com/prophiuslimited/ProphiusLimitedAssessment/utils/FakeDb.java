//package com.prophiuslimited.ProphiusLimitedAssessment.utils;
//
//import com.github.javafaker.Faker;
//import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
//import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
//import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.stream.IntStream;
//
//
//@Component
//@RequiredArgsConstructor
//public class FakeDb implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final AppUtils appUtil;
//
//    private final Faker faker;
//
//    private final Logger logger = LoggerFactory.getLogger(FakeDb.class);
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("FakeDb Loading......");
//
//                   //Create 100 rows of Users data in Db
//
//     List<User> users =  IntStream.rangeClosed(1, 100)
//                .mapToObj(i -> new User(
//                        faker.bothify("pass1234 "),
//                        faker.name().username(),
//                        faker.internet().emailAddress(),
//                        faker.internet().password(),
//                        faker.internet().image(),
//                        new HashSet<>()
//                ))
//                .toList();
//        userRepository.saveAll(users);
//
//
//    }
//}
