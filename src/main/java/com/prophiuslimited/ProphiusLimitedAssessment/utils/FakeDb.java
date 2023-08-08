//package com.prophiuslimited.ProphiusLimitedAssessment.utils;
//
//import com.github.javafaker.Faker;
//import com.prophiuslimited.ProphiusLimitedAssessment.entities.*;
//import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//
//@Component
//@RequiredArgsConstructor
//public class FakeDb implements CommandLineRunner {
//
//    private final Logger logger = LoggerFactory.getLogger(FakeDb.class);
//    private final UserRepository userRepository;
//    private final Faker faker;
//    @Override
//    public void run(String... args) throws Exception {
//
//
//      Set<CommentLike> commentLikes = IntStream.rangeClosed(1, 10).mapToObj(c-> new CommentLike(
//                faker.bool().bool(),
//                faker.random().nextInt(0,100),
//              (long) faker.number().numberBetween(1, 50),
//                faker.internet().uuid(),
//                 new Comment()
//
//        )).collect(Collectors.toSet());
//
//
// //========================================================================================================//
//        Set <Comment> comments = IntStream.rangeClosed(1, 3).mapToObj(k -> new Comment(
//                faker.lorem().paragraph(),
//                faker.internet().uuid(),
//                faker.name().username(),
//                faker.random().nextInt(0,100),
//                null,
//                null
//
//        )).collect(Collectors.toSet());
//
////===========================================================================================================//
//        Set<PostLike> postLikes = IntStream.rangeClosed(1, 50).mapToObj(p-> new PostLike(
//                faker.bool().bool(),
//                faker.internet().uuid(),
//                new Post(faker.lorem().paragraph(),
//                        faker.random().nextInt(0, 10),
//                        null,
//                        comments,
//                        null)
//        )).collect(Collectors.toSet());
////========================================================================================================//
//        Set<Post> posts = IntStream.rangeClosed(1, 5).mapToObj(j -> new Post(
//                faker.lorem().paragraph(),
//                faker.random().nextInt(0, 10),
//                new User(),
//                comments,
//                null
//                )).collect(Collectors.toSet());
////========================================================================================================//
//        Set<User> users = IntStream.rangeClosed(1, 30).mapToObj(i-> new User(
//                faker.internet().uuid(),
//                faker.name().username(),
//                faker.internet().emailAddress(),
//                faker.internet().password(),
//                faker.internet().url(),
//                posts,
//                null,
//                null
//     )).collect(Collectors.toSet());
//
//     userRepository.saveAll(users);
//
//    }
//}
