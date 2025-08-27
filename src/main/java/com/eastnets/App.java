//package com.eastnets;
//
//import com.eastnets.config.AppConfig;
//import com.eastnets.dao.BankDAO;
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.sql.SQLException;
//public class App {
//    private  BankDAO bankDAO;
//
//    public static void main(String[] args) throws SQLException {
//        var ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        BankDAO bankdao =(BankDAO) ctx.getBean(BankDAO.class);
//        System.out.println(bankdao.getAllBanks());
//    }
//
//}
