package com.example.AccountService.controller;

import com.example.AccountService.dto.EmployeeAuthenticationRequest;
import com.example.AccountService.dto.EmployeeAuthenticationResponse;
import com.example.AccountService.dto.EmployeeRequestDto;
import com.example.AccountService.dto.EmployeeResponseDto;
import com.example.AccountService.service.EmployeeService;
import com.example.AccountService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin
    @PostMapping("/login-as-employee")
    public EmployeeAuthenticationResponse generateToken(@RequestBody EmployeeAuthenticationRequest employeeAuthenticationRequest) throws BadCredentialsException{
        System.out.println("Inside generateToken");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeAuthenticationRequest.getUsername(), employeeAuthenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid");
        }
        EmployeeAuthenticationResponse employeeAuthenticationResponse = new EmployeeAuthenticationResponse();
        employeeAuthenticationResponse.setJwt(jwtUtil.generateToken(employeeAuthenticationRequest.getUsername()));
        System.out.println("TOKEN RECEIVED");
        return employeeAuthenticationResponse;
    }
    @PostMapping("/register-as-employee")
    public EmployeeResponseDto employeeResponseDto(@RequestBody EmployeeRequestDto requestDto){
        return employeeService.insertDataIntoEmployee(requestDto);
    }

    @GetMapping("/get-employee-list")
    public List<EmployeeResponseDto> employeeResponseDto() {
        return employeeService.getEmployeeList();
    }

}
