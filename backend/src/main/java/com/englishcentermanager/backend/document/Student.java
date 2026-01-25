package com.englishcentermanager.backend.document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collation = "students")
public class Student {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("student_code")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Field("full_name")
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    @Indexed(unique = true)
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    private String gender;
    private String address;
}
