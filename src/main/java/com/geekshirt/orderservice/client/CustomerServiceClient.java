package com.geekshirt.orderservice.client;

import com.geekshirt.orderservice.config.OrderServiceConfig;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.AddressDto;
import com.geekshirt.orderservice.dto.CreditCardDto;
import com.geekshirt.orderservice.dto.CustomerDto;
import com.geekshirt.orderservice.util.AccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class CustomerServiceClient {

    @Autowired
    private OrderServiceConfig config;

    private RestTemplate restTemplate;

    public CustomerServiceClient(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    public Optional<AccountDto> findAccountById(String accountId){
        Optional<AccountDto> result = Optional.empty();

        try{
            result = Optional.ofNullable(restTemplate.getForObject(config.getCustomerServiceUrl() + "/{id}",AccountDto.class,accountId));
        } catch (HttpClientErrorException ex){
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND){
                throw ex;
            }
        }

        return result;
    }

    public AccountDto createDummyAccount(){
        AddressDto address = AddressDto.builder()
                .street("Azcuenaga")
                .state("Buenos Aires")
                .country("Argentina")
                .zipCode("1231")
                .build();

        CustomerDto customer = CustomerDto.builder()
                .lastName("Perez")
                .firstName("Juan")
                .email("juanp@gmail.com")
                .build();

        CreditCardDto creditCard = CreditCardDto.builder()
                .nameOnCard("Juan Perez")
                .number("12121212")
                .expirationMonth("03")
                .expirationYear("2023")
                .type("VISA")
                .ccv("123")
                .build();

        AccountDto account = AccountDto.builder()
                .address(address)
                .customer(customer)
                .creditCard(creditCard)
                .status(AccountStatus.ACTIVE)
                .build();

        return account;
    }

    public AccountDto createAccount(AccountDto account){
        return restTemplate.postForObject(config.getCustomerServiceUrl(),account,AccountDto.class);
    }

    public AccountDto createAccountBody(AccountDto account){
        ResponseEntity<AccountDto> responseAccount = restTemplate.postForEntity(config.getCustomerServiceUrl(),account,AccountDto.class);
        log.info("Response " + responseAccount.getHeaders());
        return responseAccount.getBody();

    }

    public void updateAccount(AccountDto account){
        restTemplate.put(config.getCustomerServiceUrl() + "/{id}",account,account.getId());
    }

    public void deleteAccount(AccountDto account){
        restTemplate.delete(config.getCustomerServiceUrl() + "/{id}",account.getId());
    }






}
