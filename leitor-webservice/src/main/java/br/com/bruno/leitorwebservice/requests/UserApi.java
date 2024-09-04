package br.com.bruno.leitorwebservice.requests;

import br.com.bruno.leitorwebservice.models.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-api", url = "https://gorest.co.in/public/v1/users")
public interface UserApi {

    @GetMapping
    ResponseEntity<ResponseUser> fetchUsers(@RequestParam("page") int page);
}
