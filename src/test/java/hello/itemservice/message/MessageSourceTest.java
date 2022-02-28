package hello.itemservice.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result= ms.getMessage("hello",null,null);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(()->ms.getMessage("no code",null,null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage(){
        String result= ms.getMessage("no code",null, "기본메시지", null);
        Assertions.assertThat(result).isEqualTo("기본메시지");
    }

    @Test
    void argumentMessage() {
        String message= ms.getMessage("hello.name",new Object[]{"Spring"},null);
        Assertions.assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void  defaultLang(){
        Assertions.assertThat(ms.getMessage("hello",null,null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello",null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void  enLang(){
        Assertions.assertThat(ms.getMessage("hello",null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
