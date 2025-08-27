    package com.eastnets.web;

    import com.eastnets.model.Account;
    import com.eastnets.model.Customer;
    import com.eastnets.security.CustomerUserDetails;
    import com.eastnets.services.AccountService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController

    public class AccountController {
        private final AccountService accountService;

        public AccountController(AccountService accountService){
            this.accountService = accountService;
        }

        @GetMapping("/accounts")
        public ResponseEntity<List<Account>> getAccounts(){
            CustomerUserDetails customerUserDetails = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer =customerUserDetails.getCustomer();

            List<Account> accounts = accountService.getAllAccounts(customer.getNationalId());
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }

        @GetMapping("accounts/{accountNumber}")
        public ResponseEntity<Account> getAccount(@PathVariable("accountNumber") int accountNumber){
            Account account = accountService.getAccountByNumber(accountNumber);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }

        @PostMapping("/create-account")
        public ResponseEntity<Account> createAccount(@RequestParam("balance") float balance ,
                                                     @RequestParam("accountType") String accountType){
            CustomerUserDetails customerUserDetails = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = customerUserDetails.getCustomer();
            Optional<Account> newAccount =  accountService.createAccount(new Account(accountType , balance , customer));
            return new ResponseEntity<>(newAccount.get(), HttpStatus.OK);
        }

        @DeleteMapping("accounts/{accountNumber}")
        public String deleteAccount(@PathVariable("accountNumber") int accountNumber){
            Account account = accountService.getAccountByNumber(accountNumber);
            accountService.deleteAccount(account);
            return "successfully account " + accountNumber + "deleted";
        }
    }
