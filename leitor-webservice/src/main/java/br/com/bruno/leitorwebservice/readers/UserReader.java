package br.com.bruno.leitorwebservice.readers;

import br.com.bruno.leitorwebservice.models.User;
import br.com.bruno.leitorwebservice.requests.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserReader implements ItemReader<User> {
    private final UserApi userApi;
    @Value("${maxPage}")
    private Integer maxPage;
    @Value("${chunkSize}")
    private Integer chunkSize;
    @Value("${pageSize}")
    private Integer pageSize;
    private final List<User> users = new ArrayList<>();
    private Integer page = 1;

    @Override
    public User read() throws Exception{
        return !users.isEmpty() ? users.removeFirst() : null;
    }

    @BeforeChunk
    public void fetch() {
        for (int i = 0; i < chunkSize; i += pageSize)
            users.addAll(fetchUsers());
    }

    private List<User> fetchUsers() {
        var response = userApi.fetchUsers(page);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Erro ao buscar usuários");
        }

        if (page++ > maxPage) {
            return new ArrayList<>();
        }

        return response.getBody().getData();
    }
}
