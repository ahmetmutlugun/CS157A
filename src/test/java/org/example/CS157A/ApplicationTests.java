package org.example.CS157A.Controllers;

import org.example.CS157A.Models.Customer;
import org.example.CS157A.Repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers_Success() throws Exception {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setName("John Doe");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setName("Jane Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].customerId").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    public void getAllCustomers_EmptyList() throws Exception {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void getCustomerById_Success() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("John Doe");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }


    @Test
    public void createCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("John Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void createCustomer_InvalidData() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());  // Assuming no validation in place
    }

    @Test
    public void updateCustomer_Success() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(1L);
        updatedCustomer.setName("John Doe Updated");

        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    public void updateCustomer_InvalidData() throws Exception {
        when(customerRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());  // Assuming no validation in place
    }

    @Test
    public void deleteCustomer_Success() throws Exception {
        doNothing().when(customerRepository).deleteById(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());

        verify(customerRepository, times(1)).deleteById(1L);
    }

}