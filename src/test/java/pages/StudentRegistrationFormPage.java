package pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.texts;
//import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.RandomUtils.*;

public class StudentRegistrationFormPage {
    Faker faker = new Faker();
    String name = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String gender = getRandomGender();
    String hobby = getRandomHobby();
    String phoneNumber = faker.phoneNumber().subscriberNumber(10);
    String monthOfBirth = getRandomMonth();
    String yearOfBirth = getRandomYear();
    int dayOfBirth = getRandomInt(10,29);
    String subject = "Chemistry";
    String currentAddress = faker.address().fullAddress();
    String state = "Haryana";
    String city = "Karnal";
    String filePath="./src/test/resources/pict.jpg";

    @Step("Open students registration form")
    public void openPage() {
        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form"));
    }

    @Step("Fill students registration form")
    public void fillForm() {
        mainStudentData();
        setSubject();
        selectHobby();
        uploadUserpic();
        setAddress();
        submitForm();
    }

    @Step("Fill students registration form without address")
    public void fillFormWithoutAddress() {
        mainStudentData();
        setSubject();
        selectHobby();
        uploadUserpic();
        submitForm();
    }

    void mainStudentData() {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        setBirthDate(yearOfBirth, monthOfBirth, dayOfBirth);
    }

    void setSubject() {
        $("#subjectsInput").setValue("c");
        $(byText(subject)).click();
    }

    void selectHobby() {
        $(byText(hobby)).click();
    }

    public void uploadUserpic() {
        $("#uploadPicture").uploadFile(new File(filePath));
    }

    public void setBirthDate(String year, String month, int day){
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__day--0" + day).click();
    }

    public void setAddress() {
        $("#currentAddress").setValue(currentAddress);
        $("#state").scrollTo().click();
        $(byText(state)).click();
        $("#city").click();
        $(byText(city)).click();
    }

    public void submitForm() {
        $("#submit").click();
    }

    @Step("Verify that form is filled")
    public void checkData() {
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $$("tbody tr").filterBy(text("Student name")).shouldHave(texts(name + " " + lastName));
        $$("tbody tr").filterBy(text("Student Email")).shouldHave(texts(email));
        $$("tbody tr").filterBy(text("Gender")).shouldHave(texts(gender));
        $$("tbody tr").filterBy(text("Mobile")).shouldHave(texts(phoneNumber));
        $$("tbody tr").filterBy(text("Date of Birth")).shouldHave(texts(dayOfBirth+" "+monthOfBirth+","+yearOfBirth));
        $$("tbody tr").filterBy(text("Subjects")).shouldHave(texts(subject));
        $$("tbody tr").filterBy(text("Hobbies")).shouldHave(texts(hobby));
        $$("tbody tr").filterBy(text("Picture")).shouldHave(texts("pict.jpg"));
        $$("tbody tr").filterBy(text("Address")).shouldHave(texts(currentAddress));
        $$("tbody tr").filterBy(text("State and City")).shouldHave(texts(state+" "+city));
        /*$("button#closeLargeModal").click();
        $("#example-modal-sizes-title-lg").shouldBe(hidden);*/
    }
}

